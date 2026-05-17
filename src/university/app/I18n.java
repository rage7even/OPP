package university.app;

import university.enums.CourseType;
import university.enums.Language;
import university.enums.RegistrationStatus;
import university.enums.RequestStatus;

public final class I18n {
    private I18n() {
    }

    public static String t(String key) {
        Language language = AppLanguage.selected();
        if (language == Language.KZ) {
            return kz(key);
        }
        if (language == Language.RU) {
            return ru(key);
        }
        return en(key);
    }

    public static String f(String key, Object value) {
        return t(key) + " " + value;
    }

    public static String languageName(Language language) {
        if (language == Language.KZ) {
            return "Қазақша";
        }
        if (language == Language.RU) {
            return "Русский";
        }
        return "English";
    }

    public static String courseType(CourseType type) {
        if (AppLanguage.selected() == Language.KZ) {
            switch (type) {
                case MAJOR: return "мамандық курсы";
                case MINOR: return "minor курсы";
                case FREE_ELECTIVE: return "еркін таңдау курсы";
                default: return type.name();
            }
        }
        if (AppLanguage.selected() == Language.RU) {
            switch (type) {
                case MAJOR: return "профильный курс";
                case MINOR: return "minor курс";
                case FREE_ELECTIVE: return "курс свободного выбора";
                default: return type.name();
            }
        }
        switch (type) {
            case MAJOR: return "major course";
            case MINOR: return "minor course";
            case FREE_ELECTIVE: return "free elective";
            default: return type.name();
        }
    }

    public static String registrationStatus(RegistrationStatus status) {
        if (AppLanguage.selected() == Language.KZ) {
            switch (status) {
                case PENDING: return "күтілуде";
                case APPROVED: return "мақұлданды";
                case REJECTED: return "қабылданбады";
                default: return status.name();
            }
        }
        if (AppLanguage.selected() == Language.RU) {
            switch (status) {
                case PENDING: return "ожидает";
                case APPROVED: return "одобрено";
                case REJECTED: return "отклонено";
                default: return status.name();
            }
        }
        return status.name().toLowerCase();
    }

    public static String requestStatus(RequestStatus status) {
        if (AppLanguage.selected() == Language.KZ) {
            switch (status) {
                case NEW: return "жаңа";
                case VIEWED: return "қаралды";
                case ACCEPTED: return "қабылданды";
                case REJECTED: return "қабылданбады";
                case DONE: return "аяқталды";
                default: return status.name();
            }
        }
        if (AppLanguage.selected() == Language.RU) {
            switch (status) {
                case NEW: return "новый";
                case VIEWED: return "просмотрен";
                case ACCEPTED: return "принят";
                case REJECTED: return "отклонен";
                case DONE: return "готов";
                default: return status.name();
            }
        }
        return status.name().toLowerCase();
    }

