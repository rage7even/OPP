# University Research System - Defense Guide

This file is a defense-oriented explanation of the project. It explains how the
system matches the PDF requirements, Use Case diagram, Class Diagram, and how the
main code works.

The project is a console-based research-oriented university system. It models
users, employees, students, teachers, managers, admins, tech support, course
registration, marks, research papers, journals, news, support requests,
serialization, localization, and several design patterns.

## 1. Quick Project Summary

Project name:

```text
University Research System
```

Main entry point:

```text
src/university/app/Main.java
```

Main domain facade:

```text
src/university/core/University.java
```

Main runtime flow:

```text
Choose language -> Login -> Role-based launcher -> Run allowed module -> Logout
```

Bootstrap account when the system is empty:

```text
email:    admin@uni.kz
password: admin
```

The first admin exists so the system is not locked when there are no users yet.
After login as admin, create other users from Admin app.

## 2. Diagram Consistency Check

### 2.1 ClassDiagram.puml vs source code

The Class Diagram matches the model/service layer in `src/university`.

Classes, interfaces, enums, and exceptions from the diagram exist in the source.
One implementation class was added to the diagram during verification:

```text
InMemoryUserRepository
```

The following classes exist in source but are intentionally not part of the
domain Class Diagram because they belong to the console application layer:

```text
Main
AdminApp
EducationApp
ManagerApp
ResearchApp
SupportApp
AppData
AppFormatter
AppLanguage
ConsoleInput
I18n
```

This is acceptable for defense because the Class Diagram represents the domain
model and services, while these classes only implement the console user
interface.

### 2.2 UseCase.puml vs implementation

Core use cases are implemented:

```text
Authentication:
  Login, Logout, Change Language

Admin:
  Add User, Remove User, View Logs, Save Data, Load Data

Student:
  View Courses, Register for Course, View Transcript,
  View Teachers of Course, Create Support Request

Manager:
  Add Course for Registration, Assign Course to Teacher,
  Approve Registration, Reject Registration,
  View Students Sorted, View Teachers Sorted,
  Create Academic Report, Manage News

Teacher:
  Put Mark, Send Complaint through Manager scenario,
  View assigned courses at model level

Tech Support:
  View Requests, Assign, Accept, Reject, Done

Researcher:
  Create Journal, Publish Paper, Calculate H-index,
  Print Sorted Papers, Join Project, View News and Notifications

News and Journals:
  Research announcements, pinned research news,
  journal subscription notification model
```

Some use cases are present mainly at model level, not always as a separate
console menu item:

```text
View Profile
Comment News
Student Organization UI
Update User UI
Advanced journal browsing UI
Advanced official request approval/rejection UI
```

Defense explanation:

```text
The system contains model/service support for these concepts, while the console
demo focuses on the most important functionality required by the PDF:
course registration, marks, and research.
```

## 3. High-Level Architecture

ASCII architecture:

```text
                         +----------------------+
                         |      Main.java       |
                         | language + login     |
                         +----------+-----------+
                                    |
                                    v
                         +----------------------+
                         | Role-Based Launcher  |
                         +--+----+----+----+----+
                            |    |    |    |
       +--------------------+    |    |    +------------------+
       |                         |    |                       |
       v                         v    v                       v
+-------------+          +-------------+              +----------------+
|  AdminApp   |          | EducationApp|              |  ResearchApp   |
+------+------+          +------+------+              +-------+--------+
       |                        |                             |
       v                        v                             v
+-------------+          +-------------+              +----------------+
| UserService |          | Registration|              | ResearchService|
+-------------+          | Service     |              | JournalService |
                         +------+------+              +----------------+
                                |
                                v
                         +-------------+
                         | University  |
                         | Singleton   |
                         +-------------+
```

Central idea:

```text
Console apps do not store the main data themselves.
They call services through University.getInstance().
University owns lists of users, courses, enrollments, journals, news, logs, etc.
```

## 4. Startup and Authentication Flow

ASCII sequence:

```text
User starts program
      |
      v
Choose language: KZ / EN / RU
      |
      v
If no users exist:
  create bootstrap admin
      |
      v
Login with email and password
      |
      v
AuthService checks InMemoryUserRepository
      |
      +-- fail --> show login.failed and ask again
      |
      +-- success --> create Session
                       |
                       v
                Role-based launcher
```

Relevant classes:

```text
Main.java
AuthService.java
Session.java
UserRepository.java
InMemoryUserRepository.java
User.java
```

Line-by-line style explanation of `Main.main`:

```text
configureUtf8Output()
  Makes console output use UTF-8, so Russian and Kazakh text prints correctly.

Scanner scanner = new Scanner(System.in)
  Opens console input.

Language language = chooseLanguage(scanner)
  Reads selected language from user.

AppLanguage.apply(language)
  Stores selected language and applies it to existing users.

ensureBootstrapAdmin()
  Creates admin@uni.kz / admin if the system has no users.

while (true)
  Keeps the system running after logout.

Session session = login(scanner)
  Asks for email/password and authenticates.

runLauncher(scanner, session)
  Shows menu according to authenticated user's role.
```

## 5. Role-Based Access

The launcher does not blindly allow all modules. It checks the runtime class of
the authenticated user.

Access matrix:

```text
+-----------------------+-------+-----------+---------+----------+---------+
| Role                  | Admin | Education | Manager | Research | Support |
+-----------------------+-------+-----------+---------+----------+---------+
| Admin                 | yes   | no        | no      | no       | yes     |
| Student               | no    | yes       | no      | if has   | yes     |
| GraduateStudent       | no    | yes       | no      | yes      | yes     |
| Teacher               | no    | yes       | no      | if has   | yes     |
| Professor Teacher     | no    | yes       | no      | yes      | yes     |
| Manager               | no    | yes       | yes     | if has   | yes     |
| TechSupportSpecialist | no    | no        | no      | if has   | yes     |
+-----------------------+-------+-----------+---------+----------+---------+
```

Implementation idea:

```java
if (user instanceof Admin) {
    AdminApp.run(scanner);
}
```

For research:

```java
if (user.isResearcher()) {
    ResearchApp.run(scanner, user);
}
```

This matches the requirement:

```text
Any user should access the system via authentication.
```

## 6. Course Registration Flow

This is one of the most important PDF requirements.

ASCII flow:

```text
Manager:
  Add Course for Registration
       |
       v
Course + CourseOffering are created
       |
       v
Student:
  Register for CourseOffering
       |
       v
CourseRegistrationService.register(...)
       |
       +-- check credits <= 21
       +-- check failCount <= 3
       +-- check course capacity
       +-- check duplicate active registration
       +-- check prerequisites
       |
       v
Enrollment(status=PENDING)
       |
       v
Manager:
  Approve or Reject pending enrollment
       |
       +-- approve -> status=APPROVED, seat occupied, credits added
       |
       +-- reject  -> status=REJECTED
```

Important classes:

```text
Course.java
CourseOffering.java
Enrollment.java
CourseRegistrationService.java
Student.java
Manager.java
EducationApp.java
```

Why `CourseOffering` exists:

```text
Course is the academic subject itself.
CourseOffering is the fact that this course is open for registration
for a specific major and year.
```

Example:

```text
Course:
  CS101 - Object-Oriented Programming

CourseOffering:
  OFF-CS101-2, major=SITE, year=2
```

## 7. Mark and Transcript Flow

ASCII flow:

```text
Teacher logs in
      |
      v
Education app -> Put mark
      |
      v
Choose approved enrollment
      |
      v
Input attestation1, attestation2, finalExam
      |
      v
Teacher.putMark(enrollment, mark)
      |
      v
Enrollment.setMark(mark)
      |
      +-- if finalGrade < 50 -> student.recordFail()
      |
      v
Student.recalculateGpa()
```

Important classes:

```text
Teacher.java
Enrollment.java
Mark.java
Student.java
EducationApp.java
```

Mark formula is inside:

```text
Mark.getFinalGrade()
```

Transcript is generated by:

```text
Student.getTranscript()
```

