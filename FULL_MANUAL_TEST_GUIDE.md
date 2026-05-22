  # Full Manual Test Guide / Полная инструкция для проверки проекта

Этот файл нужен, чтобы с нуля проверить все основные действия проекта вручную
через console UI.

Проект запускается из:

```text
src/university/app/Main.java
```

Главная идея проверки:

```text
1. Запустить проект.
2. Создать пользователей.
3. Проверить login и role-based access.
4. Проверить Admin service.
5. Проверить Manager service.
6. Проверить Course Registration service.
7. Проверить Teacher marks.
8. Проверить Student transcript.
9. Проверить Research services.
10. Проверить Support services.
11. Проверить News app и comments.
12. Проверить Save/Load serialization.
```

## 0. Важно Перед Проверкой

### 0.1 Rebuild

Перед тестом сделай в Eclipse:

```text
Project -> Clean
Project -> Build Automatically
Run Main.java
```

Почему:

```text
Проект можно пересобрать через Eclipse.
Для terminal build на этой машине также найден javac:
IDE build is enough for this guide. If terminal javac is missing, rebuild and run from Eclipse/IDEA.
```

### 0.2 Проверка С Нуля

Если хочешь начать полностью с нуля, перед запуском можно удалить или
переименовать файл:

```text
D:\OOP\WSP2\data\university.ser
```

Например:

```text
university.ser -> university_backup.ser
```

Важно:

```text
Если удалить university.ser, старые сохраненные пользователи и курсы исчезнут.
Делай это только если реально хочешь чистый тест.
```

### 0.3 Автозагрузка Данных

При старте программа автоматически проверяет:

```text
data/university.ser
```

Если файл есть, данные загружаются автоматически.

Если файла нет или он пустой, система создает bootstrap admin:

```text
email:    admin@uni.kz
password: admin
```

### 0.4 Save Нужно Делать Вручную

Пока проект работает, изменения находятся в памяти.

Чтобы сохранить состояние в файл:

```text
Login as Admin -> Admin app -> 5 - Save data
```

Если не сохранить и закрыть программу, новые изменения могут потеряться.

## 1. Старт Проекта

Запусти `Main.java`.

Выбор языка:

```text
1 - Қазақша
2 - English (default)
3 - Русский
```

Для проверки удобнее выбрать:

```text
2
```

Если просто нажать Enter, программа выберет English по умолчанию.
Если ввести число вне диапазона 1-3, программа попросит выбрать язык заново.

Ожидаемый результат:

```text
Selected language: English
Bootstrap admin created: admin@uni.kz / admin
=== Login ===
```

Если данные уже были сохранены, вместо bootstrap admin будет:

```text
Data loaded.
```

## 2. Login Как Admin

Введи:

```text
Email: admin@uni.kz
Password: admin
```

Ожидаемый результат:

```text
Logged in: Admin
```

Admin должен видеть:

```text
1 - Admin app
5 - Support app
6 - News app
7 - Change language
8 - Change password
9 - Messages
0 - Logout
```

Проверка role-based access:

```text
Введи 2
```

Ожидаемый результат:

```text
Access denied for your role.
```

Это проверяет:

```text
AuthService
Session
Main role-based launcher
Unauthorized access protection
```

## 2.1 Change Language During Session

После login язык можно менять прямо в launcher.

Выбери:

```text
7 - Change language
```

Потом выбери язык:

```text
1 - Қазақша
2 - English
3 - Русский
```

Например, введи:

```text
3
```

Ожидаемый результат:

```text
Выбранный язык: Русский
=== Запускатель WSP ===
7 - Изменить язык
0 - Выйти из аккаунта
```

Проверяет:

```text
Main.changeLanguage()
AppLanguage.changeFor()
User.changeLanguage()
I18n runtime language switching
```

## 2.2 Change Password

Any logged-in user can now change password directly from launcher:

```text
8 - Change password
Current password: admin
New password: admin2
Confirm new password: admin2
```

Expected:

```text
Password changed.
```

Then logout and verify the new login:

```text
Email: admin@uni.kz
Password: admin2
```

If you want the rest of this guide to stay unchanged, change the password back to `admin` right after this test.

Negative test:

```text
Current password: wrong
New password: x
Confirm new password: x
```

Expected:

```text
Current password is incorrect.
```

Проверяет:

```text
Main.changePassword()
User.changePassword()
User.passwordMatches()
```

## 3. Создать Всех Пользователей

Войти:

```text
1 - Admin app
```

Admin menu:

```text
1 - Show users
2 - Add user
3 - Remove user
4 - Show logs
5 - Save data
6 - Load data
7 - Promote teacher to professor
0 - Back
```

### 3.1 Создать Manager

Выбери:

```text
2 - Add user
```

Введи:

```text
Role: 4
Id: M1
Name: Dana Dean
Email: dean@uni.kz
Password: dean
Manager type:
1 - OR
2 - DEPARTMENT
3 - DEAN_OFFICE
2
```

Ожидаемый результат:

```text
Added: Manager{M1, Dana Dean, dean@uni.kz}
```

Тип manager хранится внутри объекта и не показывается в коротком выводе user.
Чтобы отдельно проверить OR и DEAN_OFFICE, повтори шаг с новым id и выбери 1
или 3.

### 3.2 Создать Teacher

```text
Role: 3
Id: T1
Name: Prof Assylzhan
Email: prof@uni.kz
Password: prof
Teacher position: 4
```

Why `4 - PROFESSOR` is important:

```text
Only PROFESSOR teachers are auto-created with ResearcherProfile in the current
console flow.
The model itself allows any User to have a ResearcherProfile, but this menu
does not expose a separate "become researcher" action for teachers.
Assign supervisor requires a ResearcherProfile and h-index >= 3.
```

Ожидаемый результат:

```text
Added: Teacher{T1, Prof Assylzhan, prof@uni.kz}
```

### 3.2.1 Promote Existing Teacher To Professor

Use this if your saved `data/university.ser` already has `T1` created as `LECTOR`.
You do not need to delete `T1`.

Admin app:

```text
7 - Promote teacher to professor
Teacher id: T1
```

Expected:

```text
Promoted teacher to professor: Teacher{T1, Prof Assylzhan, prof@uni.kz}
```

What should happen:

```text
T1 keeps the same id/email/password/courses/ratings.
T1 position becomes PROFESSOR.
T1 gets ResearcherProfile.
T1 can now open Research app.
```

### 3.3 Создать Student

```text
Role: 1
Id: S1
Name: Nursultan Student
Email: nurs@uni.kz
Password: nurs
```

Ожидаемый результат:

```text
Added: Student{S1, Nursultan Student, nurs@uni.kz}
```

### 3.4 Создать Graduate Student

```text
Role: 2
Id: G1
Name: Aruzhan Master
Email: grad@uni.kz
Password: grad
Degree type:
1 - MASTER
2 - PHD
1
```

Ожидаемый результат:

```text
Added: GraduateStudent{G1, Aruzhan Master, grad@uni.kz}
```

GraduateStudent автоматически является Researcher.
Тип degree хранится внутри объекта и не показывается в коротком выводе user.
Чтобы отдельно проверить PHD, повтори шаг с новым id и выбери 2.

### 3.5 Создать Tech Support

```text
Role: 6
Id: TS1
Name: Aliya Support
Email: support@uni.kz
Password: support
```

Ожидаемый результат:

```text
Added: TechSupportSpecialist{TS1, Aliya Support, support@uni.kz}
```

### 3.6 Проверить Duplicate User Id

Попробуй снова добавить пользователя с id:

```text
S1
```

Ожидаемый результат:

```text
User with this id already exists. Use another id.
```

Это проверяет защиту от случайной замены старого пользователя.

### 3.7 Show Users

Выбери:

```text
1 - Show users
```

Ожидаемый результат:

```text
Admin{A1, Admin, admin@uni.kz}
Manager{M1, Dana Dean, dean@uni.kz}
Teacher{T1, Prof Assylzhan, prof@uni.kz}
Student{S1, Nursultan Student, nurs@uni.kz}
GraduateStudent{G1, Aruzhan Master, grad@uni.kz}
TechSupportSpecialist{TS1, Aliya Support, support@uni.kz}
```

### 3.8 Save Data

Выбери:

```text
5 - Save data
```

Ожидаемый результат:

```text
Data saved.
```

Это проверяет:

```text
UserManagementService
DefaultUserFactory
LogService
FileUniversityDataStore
Serialization
```

## 4. Проверить Login Для Каждой Роли

Выйди из Admin app:

```text
0 - Back
0 - Logout
```

Проверь логины:

```text
Manager:
  dean@uni.kz / dean

Teacher:
  prof@uni.kz / prof

Student:
  nurs@uni.kz / nurs

Graduate Student:
  grad@uni.kz / grad

Tech Support:
  support@uni.kz / support
```

Ожидаемые меню:

```text
Manager:
  2 - Education app
  3 - Manager app
  5 - Support app
  6 - News app
  7 - Change language
  8 - Change password
  9 - Messages

Teacher:
  2 - Education app
  4 - Research app
  5 - Support app
  6 - News app
  7 - Change language
  8 - Change password
  9 - Messages

Student:
  2 - Education app
  5 - Support app
  6 - News app
  7 - Change language
  8 - Change password

Graduate Student:
  2 - Education app
  4 - Research app
  5 - Support app
  6 - News app
  7 - Change language
  8 - Change password

Tech Support:
  5 - Support app
  6 - News app
  7 - Change language
  8 - Change password
  9 - Messages
```

Note:

```text
Teacher sees 4 - Research app only if that teacher is professor/researcher.
In this guide T1 is expected to be PROFESSOR.
```

## 5. Manager: Добавить Course Offering

Login:

```text
dean@uni.kz
dean
```

Открыть:

```text
3 - Manager app
1 - Add course for registration
```

Ввести:

```text
Course id:  CS101 
Course name: Object-Oriented Programming
Credits: 5
Course type:
  1 - MAJOR
  2 - MINOR
  3 - FREE_ELECTIVE
  1
Capacity: 20
Major: SITE
Year: 2
```

Ожидаемый результат:

```text
Added course: OFF-CS101-2 - CS101 - Object-Oriented Programming ...
```

Проверяет:

```text
Manager.addCourseForRegistration()
University.addCourse()
University.addCourseOffering()
Course
CourseOffering
CourseType enum
```

## 6. Manager: Назначить Teacher На Course

В Manager app выбрать:

```text
2 - Assign teacher to course
```

Ввести:

```text
Course id: CS101
Teacher id: T1
Lesson type:
  1
```

Где:

```text
1 - LECTURE
2 - PRACTICE
```

Повтори еще раз для practice:

```text
Course id: CS101
Teacher id: T1
Lesson type:
  2
```

Ожидаемый результат:

```text
Assigned teacher: Teacher{T1, Prof Assylzhan, prof@uni.kz}
```

Проверяет:

```text
Manager.assignCourseToTeacher()
Teacher.assignCourse()
Course.addLesson()
Lesson
LessonType enum
```

## 7. Manager: Sort Students и Sort Teachers

В Manager app выбрать:

```text
3 - Show students sorted
```

Ввести:

```text
1
```

Ожидаемый результат:

```text
Students sorted by GPA
```

Повтори:

```text
3
2
```

Ожидаемый результат:

```text
Students sorted by name
```

Проверка teachers:

```text
4 - Show teachers sorted
1 - by rating
2 - by name
```

Проверяет:

```text
SortStudentByGpaStrategy
SortStudentByNameStrategy
SortTeacherByRatingStrategy
SortTeacherByNameStrategy
Strategy pattern
```

## 8. Student: View Courses, Register

Logout manager.

Login:

```text
nurs@uni.kz
nurs
```

Открыть:

```text
2 - Education app
```

Важно: меню у Student показывает не все номера подряд. Используй реальные номера:

```text
1 - Show courses
2 - Register student for a course
5 - Show transcript
6 - Show course teachers
7 - Rate teacher
8 - Show student organizations
9 - Join student organization
10 - Leave student organization
11 - Request new student organization
0 - Back
```

### 8.1 View Courses

```text
1
```

Ожидаемый результат:

```text
CS101 - Object-Oriented Programming
```

### 8.2 Register

```text
2
```

Будет список offerings:

```text
OFF-CS101-2 - CS101 ...
```

Ввести:

```text
OFF-CS101-2
```

Ожидаемый результат:

```text
Created enrollment: ENR-... Nursultan Student, Object-Oriented Programming, status: pending
```

Проверяет:

```text
Student.registerCourse()
CourseRegistrationService.register()
Credit limit <= 21
Fail count <= 3
Course availability
Prerequisites
Duplicate active enrollment protection
Enrollment PENDING
```

### 8.3 Duplicate Registration Test

Сразу попробуй снова:

```text
2
OFF-CS101-2
```

Ожидаемый результат:

```text
student already has an active registration for CS101
```

### 8.4 Student Organizations

Show current organizations:

```text
8
```

Expected:

```text
ORG-DEV - Developers Club, members=...
ORG-DEBATE - Debate League, members=...
```

Join one:

```text
9
Organization id: ORG-DEV
```

Expected:

```text
Joined organization: Developers Club
```

Leave it:

```text
10
Organization id: ORG-DEV
```

Expected:

```text
Left organization: Developers Club
```

### 8.5 Request New Student Organization

```text
11
Organization id: ORG-AI
Organization name: AI Club
Description: Student club for AI demos and hackathons
```

Expected:

```text
Organization request sent: ORGREQ-... - Nursultan Student, ORG-AI, AI Club, status: pending, Student club for AI demos and hackathons
```

Проверяет:

```text
StudentOrganization
StudentOrganizationRequest
Join / leave organization flow
Pending organization request creation
```

## 9. Manager: Approve Registration

Logout student.

Login:

```text
dean@uni.kz
dean
```

Открыть:

```text
2 - Education app
```

Важно: Manager в Education app использует номера:

```text
1 - Show courses
3 - Approve a pending registration
8 - Reject a pending registration
0 - Back
```

Выбери:

```text
3
```

Скопируй enrollment id из списка:

```text
ENR-...
```

Введи его.

Ожидаемый результат:

```text
Approved: ENR-... status: approved
```

Notification check:

```text
Login as nurs@uni.kz / nurs
6 - News app
8 - Show my notifications
```