    private static String en(String key) {
        switch (key) {
            case "choose.language.title": return "=== Choose language / Til tandanyz / Vyberite yazyk ===";
            case "choose.language.prompt": return "Language:";
            case "selected.language": return "Selected language:";
            case "launcher.title": return "=== WSP2 launcher ===";
            case "launcher.admin": return "1 - Admin app";
            case "launcher.education": return "2 - Education app";
            case "launcher.manager": return "3 - Manager app";
            case "launcher.research": return "4 - Research app";
            case "launcher.support": return "5 - Support app";
            case "launcher.exit": return "0 - Exit";
            case "launcher.logout": return "0 - Logout";
            case "login.title": return "=== Login ===";
            case "logged.in": return "Logged in:";
            case "logged.out": return "Logged out.";
            case "login.failed": return "Wrong email or password.";
            case "current.user": return "Current user:";
            case "access.denied": return "Access denied for your role.";
            case "bootstrap.admin.created": return "Bootstrap admin created: admin@uni.kz / admin";
            case "choose.app": return "Choose app:";
            case "choose": return "Choose:";
            case "back": return "0 - Back";
            case "bye": return "Bye.";
            case "unknown.choice": return "Unknown choice.";
            case "enter.number": return "Please enter a number.";
            case "enter.decimal": return "Please enter a decimal number.";
            case "admin.title": return "--- Admin app ---";
            case "admin.show.users": return "1 - Show users";
            case "admin.add.user": return "2 - Add user";
            case "admin.remove.user": return "3 - Remove user";
            case "admin.show.logs": return "4 - Show logs";
            case "admin.save": return "5 - Save data";
            case "admin.load": return "6 - Load data";
            case "roles": return "Roles: 1-STUDENT, 2-GRADUATE_STUDENT, 3-TEACHER, 4-MANAGER, 5-ADMIN, 6-TECH_SUPPORT";
            case "role": return "Role:";
            case "invalid.role": return "Invalid role.";
            case "id": return "Id:";
            case "name": return "Name:";
            case "email": return "Email:";
            case "password": return "Password:";
            case "added": return "Added:";
            case "user.id": return "User id:";
            case "removed.user": return "Removed user if existed:";
            case "data.saved": return "Data saved.";
            case "data.loaded": return "Data loaded.";
            case "education.title": return "--- Education app ---";
            case "education.show.courses": return "1 - Show courses";
            case "education.register": return "2 - Register student for a course";
            case "education.approve": return "3 - Approve a pending registration";
            case "education.put.mark": return "4 - Put mark";
            case "education.transcript": return "5 - Show transcript";
            case "education.teachers": return "6 - Show course teachers";
            case "education.reject": return "7 - Reject a pending registration";
            case "created.enrollment": return "Created enrollment:";
            case "approved": return "Approved:";
            case "rejected": return "Rejected:";
            case "no.enrollment": return "No enrollment found. Register first.";
            case "no.approved.enrollment": return "No approved enrollment found.";
            case "attestation1": return "Attestation 1:";
            case "attestation2": return "Attestation 2:";
            case "final.exam": return "Final exam:";
            case "mark.saved": return "Mark saved:";
            case "lecture": return "Lecture:";
            case "practice": return "Practice:";
            case "manager.title": return "--- Manager app ---";
            case "manager.add.course": return "1 - Add course for registration";
            case "manager.assign.teacher": return "2 - Assign teacher to course";
            case "manager.students.sorted": return "3 - Show students sorted";
            case "manager.teachers.sorted": return "4 - Show teachers sorted";
            case "manager.report": return "5 - Create academic report";
            case "manager.news": return "6 - Manage news";
            case "manager.official": return "7 - Official request and complaint";
            case "course.id": return "Course id:";
            case "course.name": return "Course name:";
            case "course.type": return "Course type: 1-MAJOR, 2-MINOR, 3-FREE_ELECTIVE";
            case "credits": return "Credits:";
            case "credits.word": return "credits";
            case "capacity": return "Capacity:";
            case "major": return "Major";
            case "year": return "Year";
            case "student.id": return "Student id:";
            case "teacher.id": return "Teacher id:";
            case "offering.id": return "Course offering id:";
            case "enrollment.id": return "Enrollment id:";
            case "status": return "status";
            case "added.course": return "Added course:";
            case "assigned.practice.teacher": return "Assigned teacher:";
            case "sort.students": return "1 - by GPA, 2 - by name";
            case "sort.teachers": return "1 - by rating, 2 - by name";
            case "sort": return "Sort:";
            case "news.title": return "News title:";
            case "news.content": return "News content:";
            case "created.news": return "Created news:";
            case "research.title": return "--- Research app ---";
            case "hindex": return "H-index:";
            case "supervisor.assigned": return "Supervisor assigned:";
            case "published.papers": return "Published papers.";
            case "sort.papers": return "1 - by citations, 2 - by date, 3 - by pages";
            case "news": return "News:";
            case "no.news": return "No news found.";
            case "student.notifications": return "Student notifications:";
            case "support.title": return "--- Support app ---";
            case "support.create": return "1 - Create support request";
            case "support.assign": return "2 - Assign first request to specialist";
            case "support.accept": return "3 - Accept first request";
            case "support.done": return "4 - Mark first request done";
            case "support.show": return "5 - Show support requests";
            case "description": return "Description:";
            case "created": return "Created:";
            case "assigned": return "Assigned:";
            case "accepted": return "Accepted:";
            case "done": return "Done:";
            case "no.requests": return "No requests.";
            case "request": return "request";
            case "no.student": return "No student found. Add a student in Admin app first.";
            case "no.manager": return "No manager found. Add a manager in Admin app first.";
            case "no.teacher": return "No teacher found. Add a teacher in Admin app first.";
            case "no.support.specialist": return "No tech support specialist found. Add one in Admin app first.";
            case "no.course": return "No course found.";
            case "no.offering": return "No course offering found. Create it in Manager app first.";
            case "no.journal": return "No journal found. Create a journal first.";
            case "no.researcher": return "No researcher found. Add a graduate student or professor first.";
            case "no.graduate.student": return "No graduate student found. Add one in Admin app first.";
            case "no.pending": return "No pending enrollments found.";
            case "journal.id": return "Journal id:";
            case "journal.title": return "Journal title:";
            case "created.journal": return "Created journal:";
            case "paper.id": return "Paper id:";
            case "paper.title": return "Paper title:";
            case "paper.citations": return "Citations:";
            case "paper.pages": return "Pages:";
            case "paper.doi": return "Doi:";
            case "project.id": return "Project id:";
            case "project.topic": return "Topic:";
            case "official.description": return "Official request description:";
            case "complaint.text": return "Complaint text:";
            case "research.create.journal": return "1 - Create journal";
            case "research.publish": return "2 - Publish paper";
            case "research.hindex": return "3 - Show first researcher h-index";
            case "research.sorted": return "4 - Print papers sorted";
            case "research.supervisor": return "5 - Assign supervisor";
            case "research.join": return "6 - Join research project";
            case "research.news": return "7 - Show news and notifications";
            default: return key;
        }
    }