## 8. Research Flow

Research is another major part of the PDF requirements.

ASCII flow:

```text
Researcher logs in
      |
      v
ResearchApp
      |
      +-- Create Journal
      |
      +-- Publish Paper
      |       |
      |       v
      |  JournalService.publishPaper(...)
      |       |
      |       +-- add paper to researcher profile
      |       +-- add paper to journal
      |       +-- notify journal observers
      |       +-- generate research news
      |
      +-- Calculate H-index
      |
      +-- Print papers sorted by citations/date/pages
```

Important classes:

```text
Researcher.java
ResearcherProfile.java
ResearchPaper.java
ResearchProject.java
Journal.java
JournalService.java
ResearchService.java
NewsService.java
NotificationService.java
```

Why `ResearcherProfile` exists:

```text
Researcher is a role, not only an inheritance type.
Any User can have a ResearcherProfile.
GraduateStudent always receives ResearcherProfile.
Professor Teacher also receives ResearcherProfile.
```

ASCII model:

```text
User
 |
 +-- Student
 |     |
 |     +-- GraduateStudent ---- has ----> ResearcherProfile
 |
 +-- Employee
       |
       +-- Teacher(professor) -- has ----> ResearcherProfile
```

## 9. News and Journal Notification Flow

ASCII flow:

```text
User subscribes to Journal
      |
      v
Journal stores user as Observer
      |
      v
Researcher publishes paper
      |
      v
Journal.notifyObservers(paper)
      |
      +-- User.update(paper) -> notification added
      |
      +-- NewsService.update(paper) -> research news created
      |
      +-- NotificationService.update(paper)
```

Pattern:

```text
Observer pattern
Subject: Journal
Observers: User, NewsService, NotificationService
```

## 10. Support Request Flow

ASCII flow:

```text
Any logged-in user
      |
      v
SupportApp -> Create support request
      |
      v
SupportDeskService.createRequest(user, description)
      |
      v
SupportRequest(status=NEW)
      |
      v
TechSupportSpecialist logs in
      |
      +-- assign request -> VIEWED
      +-- accept request -> ACCEPTED
      +-- reject request -> REJECTED
      +-- mark done      -> DONE
```

Important classes:

```text
SupportApp.java
SupportDeskService.java
SupportRequest.java
TechSupportSpecialist.java
RequestStatus.java
```

## 11. Serialization and Data Storage

The project supports saving and loading system state.

ASCII flow:

```text
Admin chooses Save Data
      |
      v
University.save()
      |
      v
FileUniversityDataStore.save(university)
      |
      v
data/university.ser
```

Load:

```text
Admin chooses Load Data
      |
      v
University.load()
      |
      v
FileUniversityDataStore.load()
      |
      v
University replaces in-memory lists
      |
      v
rebuildServices()
```

Important detail:

```text
Services are transient in University.
After loading serialized data, rebuildServices() recreates services.
```

## 12. Localization

Languages:

```text
KZ, EN, RU
```

Important classes:

```text
Language.java
AppLanguage.java
I18n.java
Main.java
```

How it works:

```text
AppLanguage.selected()
  Stores current selected language.

I18n.t(key)
  Reads current language and returns text in KZ, EN, or RU.

Main.configureUtf8Output()
  Prevents broken symbols in Russian/Kazakh output.
```

## 13. Package-by-Package Explanation

```text
university.app
  Console UI layer. Reads user input and calls domain/services.

university.core
  Contains University singleton, the central facade and storage owner.

university.users
  User, Student, GraduateStudent, ratings, student organizations.

university.employees
  Employee, Teacher, Manager, Admin, TechSupportSpecialist.

university.education
  Course, CourseOffering, Enrollment, Lesson, Mark.

university.research
  Researcher role, profiles, papers, projects, journals.

university.news
  News model, comments, decorator for pinned news.

university.communication
  Message, Complaint, OfficialRequest.

university.support
  SupportRequest.

university.services
  Business logic services: auth, registration, reports, research,
  support desk, serialization, logs, notifications.

university.patterns
  Strategy, Factory, Observer, citation formatters.

university.enums
  Strongly typed constants used across the system.

university.exceptions
  Custom exceptions for business rule violations.
```

