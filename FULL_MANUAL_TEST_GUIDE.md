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
В терминале javac не найден, поэтому новые .java изменения нужно пересобрать
через Eclipse.
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
2 - English
3 - Русский
```

Для проверки удобнее выбрать:

```text
2
```

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
Logged in: Oren Admin
```

Admin должен видеть:

```text
1 - Admin app
5 - Support app
6 - News app
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
```

Ожидаемый результат:

```text
Added: Manager{M1, Dana Dean, dean@uni.kz}
```

### 3.2 Создать Teacher

```text
Role: 3
Id: T1
Name: Prof Assylzhan
Email: prof@uni.kz
Password: prof
```

Ожидаемый результат:

```text
Added: Teacher{T1, Prof Assylzhan, prof@uni.kz}
```

### 3.3 Создать Student

```text
Role: 1
Id: S1
Name: Nursultan Student
Email: nura@uni.kz
Password: nurs
```

Ожидаемый результат:

```text
Added: Student{S1, Nursultan Student, student@uni.kz}
```

### 3.4 Создать Graduate Student

```text
Role: 2
Id: G1
Name: Aruzhan Master
Email: grad@uni.kz
Password: grad
```

Ожидаемый результат:

```text
Added: GraduateStudent{G1, Aruzhan Master, grad@uni.kz}
```

GraduateStudent автоматически является Researcher.

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
Admin{A1, Oren Admin, admin@uni.kz}
Manager{M1, Dana Dean, dean@uni.kz}
Teacher{T1, Prof Assylzhan, prof@uni.kz}
Student{S1, Nursultan Student, student@uni.kz}
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
  student@uni.kz / student

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

Teacher:
  2 - Education app
  5 - Support app
  6 - News app

Student:
  2 - Education app
  5 - Support app
  6 - News app

Graduate Student:
  2 - Education app
  4 - Research app
  5 - Support app
  6 - News app

Tech Support:
  5 - Support app
  6 - News app
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
Course id:  
Course name: Object-Oriented Programming
Credits: 5
Course type:
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
student@uni.kz
student
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
7 - Reject a pending registration
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
Attestation 1: 80
Attestation 2: 85
Final exam: 90
```

Ожидаемый результат:

```text
Mark saved: ...
```

Проверяет:

```text
Teacher.putMark()
Enrollment.setMark()
Mark
Student.recalculateGpa()
TeacherRating auto rating
```

## 12. Student: Transcript and Teacher Info

Logout teacher.

Login:

```text
student@uni.kz
student
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
ENR-... Object-Oriented Programming APPROVED Mark...
GPA: ...
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
Official request description: Book room 301 for final exam
Complaint text: Student was absent many times
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
Urgency.MEDIUM
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
0 - Back
```

### 16.1 Create Journal

```text
1
Journal id: J-UR
Journal title: University Research Journal
```

Ожидаемый результат:

```text
Created journal: ...
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

### 16.5 Join Research Project

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

### 16.6 Show News and Notifications

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

## 17. Support Request Flow

### 17.1 Create Request As Student

Login:

```text
student@uni.kz
student
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
2 - Assign first request to specialist
3 - Accept first request
4 - Mark first request done
5 - Show support requests
0 - Back
```

Проверка:

```text
5
```

Ожидаемый результат:

```text
SUP-... status: new
```

Assign:

```text
2
```

Ожидаемый результат:

```text
status: viewed
```

Accept:

```text
3
```

Ожидаемый результат:

```text
status: accepted
```

Done:

```text
4
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

## 18. Admin: Logs

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
student@uni.kz
student
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

```text
3 - Add comment
News number: 1
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
student@uni.kz
student
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
| `Session` | Login creates current user session | [ ] |
| `UserManagementService` | Admin add/remove users | [ ] |
| `DefaultUserFactory` | Create all roles | [ ] |
| `CourseRegistrationService.register` | Student registers for offering | [ ] |
| `CourseRegistrationService.approve` | Manager approves enrollment | [ ] |
| `CourseRegistrationService.reject` | Manager rejects enrollment | [ ] |
| `ReportService` | Manager creates academic report | [ ] |
| `NewsService.addGeneralNews` | Manager creates news | [ ] |
| `NewsItem.addComment` | News app adds comment | [ ] |
| `JournalService.publishPaper` | Researcher publishes paper | [ ] |
| `ResearchService.printAllResearchPapers` | Research sorted papers | [ ] |
| `ResearchService.joinProject` | Researcher joins project | [ ] |
| `SupportDeskService.createRequest` | User creates support request | [ ] |
| `SupportDeskService.assignToSpecialist` | Tech support assigns request | [ ] |
| `LogService` | Admin shows logs | [ ] |
| `FileUniversityDataStore.save` | Admin saves data | [ ] |
| `FileUniversityDataStore.load` | Restart and data loads | [ ] |
| `I18n` | Run in EN/RU/KZ | [ ] |
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
3. Create M1, T1, S1, G1, TS1.
4. Save.
5. Login manager.
6. Add CS101 course offering.
7. Assign T1 as lecture and practice teacher.
8. Login student.
9. Register S1 to OFF-CS101-2.
10. Login manager.
11. Approve registration.
12. Login teacher.
13. Put mark.
14. Login student.
15. Show transcript and teacher info.
16. Login manager.
17. Create report, news, official request/complaint.
18. Login graduate student.
19. Create journal, publish paper, show h-index, sort papers, join project.
20. Login student.
21. Create support request.
22. Login tech support.
23. Assign, accept, done support request.
24. Login any user.
25. Open News app, show news details, add comment.
26. Login admin.
27. Show logs.
28. Save.
29. Restart.
30. Confirm data loaded and all users/data still exist.
```

## 24. Known Console UI Notes

Some menus hide unavailable actions, but the internal numbers stay the same.

Example:

```text
Student Education menu:
  1, 2, 5, 6, 0

Manager Education menu:
  1, 3, 7, 0

Teacher Education menu:
  1, 4, 0
```

This is not a data bug. It is just how the current console menu is implemented.

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