    private static String ru(String key) {
        switch (key) {
            case "choose.language.title": return "=== Выберите язык / Тілді таңдаңыз / Choose language ===";
            case "choose.language.prompt": return "Язык:";
            case "selected.language": return "Выбранный язык:";
            case "launcher.title": return "=== Запускатель WSP ===";
            case "launcher.admin": return "1 - Приложение администратора";
            case "launcher.education": return "2 - Учебное приложение";
            case "launcher.manager": return "3 - Приложение менеджера";
            case "launcher.research": return "4 - Исследовательское приложение";
            case "launcher.support": return "5 - Приложение техподдержки";
            case "launcher.exit": return "0 - Выход";
            case "launcher.logout": return "0 - Выйти из аккаунта";
            case "login.title": return "=== Вход ===";
            case "logged.in": return "Вошел пользователь:";
            case "logged.out": return "Вы вышли из аккаунта.";
            case "login.failed": return "Неверный email или пароль.";
            case "current.user": return "Текущий пользователь:";
            case "access.denied": return "Доступ запрещен для вашей роли.";
            case "bootstrap.admin.created": return "Создан стартовый админ: admin@uni.kz / admin";
            case "choose.app": return "Выберите приложение:";
            case "choose": return "Выберите:";
            case "back": return "0 - Назад";
            case "bye": return "До свидания.";
            case "unknown.choice": return "Неизвестный выбор.";
            case "enter.number": return "Введите число.";
            case "enter.decimal": return "Введите десятичное число.";
            case "admin.title": return "--- Администратор ---";
            case "admin.show.users": return "1 - Показать пользователей";
            case "admin.add.user": return "2 - Добавить пользователя";
            case "admin.remove.user": return "3 - Удалить пользователя";
            case "admin.show.logs": return "4 - Показать логи";
            case "admin.save": return "5 - Сохранить данные";
            case "admin.load": return "6 - Загрузить данные";
            case "roles": return "Роли: 1-СТУДЕНТ, 2-МАГИСТРАНТ, 3-ПРЕПОДАВАТЕЛЬ, 4-МЕНЕДЖЕР, 5-АДМИН, 6-ТЕХПОДДЕРЖКА";
            case "role": return "Роль:";
            case "invalid.role": return "Неверная роль.";
            case "id": return "Id:";
            case "name": return "Имя:";
            case "email": return "Email:";
            case "password": return "Пароль:";
            case "added": return "Добавлено:";
            case "user.id": return "Id пользователя:";
            case "removed.user": return "Пользователь удален, если существовал:";
            case "data.saved": return "Данные сохранены.";
            case "data.loaded": return "Данные загружены.";
            case "education.title": return "--- Обучение ---";
            case "education.show.courses": return "1 - Показать курсы";
            case "education.register": return "2 - Зарегистрировать студента на курс";
            case "education.approve": return "3 - Одобрить ожидающую регистрацию";
            case "education.put.mark": return "4 - Поставить оценку";
            case "education.transcript": return "5 - Показать транскрипт";
            case "education.teachers": return "6 - Показать преподавателей курса";
            case "education.reject": return "7 - Отклонить ожидающую регистрацию";
            case "created.enrollment": return "Создана регистрация:";
            case "approved": return "Одобрено:";
            case "rejected": return "Отклонено:";
            case "no.enrollment": return "Регистрация не найдена. Сначала зарегистрируйтесь.";
            case "no.approved.enrollment": return "Утвержденная регистрация не найдена.";
            case "attestation1": return "Аттестация 1:";
            case "attestation2": return "Аттестация 2:";
            case "final.exam": return "Финальный экзамен:";
            case "mark.saved": return "Оценка сохранена:";
            case "lecture": return "Лекция:";
            case "practice": return "Практика:";
            case "manager.title": return "--- Менеджер ---";
            case "manager.add.course": return "1 - Добавить курс для регистрации";
            case "manager.assign.teacher": return "2 - Назначить преподавателя на курс";
            case "manager.students.sorted": return "3 - Показать студентов с сортировкой";
            case "manager.teachers.sorted": return "4 - Показать преподавателей с сортировкой";
            case "manager.report": return "5 - Создать академический отчет";
            case "manager.news": return "6 - Управлять новостями";
            case "manager.official": return "7 - Официальный запрос и жалоба";
            case "course.id": return "Id курса:";
            case "course.name": return "Название курса:";
            case "course.type": return "Тип курса: 1-MAJOR, 2-MINOR, 3-FREE_ELECTIVE";
            case "credits": return "Кредиты:";
            case "credits.word": return "кредитов";
            case "capacity": return "Вместимость:";
            case "major": return "Специальность";
            case "year": return "Год";
            case "student.id": return "Id студента:";
            case "teacher.id": return "Id преподавателя:";
            case "offering.id": return "Id предложения курса:";
            case "enrollment.id": return "Id регистрации:";
            case "status": return "статус";
            case "added.course": return "Курс добавлен:";
            case "assigned.practice.teacher": return "Преподаватель назначен:";
            case "sort.students": return "1 - по GPA, 2 - по имени";
            case "sort.teachers": return "1 - по рейтингу, 2 - по имени";
            case "sort": return "Сортировка:";
            case "news.title": return "Заголовок новости:";
            case "news.content": return "Текст новости:";
            case "created.news": return "Новость создана:";
            case "research.title": return "--- Исследования ---";
            case "hindex": return "H-index:";
            case "supervisor.assigned": return "Научный руководитель назначен:";
            case "published.papers": return "Статьи опубликованы.";
            case "sort.papers": return "1 - по цитированиям, 2 - по дате, 3 - по страницам";
            case "news": return "Новости:";
            case "no.news": return "Новостей нет.";
            case "student.notifications": return "Уведомления студента:";
            case "support.title": return "--- Техподдержка ---";
            case "support.create": return "1 - Создать запрос в техподдержку";
            case "support.assign": return "2 - Назначить первый запрос специалисту";
            case "support.accept": return "3 - Принять первый запрос";
            case "support.done": return "4 - Завершить первый запрос";
            case "support.show": return "5 - Показать запросы в техподдержку";
            case "description": return "Описание:";
            case "created": return "Создано:";
            case "assigned": return "Назначено:";
            case "accepted": return "Принято:";
            case "done": return "Готово:";
            case "no.requests": return "Запросов нет.";
            case "request": return "запрос";
            case "no.student": return "Студент не найден. Сначала добавьте студента в приложении администратора.";
            case "no.manager": return "Менеджер не найден. Сначала добавьте менеджера в приложении администратора.";
            case "no.teacher": return "Преподаватель не найден. Сначала добавьте преподавателя в приложении администратора.";
            case "no.support.specialist": return "Специалист техподдержки не найден. Сначала добавьте его в приложении администратора.";
            case "no.course": return "Курс не найден.";
            case "no.offering": return "Предложение курса не найдено. Сначала создайте его в приложении менеджера.";
            case "no.journal": return "Журнал не найден. Сначала создайте журнал.";
            case "no.researcher": return "Исследователь не найден. Сначала добавьте магистранта или профессора.";
            case "no.graduate.student": return "Магистрант не найден. Сначала добавьте его в приложении администратора.";
            case "no.pending": return "Ожидающих регистраций нет.";
            case "journal.id": return "Id журнала:";
            case "journal.title": return "Название журнала:";
            case "created.journal": return "Журнал создан:";
            case "paper.id": return "Id статьи:";
            case "paper.title": return "Название статьи:";
            case "paper.citations": return "Цитирования:";
            case "paper.pages": return "Страницы:";
            case "paper.doi": return "Doi:";
            case "project.id": return "Id проекта:";
            case "project.topic": return "Тема:";
            case "official.description": return "Описание официального запроса:";
            case "complaint.text": return "Текст жалобы:";
            case "research.create.journal": return "1 - Создать журнал";
            case "research.publish": return "2 - Опубликовать статью";
            case "research.hindex": return "3 - Показать h-index первого исследователя";
            case "research.sorted": return "4 - Напечатать статьи с сортировкой";
            case "research.supervisor": return "5 - Назначить научного руководителя";
            case "research.join": return "6 - Вступить в исследовательский проект";
            case "research.news": return "7 - Показать новости и уведомления";
            default: return en(key);
        }
    }