## 14. Important Classes and What to Say in Defense

### Main

Responsibility:

```text
Starts the program, chooses language, creates bootstrap admin, logs user in,
and shows only role-allowed apps.
```

Key methods:

```text
main()
  Program entry point.

chooseLanguage()
  Allows KZ/EN/RU localization.

login()
  Uses AuthService and returns Session.

runLauncher()
  Dispatches to AdminApp, EducationApp, ManagerApp, ResearchApp, SupportApp.

ensureBootstrapAdmin()
  Creates the first admin if storage is empty.
```

### University

Responsibility:

```text
Singleton and facade. Owns the system lists and service objects.
```

Why Singleton:

```text
All apps and services need one shared university state.
University.getInstance() guarantees one central object.
```

### AuthService

Responsibility:

```text
Checks email/password and creates Session.
```

Important method:

```text
login(email, password)
  Finds user by email.
  Compares passwordHash with input password.
  Throws UnauthorizedActionException if invalid.
  Returns Session if valid.
```

### User

Responsibility:

```text
Base class for all system users.
Stores id, name, email, password, language, notifications,
and optional ResearcherProfile.
```

Important methods:

```text
changeLanguage()
  Changes user's selected language.

subscribeToJournal()
  Subscribes user to a journal.

update()
  Observer callback when a new paper is published.

becomeResearcher()
  Adds a ResearcherProfile to a user.
```

### Student

Responsibility:

```text
Represents student academic behavior.
```

Important methods:

```text
registerCourse()
  Calls CourseRegistrationService.register().

viewMarks()
  Returns marks from enrollments.

getTranscript()
  Prints all enrollments and GPA.

rateTeacher()
  Creates TeacherRating.

recordFail()
  Increases fail count and throws TooManyFailsException if above 3.
```

### GraduateStudent

Responsibility:

```text
Special student type that is always a researcher.
```

Important behavior:

```text
Constructor creates ResearcherProfile.
assignSupervisor() checks supervisor h-index >= 3.
```

### Teacher

Responsibility:

```text
Manages courses, puts marks, sends complaints, can be researcher if professor.
```

Important methods:

```text
assignCourse()
  Adds course to teacher's course list.

putMark()
  Writes Mark into Enrollment.

sendComplaint()
  Creates complaint to Manager/Dean.
```

### Manager

Responsibility:

```text
Dean office / department manager behavior.
```

Important methods:

```text
addCourseForRegistration()
  Creates CourseOffering.

assignCourseToTeacher()
  Creates Lesson and assigns teacher.

approveStudentRegistration()
  Approves pending Enrollment through registration service.

rejectStudentRegistration()
  Rejects pending Enrollment through registration service.

viewStudents(strategy)
  Sorts students by selected strategy.
```

### Admin

Responsibility:

```text
Manages users and views logs.
```

Important methods:

```text
addUser()
removeUser()
updateUser()
viewLogFiles()
```

### CourseRegistrationService

Responsibility:

```text
Main business logic for student course registration.
```

Important rules:

```text
credits <= 21
failCount <= 3
course has capacity
student cannot have active duplicate registration
prerequisites must be satisfied
```

Important methods:

```text
register(student, offering)
  Validates rules and creates PENDING Enrollment.

approve(manager, enrollment)
  Checks capacity and credit limit, then approves.

reject(manager, enrollment)
  Rejects pending enrollment.
```

### Course

Responsibility:

```text
Academic course.
```

Important fields:

```text
courseId
name
credits
type
capacity
prerequisites
lessons
```

### CourseOffering

Responsibility:

```text
Connects a course to a major/year registration period.
```

### Enrollment

Responsibility:

```text
Represents student's registration to a CourseOffering.
```

Statuses:

```text
PENDING -> APPROVED
PENDING -> REJECTED
```

### Mark

Responsibility:

```text
Stores attestation1, attestation2, final exam and calculates final grade.
```