Expected:

```text
Notifications:
- Registration approved: Object-Oriented Programming (ENR-...)
```

Проверяет:

```text
Manager.approveStudentRegistration()
CourseRegistrationService.approve()
Enrollment.approve()
Course.occupySeat()
Student.addCredits()
RegistrationStatus.APPROVED
```

## 10. Manager: Reject Registration

Чтобы проверить reject, нужен второй pending enrollment.

Вариант:

```text
1. Manager app -> Add course for registration
   Course id: MATH101
   Course name: Calculus
   Credits: 4
   Type: 1
   Capacity: 20
   Major: SITE
   Year: 2

2. Student login -> Education app -> Register
   Offering id: OFF-MATH101-2

3. Manager login -> Education app -> Reject pending registration
```

Ожидаемый результат:

```text
Rejected: ENR-... status: rejected
```

Проверяет:

```text
Manager.rejectStudentRegistration()
CourseRegistrationService.reject()
Enrollment.reject()
RegistrationStatus.REJECTED
```

## 11. Teacher: Put Mark

Logout manager.

Login:

```text
prof@uni.kz
prof
```

Открыть:

```text
2 - Education app
```

Teacher menu uses:

```text
1 - Show courses
4 - Put mark
0 - Back
```

Выбрать:

```text
4
```

Скопировать approved enrollment id:

```text
ENR-...
```

Ввести:

```text
Attestation 1 (0-30): 30
Attestation 2 (0-30): 30
Final exam (0-40): 40
```

Ожидаемый результат:

```text
Mark saved: A1=30.0, A2=30.0, Final=40.0, Grade=100.0, Letter=A
```

Важно:

```text
Grade = A1 + A2 + Final
A1 max = 30
A2 max = 30
Final max = 40
Maximum total = 100
```

Negative test:

```text
Attestation 1 (0-30): 31
```

Ожидаемый результат:

```text
Attestation 1 must be between 0 and 30.0
```

Проверяет:

```text
Teacher.putMark()
Enrollment.setMark()
Mark
Mark.getLetterGrade()
Student.recalculateGpa()
```

## 12. Student: Transcript and Teacher Info

Logout teacher.

Login:

```text
nurs@uni.kz
nurs
```

Открыть:

```text
2 - Education app
```

### 12.1 Transcript

```text
5
```

Ожидаемый результат:

```text
Transcript for Nursultan Student
ENR-... Object-Oriented Programming APPROVED A1=30.0, A2=30.0, Final=40.0, Grade=100.0, Letter=A
GPA: 4.00
```

### 12.2 View Teacher Info

```text
6
OFF-CS101-2
```

Ожидаемый результат:

```text
Lecture: Teacher{T1, ...}
Practice: Teacher{T1, ...}
```

Проверяет:

```text
Student.getTranscript()
Student.viewTeacherInfo()
Course lessons
LessonType LECTURE/PRACTICE
```

### 12.3 Rate Teacher

```text
7
OFF-CS101-2
1
Teacher rating: 5
```

Where:

```text
1 - LECTURE
2 - PRACTICE
```

Expected:

```text
Teacher rated: Prof Assylzhan (5)
```

Important:

```text
Teacher rating is now a separate student action.
Put mark no longer auto-creates rating 5.
```

Проверяет:

```text
Student.rateTeacher()
Teacher.addRating()
Manual rating flow
```

## 13. Manager: Academic Report

Login:

```text
dean@uni.kz
dean
```

Открыть:

```text
3 - Manager app
5 - Create academic report
```

Ожидаемый результат:

```text
Marks count: 1
Average final grade: ...
```

Проверяет:

```text
ReportService.createAcademicReport()
StatisticalReport
```

## 14. Manager: Manage News

Manager app:

```text
6 - Manage news
```

Ввести:

```text
News title: Exam week
News content: Exams start next Monday
```

Ожидаемый результат:

```text
Created news: ...
```

Проверяет:

```text
NewsService.addGeneralNews()
News
NewsTopic.GENERAL
```

## 15. Manager: Official Request and Complaint

Manager app:

```text
7 - Official request and complaint
```

Ввести:

```text
Teacher id: T1
Student id: S1
Official request description: Book room 301 for final exam
Complaint text: Student was absent many times
Urgency:
1 - LOW
2 - MEDIUM
3 - HIGH
2
```

Ожидаемый результат:

```text
OfficialRequest...
Complaint...
```

Проверяет:

```text
Employee.createOfficialRequest()
OfficialRequest.sign()
Teacher.sendComplaint()
Complaint
selected urgency (LOW / MEDIUM / HIGH)
```

### 15.1 Student Organization Requests

Manager app:

```text
8 - Show organization requests
```

Expected:

```text
ORGREQ-... - Nursultan Student, ORG-AI, AI Club, status: pending, ...
```

Approve:

```text
9 - Approve organization request
Organization request id: ORGREQ-...
```

Expected:

```text
Organization request approved: ORGREQ-... status: approved
```

Now login as `nurs@uni.kz / nurs` and check:

```text
2 - Education app
8 - Show student organizations
```

Expected:

```text
ORG-AI - AI Club, members=1, head=Nursultan Student, Student club for AI demos and hackathons
```

Notification check:

```text
6 - News app
8 - Show my notifications
```

Expected:

```text
Notifications:
- Organization request approved: AI Club (ORG-AI)
```

Negative branch:

```text
Create another request and use 10 - Reject organization request.
```

Expected:

```text
Organization request rejected: ORGREQ-... status: rejected
```

### 15.2 Employee Messages

Any employee can open:

```text
9 - Messages
```

Test as manager:

```text
1 - Send message
Employee id: T1
Message text: Please confirm the exam room.
```

Expected:

```text
Sent message: ...
```

Then login as teacher and open:

```text
9 - Messages
2 - Show my messages
```

Expected:

```text
... Dana Dean ... Please confirm the exam room.
```

Notification check as teacher:

```text
6 - News app
8 - Show my notifications
```

Expected:

```text
Notifications:
- New message from Dana Dean: Please confirm the exam room.
```

## 16. Research: Journal, Paper, H-index, Sorting

Login:

```text
grad@uni.kz
grad
```

GraduateStudent должен видеть:

```text
4 - Research app
```

Открыть:

```text
4
```

Research menu:

```text
1 - Create journal
2 - Publish paper
3 - Show first researcher h-index
4 - Print papers sorted
5 - Assign supervisor
6 - Join research project
7 - Show news and notifications
8 - Generate top cited researcher news
9 - Add diploma project
10 - Show diploma projects
0 - Back
```

### 16.1 Create Journal

Negative test:

```text
1
Journal id:
```

Expected:

```text
This field is required.
```

Now create a valid journal:

```text
1
Journal id: J-UR
Journal title: University Research Journal
```

Ожидаемый результат:

```text
Created journal: ...
```

If you try the same `Journal id` again:

```text
Journal with this id already exists.
```

Проверяет:

```text
Journal
University.addJournal()
Journal subscribes NewsService and NotificationService
Observer pattern
```

### 16.2 Publish Paper

```text
2
Journal id: J-UR
Paper id: P1
Paper title: LMS Logs and Student Performance
Citations: 10
Pages: 12
Doi: 10.1000/oop1
```

Ожидаемый результат:

```text
Published papers.
Plain text citation...
BibTeX citation...
```

Проверяет:

```text
JournalService.publishPaper()
ResearcherProfile.publishPaper()
ResearchPaper
CitationFormatterFactory
PlainTextCitationFormatter
BibtexCitationFormatter
NewsService.generateResearchAnnouncement()
PinnedNewsDecorator
```

### 16.3 Show H-index

```text
3
```

Ожидаемый результат:

```text
H-index: ...
```

### 16.4 Print Sorted Papers

```text
4
1
```

Сортировка:

```text
1 - by citations
2 - by date
3 - by pages
```

Проверь все три варианта.

Проверяет:

```text
ResearchService.printAllResearchPapers()
SortByCitationsStrategy
SortByDateStrategy
SortByPagesStrategy
Strategy pattern
```

### 16.5 Assign Supervisor

This action now asks for exact ids. It should not silently select the first graduate student or first researcher.

Important setup:

```text
T1 should have a ResearcherProfile.
In the current console flow, the easiest way to get that is Teacher position 4 - PROFESSOR.
If T1 was created earlier as LECTOR, you can promote T1 to professor first.
For successful assignment, T1 must also have h-index >= 3.
```

To make T1 h-index >= 3, login as `prof@uni.kz / prof`, open Research app, and publish 3 papers with at least 3 citations each:

```text
4 - Research app
2 - Publish paper
Journal id: J-UR
Paper id: PT1
Paper title: Supervisor Paper 1
Citations: 3
Pages: 5
Doi: 10.1000/t1

2 - Publish paper
Journal id: J-UR
Paper id: PT2
Paper title: Supervisor Paper 2
Citations: 3
Pages: 5
Doi: 10.1000/t2

2 - Publish paper
Journal id: J-UR
Paper id: PT3
Paper title: Supervisor Paper 3
Citations: 3
Pages: 5
Doi: 10.1000/t3
```

Then login as graduate student again:

```text
5
Student id: G1
Researcher user id: T1
```

Expected if selected researcher h-index is lower than 3:

```text
Low h-index...
```

Expected if selected researcher has h-index >= 3:

```text
Supervisor assigned: ...
```

Проверяет:

```text
ResearchService.assignSupervisor()
GraduateStudent.assignSupervisor()
LowHIndexException
Self-supervisor protection
```

### 16.6 Join Research Project