    private static String kz(String key) {
        switch (key) {
            case "choose.language.title": return "=== Тілді таңдаңыз / Выберите язык / Choose language ===";
            case "choose.language.prompt": return "Тіл:";
            case "selected.language": return "Таңдалған тіл:";
            case "launcher.title": return "=== WSP іске қосқышы ===";
            case "launcher.admin": return "1 - Әкімші қосымшасы";
            case "launcher.education": return "2 - Оқу қосымшасы";
            case "launcher.manager": return "3 - Менеджер қосымшасы";
            case "launcher.research": return "4 - Зерттеу қосымшасы";
            case "launcher.support": return "5 - Техникалық қолдау қосымшасы";
            case "launcher.exit": return "0 - Шығу";
            case "launcher.logout": return "0 - Аккаунттан шығу";
            case "login.title": return "=== Кіру ===";
            case "logged.in": return "Кірген пайдаланушы:";
            case "logged.out": return "Аккаунттан шықтыңыз.";
            case "login.failed": return "Email немесе құпиясөз қате.";
            case "current.user": return "Қазіргі пайдаланушы:";
            case "access.denied": return "Бұл рөлге кіруге рұқсат жоқ.";
            case "bootstrap.admin.created": return "Бастапқы админ жасалды: admin@uni.kz / admin";
            case "choose.app": return "Қосымшаны таңдаңыз:";
            case "choose": return "Таңдаңыз:";
            case "back": return "0 - Артқа";
            case "bye": return "Сау болыңыз.";
            case "unknown.choice": return "Белгісіз таңдау.";
            case "enter.number": return "Сан енгізіңіз.";
            case "enter.decimal": return "Ондық сан енгізіңіз.";
            case "admin.title": return "--- Әкімші ---";
            case "admin.show.users": return "1 - Пайдаланушыларды көрсету";
            case "admin.add.user": return "2 - Пайдаланушы қосу";
            case "admin.remove.user": return "3 - Пайдаланушыны жою";
            case "admin.show.logs": return "4 - Логтарды көрсету";
            case "admin.save": return "5 - Деректерді сақтау";
            case "admin.load": return "6 - Деректерді жүктеу";
            case "roles": return "Рөлдер: 1-СТУДЕНТ, 2-МАГИСТРАНТ, 3-ОҚЫТУШЫ, 4-МЕНЕДЖЕР, 5-ӘКІМШІ, 6-ТЕХҚОЛДАУ";
            case "role": return "Рөл:";
            case "invalid.role": return "Рөл дұрыс емес.";
            case "id": return "Id:";
            case "name": return "Аты:";
            case "email": return "Email:";
            case "password": return "Құпиясөз:";
            case "added": return "Қосылды:";
            case "user.id": return "Пайдаланушы id:";
            case "removed.user": return "Пайдаланушы бар болса жойылды:";
            case "data.saved": return "Деректер сақталды.";
            case "data.loaded": return "Деректер жүктелді.";
            case "education.title": return "--- Оқу ---";
            case "education.show.courses": return "1 - Курстарды көрсету";
            case "education.register": return "2 - Студентті курсқа тіркеу";
            case "education.approve": return "3 - Күтіп тұрған тіркеуді мақұлдау";
            case "education.put.mark": return "4 - Баға қою";
            case "education.transcript": return "5 - Транскрипт көрсету";
            case "education.teachers": return "6 - Курс оқытушыларын көрсету";
            case "education.reject": return "7 - Күтіп тұрған тіркеуді қабылдамау";
            case "created.enrollment": return "Тіркеу жасалды:";
            case "approved": return "Мақұлданды:";
            case "rejected": return "Қабылданбады:";
            case "no.enrollment": return "Тіркеу табылмады. Алдымен тіркеліңіз.";
            case "no.approved.enrollment": return "Мақұлданған тіркеу табылмады.";
            case "attestation1": return "1-аттестация:";
            case "attestation2": return "2-аттестация:";
            case "final.exam": return "Қорытынды емтихан:";
            case "mark.saved": return "Баға сақталды:";
            case "lecture": return "Лекция:";
            case "practice": return "Практика:";
            case "manager.title": return "--- Менеджер ---";
            case "manager.add.course": return "1 - Тіркеуге курс қосу";
            case "manager.assign.teacher": return "2 - Оқытушыны курсқа тағайындау";
            case "manager.students.sorted": return "3 - Студенттерді сұрыптап көрсету";
            case "manager.teachers.sorted": return "4 - Оқытушыларды сұрыптап көрсету";
            case "manager.report": return "5 - Академиялық есеп жасау";
            case "manager.news": return "6 - Жаңалықтарды басқару";
            case "manager.official": return "7 - Ресми сұраныс және шағым";
            case "course.id": return "Курс id:";
            case "course.name": return "Курс атауы:";
            case "course.type": return "Курс түрі: 1-MAJOR, 2-MINOR, 3-FREE_ELECTIVE";
            case "credits": return "Кредиттер:";
            case "credits.word": return "кредит";
            case "capacity": return "Орын саны:";
            case "major": return "Мамандық";
            case "year": return "Жыл";
            case "student.id": return "Студент id:";
            case "teacher.id": return "Оқытушы id:";
            case "offering.id": return "Курс ұсынысының id:";
            case "enrollment.id": return "Тіркеу id:";
            case "status": return "күйі";
            case "added.course": return "Курс қосылды:";
            case "assigned.practice.teacher": return "Оқытушы тағайындалды:";
            case "sort.students": return "1 - GPA бойынша, 2 - аты бойынша";
            case "sort.teachers": return "1 - рейтинг бойынша, 2 - аты бойынша";
            case "sort": return "Сұрыптау:";
            case "news.title": return "Жаңалық тақырыбы:";
            case "news.content": return "Жаңалық мәтіні:";
            case "created.news": return "Жаңалық жасалды:";
            case "research.title": return "--- Зерттеу ---";
            case "hindex": return "H-index:";
            case "supervisor.assigned": return "Ғылыми жетекші тағайындалды:";
            case "published.papers": return "Мақалалар жарияланды.";
            case "sort.papers": return "1 - дәйексөз бойынша, 2 - күн бойынша, 3 - бет саны бойынша";
            case "news": return "Жаңалықтар:";
            case "no.news": return "Жаңалықтар жоқ.";
            case "student.notifications": return "Студент хабарламалары:";
            case "support.title": return "--- Техникалық қолдау ---";
            case "support.create": return "1 - Техқолдауға сұраныс жасау";
            case "support.assign": return "2 - Бірінші сұранысты маманға тағайындау";
            case "support.accept": return "3 - Бірінші сұранысты қабылдау";
            case "support.done": return "4 - Бірінші сұранысты аяқтау";
            case "support.show": return "5 - Техқолдау сұраныстарын көрсету";
            case "description": return "Сипаттама:";
            case "created": return "Жасалды:";
            case "assigned": return "Тағайындалды:";
            case "accepted": return "Қабылданды:";
            case "done": return "Аяқталды:";
            case "no.requests": return "Сұраныстар жоқ.";
            case "request": return "сұраныс";
            case "no.student": return "Студент табылмады. Алдымен әкімші қосымшасында студент қосыңыз.";
            case "no.manager": return "Менеджер табылмады. Алдымен әкімші қосымшасында менеджер қосыңыз.";
            case "no.teacher": return "Оқытушы табылмады. Алдымен әкімші қосымшасында оқытушы қосыңыз.";
            case "no.support.specialist": return "Техқолдау маманы табылмады. Алдымен әкімші қосымшасында қосыңыз.";
            case "no.course": return "Курс табылмады.";
            case "no.offering": return "Курс ұсынысы табылмады. Алдымен менеджер қосымшасында жасаңыз.";
            case "no.journal": return "Журнал табылмады. Алдымен журнал жасаңыз.";
            case "no.researcher": return "Зерттеуші табылмады. Алдымен магистрант немесе профессор қосыңыз.";
            case "no.graduate.student": return "Магистрант табылмады. Алдымен әкімші қосымшасында қосыңыз.";
            case "no.pending": return "Күтіп тұрған тіркеулер жоқ.";
            case "journal.id": return "Журнал id:";
            case "journal.title": return "Журнал атауы:";
            case "created.journal": return "Журнал жасалды:";
            case "paper.id": return "Мақала id:";
            case "paper.title": return "Мақала атауы:";
            case "paper.citations": return "Дәйексөздер:";
            case "paper.pages": return "Беттер:";
            case "paper.doi": return "Doi:";
            case "project.id": return "Жоба id:";
            case "project.topic": return "Тақырып:";
            case "official.description": return "Ресми сұраныс сипаттамасы:";
            case "complaint.text": return "Шағым мәтіні:";
            case "research.create.journal": return "1 - Журнал жасау";
            case "research.publish": return "2 - Мақала жариялау";
            case "research.hindex": return "3 - Бірінші зерттеушінің h-index көрсету";
            case "research.sorted": return "4 - Мақалаларды сұрыптап шығару";
            case "research.supervisor": return "5 - Ғылыми жетекші тағайындау";
            case "research.join": return "6 - Зерттеу жобасына қосылу";
            case "research.news": return "7 - Жаңалықтар мен хабарламаларды көрсету";
            default: return en(key);
        }
    }
}