### ResearcherProfile

Responsibility:

```text
Implements Researcher role for any User.
```

Why not only inheritance:

```text
Because teachers, students, graduate students, and other users can be researchers.
Composition is more flexible than forcing all researcher users into one class branch.
```

### ResearchPaper

Responsibility:

```text
Stores paper data and returns citation in Plain Text or BibTeX format.
```

### Journal

Responsibility:

```text
Stores papers and notifies observers when a paper is published.
```

### NewsService

Responsibility:

```text
Creates news, research announcements, and top researcher news.
Research news is pinned using decorator.
```

### SupportRequest

Responsibility:

```text
Represents support desk request lifecycle.
```

Statuses:

```text
NEW -> VIEWED -> ACCEPTED -> DONE
NEW -> VIEWED -> REJECTED
```

## 15. Design Patterns Used

### Singleton

Class:

```text
University
```

Purpose:

```text
One central university state and service facade.
```

### Factory

Classes:

```text
UserFactory
DefaultUserFactory
CitationFormatterFactory
```

Purpose:

```text
Create users and citation formatters without scattering construction logic.
```

### Strategy

Classes:

```text
PaperSortStrategy
SortByDateStrategy
SortByCitationsStrategy
SortByPagesStrategy
StudentSortStrategy
SortStudentByGpaStrategy
SortStudentByNameStrategy
TeacherSortStrategy
SortTeacherByRatingStrategy
SortTeacherByNameStrategy
```

Purpose:

```text
Sorting behavior is replaceable at runtime.
```

### Observer

Classes:

```text
Subject
Observer
Journal
User
NewsService
NotificationService
```

Purpose:

```text
Journal notifies subscribers when new paper is published.
```

### Decorator

Classes:

```text
NewsItem
News
NewsDecorator
PinnedNewsDecorator
```

Purpose:

```text
Research news can be wrapped to become pinned and higher priority.
```

## 16. Custom Exceptions

```text
CreditLimitExceededException
  Student tries to exceed 21 credits.

TooManyFailsException
  Student fail count exceeds 3.

CourseNotAvailableException
  Course has no capacity.

PrerequisiteNotMetException
  Student has not passed required course.

LowHIndexException
  Supervisor h-index is below 3.

NotResearcherException
  Non-researcher tries to join research project.

DuplicateParticipantException
  Same researcher joins the same project twice.

DuplicateSubscriptionException
  Same user subscribes to same journal twice.

InvalidRatingException
  Rating value is outside valid range.

UnauthorizedActionException
  Invalid login or forbidden action.

AlreadyResearcherException
  User already has ResearcherProfile.
```

## 17. Suggested Demo Script

Use this flow during defense.

### Step 1: Start and login

```text
Choose language:
  2

Login:
  admin@uni.kz
  admin
```

### Step 2: Create users

In Admin app, create:

```text
Manager:
  id=M1
  name=Dana Dean
  email=dean@uni.kz
  password=dean

Teacher:
  id=T1
  name=Prof. Assylzhan
  email=prof@uni.kz
  password=prof

Student:
  id=S1
  name=Nursultan Student
  email=student@uni.kz
  password=student

Graduate student:
  id=G1
  name=Aruzhan Master
  email=grad@uni.kz
  password=grad

Tech support:
  id=TS1
  name=Aliya Support
  email=support@uni.kz
  password=support
```

### Step 3: Manager creates course

Logout admin, login manager:

```text
dean@uni.kz
dean
```

Manager app:

```text
Add course for registration:
  courseId=CS101
  name=Object-Oriented Programming
  credits=5
  type=1
  capacity=20
  major=SITE
  year=2
```

Assign teacher:

```text
courseId=CS101
teacherId=T1
lesson type=1 or 2
```

### Step 4: Student registers

Logout manager, login student:

```text
student@uni.kz
student
```

Education app:

```text
View courses
Register for course
Choose offering OFF-CS101-2
```

Result:

```text
Enrollment is created with status PENDING.
```

### Step 5: Manager approves

Login manager again:

```text
dean@uni.kz
dean
```