```text
6
Project id: RP1
Topic: Student Performance Analytics
```

Ожидаемый результат:

```text
ResearchProject...
```

Проверяет:

```text
ResearchService.joinProject()
ResearchProject
NotResearcherException protection
DuplicateParticipantException protection
```

### 16.7 Show News and Notifications

```text
7
```

Ожидаемый результат:

```text
News:
New research paper: LMS Logs and Student Performance
Student notifications: ...
```

Проверяет:

```text
NewsService.getFeed()
Pinned research news priority
Observer notifications
```

### 16.8 Generate Top Cited Researcher News

```text
8
School: SITE
Year: 2026
```

Expected:

```text
Generated news: [PINNED] Top cited researcher 2026 ...
```

Проверяет:

```text
NewsService.generateTopCitedResearcherNews()
ResearchService.getTopCitedResearcher()
Pinned research news generation
```

### 16.9 Diploma Projects

Graduate student only:

```text
9
Paper id: DP1
Paper title: AI Tutor for OOP
Citations: 0
Pages: 40
Year: 2026
Doi: 10.1000/dp1
```

Expected:

```text
Diploma project saved: ...
```

Now show saved diploma projects:

```text
10
```

Expected:

```text
DP1 ...
```

Проверяет:

```text
GraduateStudent.addDiplomaProject()
GraduateStudent.getDiplomaProjects()
ResearchPaper as diploma project
```

## 17. Support Request Flow

### 17.1 Create Request As Student

Login:

```text
nurs@uni.kz
nurs
```

Открыть:

```text
5 - Support app
1 - Create support request
```

Ввести:

```text
Description: Projector in room 301 is not working
```

Ожидаемый результат:

```text
Created: request SUP-..., status: new, Projector...
```

Проверяет:

```text
SupportDeskService.createRequest()
SupportRequest
RequestStatus.NEW
```

### 17.2 Process Request As Tech Support

Login:

```text
support@uni.kz
support
```

Открыть:

```text
5 - Support app
```

Tech support menu:

```text
1 - Create support request
2 - Assign request to me
3 - Accept request
4 - Reject request
5 - Mark request done
6 - Show support requests
0 - Back
```

Проверка:

```text
6
```

Ожидаемый результат:

```text
SUP-... status: new
```

Assign:

```text
2
Request id: SUP-...
```

Ожидаемый результат:

```text
status: viewed
```

Accept:

```text
3
Request id: SUP-...
```

Ожидаемый результат:

```text
status: accepted
```

Done:

```text
5
Request id: SUP-...
```

Ожидаемый результат:

```text
status: done
```

Проверяет:

```text
TechSupportSpecialist
SupportDeskService.assignToSpecialist()
SupportRequest.markViewed()
SupportRequest.accept()
SupportRequest.done()
RequestStatus lifecycle
```

Negative test:

```text
Create two support requests.
Login as tech support.
Use 6 - Show support requests.
Choose 2 / 3 / 5 and enter the exact SUP-... id you want.
```

Expected:

```text
Only selected request changes status.
Other requests stay unchanged.
```

### 17.3 Support Notifications

After assign / accept / reject / done, login back as `nurs@uni.kz / nurs` and open:

```text
6 - News app
8 - Show my notifications
```

Expected examples:

```text
Support request assigned: SUP-... -> Aliya Support
Support request accepted: SUP-...
Support request completed: SUP-...
```

Проверяет:

```text
SupportRequest notifications to creator
User.addNotification()
```

## 18. News App: View Details and Comments

News app доступен всем залогиненным пользователям через launcher:

```text
6 - News app
```

Сначала убедись, что новости уже есть:

```text
Manager app -> 6 - Manage news
```

Или:

```text
Research app -> Publish paper
```

Research publication автоматически создает pinned research news.

### 18.1 Show News

Login любым пользователем, например student:

```text
nurs@uni.kz
nurs
```

Открыть:

```text
6 - News app
1 - Show news
```

Ожидаемый результат:

```text
1 - Exam week (GENERAL, 0 comments)
2 - [PINNED] New research paper: ... (RESEARCH, 0 comments)
```

Проверяет:

```text
NewsApp.showNews()
NewsService.getFeed()
Pinned news priority
```

### 18.2 Show News Details

```text
2 - Show news details
News number: 1
```

Ожидаемый результат:

```text
Exam week (GENERAL, 0 comments)
Exams start next Monday
Comments:
No comments.
```

Проверяет:

```text
NewsItem.getContent()
NewsItem.getComments()
```

### 18.3 Add Comment

Important:

```text
Add comment works only after opening news details first.
If you choose 3 before choosing 2, expected result is:
Open news details first.
```

```text
2 - Show news details
News number: 1
3 - Add comment
Comment text: Good luck everyone!
```

Ожидаемый результат:

```text
Comment added.
```

Теперь снова:

```text
2 - Show news details
News number: 1
```

Ожидаемый результат:

```text
Comments:
- Nursultan Student: Good luck everyone!
```

Проверяет:

```text
NewsItem.addComment()
News.addComment()
Comment
Current logged-in user as comment author
```

### 18.4 Journal Subscribe / Unsubscribe

Open News app:

```text
6 - News app
```

Show journals:

```text
4 - Show journals
```

Expected:

```text
J-UR - University Research Journal papers=...
```

Subscribe:

```text
5 - Subscribe to journal
Journal id: J-UR
```

Expected:

```text
Subscribed to journal: University Research Journal
```

Unsubscribe:

```text
6 - Unsubscribe from journal
Journal id: J-UR
```

Expected:

```text
Unsubscribed from journal: University Research Journal
```

Show journal papers:

```text
7 - Show journal papers
Journal id: J-UR
```

Show notifications:

```text
8 - Show my notifications
```

Important:

```text
Notifications are stored inside current User.
Subscribe itself does not create a notification.
Notification appears after a new paper is published in a journal you are subscribed to.
```

Full notification test:

```text
1. Login as student.
2. News app -> 5 - Subscribe to journal -> J-UR.
3. Logout.
4. Login as professor/researcher.
5. Research app -> 2 - Publish paper -> Journal id: J-UR.
6. Logout.
7. Login as the same student.
8. News app -> 8 - Show my notifications.
```

Expected:

```text
Notifications:
- New paper in University Research Journal: ...
```

Проверяет:

```text
JournalService.subscribe()
JournalService.unsubscribe()
Journal.getPapers()
User.update()
User.getNotifications()
Observer subscription list
```

### 18.5 Other Notification Sources

`News app -> 8 - Show my notifications` is also the place to verify:

```text
Registration approved / rejected
Support request assigned / accepted / rejected / completed
Organization request submitted / approved / rejected
New employee message
Official request signed / rejected
```

This is the fastest smoke test to confirm cross-service notifications are working.

## 19. Admin: Logs

Login:

```text
admin@uni.kz
admin
```

Открыть:

```text
1 - Admin app
4 - Show logs
```

Ожидаемый результат:

```text
User added
Registered for Object-Oriented Programming
Approved ENR-...
Published paper ...
Created support request
...
```

Проверяет:

```text
LogService
LogEntry
Admin.viewLogFiles()
```

## 20. Save / Load Test

### 20.1 Save

Admin app:

```text
5 - Save data
```

Ожидаемый результат:

```text
Data saved.
```

Закрой программу.

### 20.2 Restart

Запусти `Main.java` снова.

Ожидаемый результат:

```text
Data loaded.
```

Login:

```text
admin@uni.kz
admin
```

Admin app:

```text
1 - Show users
```

Ожидаемый результат:

```text
Все созданные пользователи остались.
```

Проверь также:

```text
Manager login -> courses остались
Student login -> transcript остался
Tech support login -> support requests остались
Researcher login -> journal/news/papers остались
```

Проверяет:

```text
University.save()
University.load()
FileUniversityDataStore
Serializable classes
transient services rebuilt after load
```

## 21. Negative Tests / Проверка Ошибок

### 21.1 Wrong Login

На login screen:

```text
Email: wrong@uni.kz
Password: wrong
```

Ожидаемый результат:

```text
Wrong email or password.
```

### 21.2 Access Denied

Login as Student:

```text
nurs@uni.kz
nurs
```

В launcher введи:

```text
1
```

Ожидаемый результат:

```text
Access denied for your role.
```

### 21.3 Duplicate User

Login admin, Add user с id:

```text
S1
```

Ожидаемый результат:

```text
User with this id already exists. Use another id.
```

### 21.4 Duplicate Course Registration

Student tries to register to the same course offering twice.

Ожидаемый результат:

```text
student already has an active registration for CS101
```

### 21.5 Put Mark Without Approved Enrollment

Login teacher before manager approves registration.

Teacher -> Education app -> Put mark.

Ожидаемый результат:

```text
No approved enrollment found.
```

### 21.6 Course Credit Limit

Create courses with total credits above 21 and try to register student.

Ожидаемый результат:

```text
Credit limit exceeded...
```

## 22. Service Coverage Checklist

Use this table to mark what you tested.