Education app:

```text
Approve pending registration
Choose enrollment id
```

Result:

```text
Enrollment becomes APPROVED.
Student credits increase.
Course capacity decreases.
```

### Step 6: Teacher puts mark

Login teacher:

```text
prof@uni.kz
prof
```

Education app:

```text
Put mark
Choose approved enrollment
Input attestation1, attestation2, finalExam
```

### Step 7: Student checks transcript

Login student:

```text
student@uni.kz
student
```

Education app:

```text
Show transcript
```

### Step 8: Research demo

Login graduate student:

```text
grad@uni.kz
grad
```

Research app:

```text
Create journal
Publish paper
Show h-index
Print sorted papers
```

## 18. Defense Talking Points

Short explanation:

```text
This project uses a layered OOP design. Console app classes only handle input
and output. Business rules are inside model and service classes. University is
a singleton facade that stores all data and exposes services.
```

For authentication:

```text
Before any module opens, user must log in. Main creates a Session and checks the
runtime class of the user to show only role-allowed modules.
```

For registration:

```text
Student cannot directly become enrolled. Student creates a pending enrollment.
Manager must approve it. This matches the academic office workflow.
```

For patterns:

```text
Singleton gives one University object.
Factory creates users and citation formatters.
Strategy sorts papers/students/teachers.
Observer notifies journal subscribers.
Decorator pins research news.
```

For serialization:

```text
University is serializable. It saves to data/university.ser. Services are
transient and rebuilt after loading.
```

## 19. If Teacher Asks: Why Some Console Features Are Simple

Answer:

```text
The project is primarily an OOP design project, not a full production LMS.
The domain model contains the required classes and relationships. The console
demo focuses on the most important functionality from the specification:
course registration, putting marks, research, authentication, serialization,
localization, and design patterns.
```

## 20. Current Verification Result

Checked items:

```text
UseCase.puml:
  Core use cases match implementation.
  Some optional use cases are model-level or simplified in console UI.

ClassDiagram.puml:
  Domain/service classes match source code.
  Console UI helper classes are intentionally outside the domain diagram.

Localization:
  EN/RU/KZ keys are present for the new authentication and registration flow.

Course registration:
  PENDING -> APPROVED/REJECTED flow is implemented.

Authentication:
  Login/session and role-based access are implemented.
```

## 21. Compact ASCII Class Map

```text
User
|-- Student
|   |-- GraduateStudent
|
|-- Employee
    |-- Teacher
    |-- Manager
    |-- Admin
    |-- TechSupportSpecialist

Student -- owns --> Enrollment -- points to --> CourseOffering -- points to --> Course
Course  -- owns --> Lesson -- has instructor --> Teacher
Enrollment -- may have --> Mark

User -- may have --> ResearcherProfile -- owns --> ResearchPaper
Journal -- owns --> ResearchPaper
Journal -- notifies --> Observer

University -- owns --> users, courses, offerings, journals, news, requests, logs
University -- owns --> services
```

## 22. Compact ASCII Sequence: Registration

```text
Student          EducationApp          RegistrationService          Manager
   |                  |                         |                      |
   | register course  |                         |                      |
   +----------------->|                         |                      |
   |                  | register(student, off.) |                      |
   |                  +------------------------>|                      |
   |                  |                         | check rules          |
   |                  |                         | create PENDING       |
   |                  |<------------------------+                      |
   |<-----------------+                         |                      |
   |                  |                         |                      |
   |                  |                  approve/reject enrollment      |
   |                  |<------------------------------------------------+
   |                  |                         | status changes       |
```

## 23. Compact ASCII Sequence: Research Publish

```text
Researcher      ResearchApp      JournalService      Journal        Observers
    |               |                 |                |              |
    | publish paper |                 |                |              |
    +-------------->|                 |                |              |
    |               | publishPaper    |                |              |
    |               +---------------->|                |              |
    |               |                 | add to profile |              |
    |               |                 | add to journal |              |
    |               |                 +--------------->|              |
    |               |                 |                | notify       |
    |               |                 |                +------------->|
```