| Service / Class | How to test | Done |
| --- | --- | --- |
| `AuthService` | Login success and wrong login | [ ] |
| `Session` | Login creates current user session and logout clears it | [ ] |
| `User.changePassword` | Launcher password change and relogin with new password | [ ] |
| `UserManagementService` | Admin add/remove users | [ ] |
| `DefaultUserFactory` | Create all roles | [ ] |
| `CourseRegistrationService.register` | Student registers for offering | [ ] |
| `CourseRegistrationService.approve` | Manager approves enrollment | [ ] |
| `CourseRegistrationService.reject` | Manager rejects enrollment | [ ] |
| `StudentOrganizationRequest` | Student creates request, manager approves/rejects | [ ] |
| `CommunicationApp` / `Message` | Employee sends and reads messages | [ ] |
| `ReportService` | Manager creates academic report | [ ] |
| `NewsService.addGeneralNews` | Manager creates news | [ ] |
| `NewsService.generateTopCitedResearcherNews` | Research app generates top cited news manually | [ ] |
| `NewsItem.addComment` | News app adds comment | [ ] |
| `JournalService.publishPaper` | Researcher publishes paper | [ ] |
| `JournalService.subscribe` | News app subscribes user to journal | [ ] |
| `JournalService.unsubscribe` | News app unsubscribes user from journal | [ ] |
| `ResearchService.printAllResearchPapers` | Research sorted papers | [ ] |
| `ResearchService.assignSupervisor` | Research app assigns supervisor by ids | [ ] |
| `ResearchService.joinProject` | Researcher joins project | [ ] |
| `GraduateStudent.addDiplomaProject` | Graduate student adds and shows diploma project | [ ] |
| `SupportDeskService.createRequest` | User creates support request | [ ] |
| `SupportDeskService.assignToSpecialist` | Tech support assigns selected request by id | [ ] |
| `LogService` | Admin shows logs | [ ] |
| `FileUniversityDataStore.save` | Admin saves data | [ ] |
| `FileUniversityDataStore.load` | Restart and data loads | [ ] |
| `I18n` | Run in EN/RU/KZ and change language inside session | [ ] |
| `Strategy` patterns | Sort students/teachers/papers | [ ] |
| `Observer` pattern | Publish paper creates notification/news | [ ] |
| `Decorator` pattern | Research news appears pinned/high priority | [ ] |
| `Factory` pattern | User/citation creation works | [ ] |
| `Singleton` pattern | Shared data visible between apps | [ ] |

## 23. Full Happy Path Summary

If you want one compact full path:

```text
1. Start project.
2. Login admin.
3. Use 7 - Change language once, then switch back if needed.
4. Use 8 - Change password once and verify relogin, then either keep the new password in mind or change it back.
5. Create M1, T1, S1, G1, TS1.
6. Save.
7. Login manager.
8. Add CS101 course offering.
9. Assign T1 as lecture and practice teacher.
10. Login student.
11. Register S1 to OFF-CS101-2.
12. Join one default student organization and create one new organization request.
13. Login manager.
14. Approve registration and approve the organization request.
15. Login teacher.
16. Put mark with A1=30, A2=30, Final=40.
17. Login student.
18. Show transcript, teacher info, and rate teacher.
19. Login manager.
20. Create report, news, official request/complaint, then send one employee message to T1.
21. Login graduate student.
22. Create journal, publish paper, show h-index, sort papers, join project, generate top cited researcher news, add diploma project.
23. Login student.
24. Create support request and check notifications.
25. Login tech support.
26. Show requests, choose exact SUP-... id, assign, accept, done support request.
27. Login any user.
28. Open News app, show news details, add comment.
29. In News app, show journals, subscribe, unsubscribe, show journal papers, show notifications.
30. Login admin.
31. Show logs.
32. Save.
33. Restart.
34. Confirm data loaded and all users/data still exist.
```

## 24. Known Console UI Notes

Some menus hide unavailable actions, but the internal numbers stay the same.

Example:

```text
Student Education menu:
  1, 2, 5, 6, 7, 8, 9, 10, 11, 0

Manager Education menu:
  1, 3, 8, 0

Teacher Education menu:
  1, 4, 0
```

This is not a data bug. It is just how the current console menu is implemented.

Launcher common menu:

```text
5 - Support app
6 - News app
7 - Change language
8 - Change password
0 - Logout
```

Employee-only launcher action:

```text
9 - Messages
```

Support app now works by exact request id:

```text
2 - Assign request to me
3 - Accept request
4 - Reject request
5 - Mark request done
6 - Show support requests
```

Research supervisor note:

```text
If Assign supervisor prints:
No researcher found. Add a graduate student or professor first.

Most likely T1 was created as LECTOR in old saved data.
Fix: Admin app -> 7 - Promote teacher to professor -> Teacher id: T1.
Alternative: create a new teacher like T2 with Teacher position 4 - PROFESSOR, or delete/rename data/university.ser and retest from clean data.
Also remember: supervisor h-index must be >= 3, otherwise LowHIndexException is expected.
```

## 25. What To Do If Data Looks Missing

Checklist:

```text
1. Did you save from Admin app before closing?
2. Does data/university.ser exist?
3. Did the program print "Data loaded." at startup?
4. Did you accidentally create a user with the same id?
5. Did you reset/delete university.ser?
```

After the latest fix:

```text
The system loads data/university.ser automatically at startup.
AdminApp blocks duplicate user ids.
```
