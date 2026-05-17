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

    public static String courseType(CourseType type) {
        if (AppLanguage.selected() == Language.KZ) {
            switch (type) {
                case MAJOR: return "РјР°РјР°РЅРґС‹Т› РєСѓСЂСЃС‹";
                case MINOR: return "minor РєСѓСЂСЃС‹";
                case FREE_ELECTIVE: return "РµСЂРєС–РЅ С‚Р°ТЈРґР°Сѓ РєСѓСЂСЃС‹";
                default: return type.name();
            }
        }
        if (AppLanguage.selected() == Language.RU) {
            switch (type) {
                case MAJOR: return "РїСЂРѕС„РёР»СЊРЅС‹Р№ РєСѓСЂСЃ";
                case MINOR: return "minor РєСѓСЂСЃ";
                case FREE_ELECTIVE: return "РєСѓСЂСЃ СЃРІРѕР±РѕРґРЅРѕРіРѕ РІС‹Р±РѕСЂР°";
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
                case PENDING: return "РєТЇС‚С–Р»СѓРґРµ";
                case APPROVED: return "РјР°Т›Т±Р»РґР°РЅРґС‹";
                case REJECTED: return "Т›Р°Р±С‹Р»РґР°РЅР±Р°РґС‹";
                default: return status.name();
            }
        }
        if (AppLanguage.selected() == Language.RU) {
            switch (status) {
                case PENDING: return "РѕР¶РёРґР°РµС‚";
                case APPROVED: return "РѕРґРѕР±СЂРµРЅРѕ";
                case REJECTED: return "РѕС‚РєР»РѕРЅРµРЅРѕ";
                default: return status.name();
            }
        }
        return status.name().toLowerCase();
    }

    public static String requestStatus(RequestStatus status) {
        if (AppLanguage.selected() == Language.KZ) {
            switch (status) {
                case NEW: return "Р¶Р°ТЈР°";
                case VIEWED: return "Т›Р°СЂР°Р»РґС‹";
                case ACCEPTED: return "Т›Р°Р±С‹Р»РґР°РЅРґС‹";
                case REJECTED: return "Т›Р°Р±С‹Р»РґР°РЅР±Р°РґС‹";
                case DONE: return "Р°СЏТ›С‚Р°Р»РґС‹";
                default: return status.name();
            }
        }
        if (AppLanguage.selected() == Language.RU) {
            switch (status) {
                case NEW: return "РЅРѕРІС‹Р№";
                case VIEWED: return "РїСЂРѕСЃРјРѕС‚СЂРµРЅ";
                case ACCEPTED: return "РїСЂРёРЅСЏС‚";
                case REJECTED: return "РѕС‚РєР»РѕРЅРµРЅ";
                case DONE: return "РіРѕС‚РѕРІ";
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
            case "education.register": return "2 - Register student to OOP";
            case "education.approve": return "3 - Approve pending enrollments";
            case "education.put.mark": return "4 - Put mark";
            case "education.transcript": return "5 - Show transcript";
            case "education.teachers": return "6 - Show course teachers";
            case "created.enrollment": return "Created enrollment:";
            case "approved": return "Approved:";
            case "no.enrollment": return "No enrollment found. Register first.";
            case "attestation1": return "Attestation 1:";
            case "attestation2": return "Attestation 2:";
            case "final.exam": return "Final exam:";
            case "mark.saved": return "Mark saved:";
            case "lecture": return "Lecture:";
            case "practice": return "Practice:";
            case "manager.title": return "--- Manager app ---";
            case "manager.add.course": return "1 - Add course for registration";
            case "manager.assign.teacher": return "2 - Assign OOP practice teacher";
            case "manager.students.sorted": return "3 - Show students sorted";
            case "manager.teachers.sorted": return "4 - Show teachers sorted";
            case "manager.report": return "5 - Create academic report";
            case "manager.news": return "6 - Manage news";
            case "manager.official": return "7 - Official request and complaint";
            case "course.id": return "Course id:";
            case "course.name": return "Course name:";
            case "credits": return "Credits:";
            case "credits.word": return "credits";
            case "status": return "status";
            case "added.course": return "Added course:";
            case "assigned.practice.teacher": return "Assigned practice teacher:";
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
            case "choose.language.title": return "=== Р’С‹Р±РµСЂРёС‚Рµ СЏР·С‹Рє / РўС–Р»РґС– С‚Р°ТЈРґР°ТЈС‹Р· / Choose language ===";
            case "choose.language.prompt": return "РЇР·С‹Рє:";
            case "selected.language": return "Р’С‹Р±СЂР°РЅРЅС‹Р№ СЏР·С‹Рє:";
            case "launcher.title": return "=== Р—Р°РїСѓСЃРєР°С‚РµР»СЊ WSP ===";
            case "launcher.admin": return "1 - РџСЂРёР»РѕР¶РµРЅРёРµ Р°РґРјРёРЅРёСЃС‚СЂР°С‚РѕСЂР°";
            case "launcher.education": return "2 - РЈС‡РµР±РЅРѕРµ РїСЂРёР»РѕР¶РµРЅРёРµ";
            case "launcher.manager": return "3 - РџСЂРёР»РѕР¶РµРЅРёРµ РјРµРЅРµРґР¶РµСЂР°";
            case "launcher.research": return "4 - РСЃСЃР»РµРґРѕРІР°С‚РµР»СЊСЃРєРѕРµ РїСЂРёР»РѕР¶РµРЅРёРµ";
            case "launcher.support": return "5 - РџСЂРёР»РѕР¶РµРЅРёРµ С‚РµС…РїРѕРґРґРµСЂР¶РєРё";
            case "launcher.exit": return "0 - Р’С‹С…РѕРґ";
            case "choose.app": return "Р’С‹Р±РµСЂРёС‚Рµ РїСЂРёР»РѕР¶РµРЅРёРµ:";
            case "choose": return "Р’С‹Р±РµСЂРёС‚Рµ:";
            case "back": return "0 - РќР°Р·Р°Рґ";
            case "bye": return "Р”Рѕ СЃРІРёРґР°РЅРёСЏ.";
            case "unknown.choice": return "РќРµРёР·РІРµСЃС‚РЅС‹Р№ РІС‹Р±РѕСЂ.";
            case "enter.number": return "Р’РІРµРґРёС‚Рµ С‡РёСЃР»Рѕ.";
            case "enter.decimal": return "Р’РІРµРґРёС‚Рµ РґРµСЃСЏС‚РёС‡РЅРѕРµ С‡РёСЃР»Рѕ.";
            case "admin.title": return "--- РђРґРјРёРЅРёСЃС‚СЂР°С‚РѕСЂ ---";
            case "admin.show.users": return "1 - РџРѕРєР°Р·Р°С‚СЊ РїРѕР»СЊР·РѕРІР°С‚РµР»РµР№";
            case "admin.add.user": return "2 - Р”РѕР±Р°РІРёС‚СЊ РїРѕР»СЊР·РѕРІР°С‚РµР»СЏ";
            case "admin.remove.user": return "3 - РЈРґР°Р»РёС‚СЊ РїРѕР»СЊР·РѕРІР°С‚РµР»СЏ";
            case "admin.show.logs": return "4 - РџРѕРєР°Р·Р°С‚СЊ Р»РѕРіРё";
            case "admin.save": return "5 - РЎРѕС…СЂР°РЅРёС‚СЊ РґР°РЅРЅС‹Рµ";
            case "admin.load": return "6 - Р—Р°РіСЂСѓР·РёС‚СЊ РґР°РЅРЅС‹Рµ";
            case "roles": return "Р РѕР»Рё: 1-РЎРўРЈР”Р•РќРў, 2-РњРђР“РРЎРўР РђРќРў, 3-РџР Р•РџРћР”РђР’РђРўР•Р›Р¬, 4-РњР•РќР•Р”Р–Р•Р , 5-РђР”РњРРќ, 6-РўР•РҐРџРћР”Р”Р•Р Р–РљРђ";
            case "role": return "Р РѕР»СЊ:";
            case "invalid.role": return "РќРµРІРµСЂРЅР°СЏ СЂРѕР»СЊ.";
            case "id": return "Id:";
            case "name": return "РРјСЏ:";
            case "email": return "Email:";
            case "password": return "РџР°СЂРѕР»СЊ:";
            case "added": return "Р”РѕР±Р°РІР»РµРЅРѕ:";
            case "user.id": return "Id РїРѕР»СЊР·РѕРІР°С‚РµР»СЏ:";
            case "removed.user": return "РџРѕР»СЊР·РѕРІР°С‚РµР»СЊ СѓРґР°Р»РµРЅ, РµСЃР»Рё СЃСѓС‰РµСЃС‚РІРѕРІР°Р»:";
            case "data.saved": return "Р”Р°РЅРЅС‹Рµ СЃРѕС…СЂР°РЅРµРЅС‹.";
            case "data.loaded": return "Р”Р°РЅРЅС‹Рµ Р·Р°РіСЂСѓР¶РµРЅС‹.";
            case "education.title": return "--- РћР±СѓС‡РµРЅРёРµ ---";
            case "education.show.courses": return "1 - РџРѕРєР°Р·Р°С‚СЊ РєСѓСЂСЃС‹";
            case "education.register": return "2 - Р—Р°СЂРµРіРёСЃС‚СЂРёСЂРѕРІР°С‚СЊ СЃС‚СѓРґРµРЅС‚Р° РЅР° OOP";
            case "education.approve": return "3 - РћРґРѕР±СЂРёС‚СЊ РѕР¶РёРґР°СЋС‰РёРµ СЂРµРіРёСЃС‚СЂР°С†РёРё";
            case "education.put.mark": return "4 - РџРѕСЃС‚Р°РІРёС‚СЊ РѕС†РµРЅРєСѓ";
            case "education.transcript": return "5 - РџРѕРєР°Р·Р°С‚СЊ С‚СЂР°РЅСЃРєСЂРёРїС‚";
            case "education.teachers": return "6 - РџРѕРєР°Р·Р°С‚СЊ РїСЂРµРїРѕРґР°РІР°С‚РµР»РµР№ РєСѓСЂСЃР°";
            case "created.enrollment": return "РЎРѕР·РґР°РЅР° СЂРµРіРёСЃС‚СЂР°С†РёСЏ:";
            case "approved": return "РћРґРѕР±СЂРµРЅРѕ:";
            case "no.enrollment": return "Р РµРіРёСЃС‚СЂР°С†РёСЏ РЅРµ РЅР°Р№РґРµРЅР°. РЎРЅР°С‡Р°Р»Р° Р·Р°СЂРµРіРёСЃС‚СЂРёСЂСѓР№С‚РµСЃСЊ.";
            case "attestation1": return "РђС‚С‚РµСЃС‚Р°С†РёСЏ 1:";
            case "attestation2": return "РђС‚С‚РµСЃС‚Р°С†РёСЏ 2:";
            case "final.exam": return "Р¤РёРЅР°Р»СЊРЅС‹Р№ СЌРєР·Р°РјРµРЅ:";
            case "mark.saved": return "РћС†РµРЅРєР° СЃРѕС…СЂР°РЅРµРЅР°:";
            case "lecture": return "Р›РµРєС†РёСЏ:";
            case "practice": return "РџСЂР°РєС‚РёРєР°:";
            case "manager.title": return "--- РњРµРЅРµРґР¶РµСЂ ---";
            case "manager.add.course": return "1 - Р”РѕР±Р°РІРёС‚СЊ РєСѓСЂСЃ РґР»СЏ СЂРµРіРёСЃС‚СЂР°С†РёРё";
            case "manager.assign.teacher": return "2 - РќР°Р·РЅР°С‡РёС‚СЊ РїСЂРµРїРѕРґР°РІР°С‚РµР»СЏ РїСЂР°РєС‚РёРєРё OOP";
            case "manager.students.sorted": return "3 - РџРѕРєР°Р·Р°С‚СЊ СЃС‚СѓРґРµРЅС‚РѕРІ СЃ СЃРѕСЂС‚РёСЂРѕРІРєРѕР№";
            case "manager.teachers.sorted": return "4 - РџРѕРєР°Р·Р°С‚СЊ РїСЂРµРїРѕРґР°РІР°С‚РµР»РµР№ СЃ СЃРѕСЂС‚РёСЂРѕРІРєРѕР№";
            case "manager.report": return "5 - РЎРѕР·РґР°С‚СЊ Р°РєР°РґРµРјРёС‡РµСЃРєРёР№ РѕС‚С‡РµС‚";
            case "manager.news": return "6 - РЈРїСЂР°РІР»СЏС‚СЊ РЅРѕРІРѕСЃС‚СЏРјРё";
            case "manager.official": return "7 - РћС„РёС†РёР°Р»СЊРЅС‹Р№ Р·Р°РїСЂРѕСЃ Рё Р¶Р°Р»РѕР±Р°";
            case "course.id": return "Id РєСѓСЂСЃР°:";
            case "course.name": return "РќР°Р·РІР°РЅРёРµ РєСѓСЂСЃР°:";
            case "credits": return "РљСЂРµРґРёС‚С‹:";
            case "credits.word": return "РєСЂРµРґРёС‚РѕРІ";
            case "status": return "СЃС‚Р°С‚СѓСЃ";
            case "added.course": return "РљСѓСЂСЃ РґРѕР±Р°РІР»РµРЅ:";
            case "assigned.practice.teacher": return "РџСЂРµРїРѕРґР°РІР°С‚РµР»СЊ РїСЂР°РєС‚РёРєРё РЅР°Р·РЅР°С‡РµРЅ:";
            case "sort.students": return "1 - РїРѕ GPA, 2 - РїРѕ РёРјРµРЅРё";
            case "sort.teachers": return "1 - РїРѕ СЂРµР№С‚РёРЅРіСѓ, 2 - РїРѕ РёРјРµРЅРё";
            case "sort": return "РЎРѕСЂС‚РёСЂРѕРІРєР°:";
            case "news.title": return "Р—Р°РіРѕР»РѕРІРѕРє РЅРѕРІРѕСЃС‚Рё:";
            case "news.content": return "РўРµРєСЃС‚ РЅРѕРІРѕСЃС‚Рё:";
            case "created.news": return "РќРѕРІРѕСЃС‚СЊ СЃРѕР·РґР°РЅР°:";
            case "research.title": return "--- РСЃСЃР»РµРґРѕРІР°РЅРёСЏ ---";
            case "hindex": return "H-index:";
            case "supervisor.assigned": return "РќР°СѓС‡РЅС‹Р№ СЂСѓРєРѕРІРѕРґРёС‚РµР»СЊ РЅР°Р·РЅР°С‡РµРЅ:";
            case "published.papers": return "РЎС‚Р°С‚СЊРё РѕРїСѓР±Р»РёРєРѕРІР°РЅС‹.";
            case "sort.papers": return "1 - РїРѕ С†РёС‚РёСЂРѕРІР°РЅРёСЏРј, 2 - РїРѕ РґР°С‚Рµ, 3 - РїРѕ СЃС‚СЂР°РЅРёС†Р°Рј";
            case "news": return "РќРѕРІРѕСЃС‚Рё:";
            case "no.news": return "РќРѕРІРѕСЃС‚РµР№ РЅРµС‚.";
            case "student.notifications": return "РЈРІРµРґРѕРјР»РµРЅРёСЏ СЃС‚СѓРґРµРЅС‚Р°:";
            case "support.title": return "--- РўРµС…РїРѕРґРґРµСЂР¶РєР° ---";
            case "support.create": return "1 - РЎРѕР·РґР°С‚СЊ Р·Р°РїСЂРѕСЃ РІ С‚РµС…РїРѕРґРґРµСЂР¶РєСѓ";
            case "support.assign": return "2 - РќР°Р·РЅР°С‡РёС‚СЊ РїРµСЂРІС‹Р№ Р·Р°РїСЂРѕСЃ СЃРїРµС†РёР°Р»РёСЃС‚Сѓ";
            case "support.accept": return "3 - РџСЂРёРЅСЏС‚СЊ РїРµСЂРІС‹Р№ Р·Р°РїСЂРѕСЃ";
            case "support.done": return "4 - Р—Р°РІРµСЂС€РёС‚СЊ РїРµСЂРІС‹Р№ Р·Р°РїСЂРѕСЃ";
            case "support.show": return "5 - РџРѕРєР°Р·Р°С‚СЊ Р·Р°РїСЂРѕСЃС‹ РІ С‚РµС…РїРѕРґРґРµСЂР¶РєСѓ";
            case "description": return "РћРїРёСЃР°РЅРёРµ:";
            case "created": return "РЎРѕР·РґР°РЅРѕ:";
            case "assigned": return "РќР°Р·РЅР°С‡РµРЅРѕ:";
            case "accepted": return "РџСЂРёРЅСЏС‚Рѕ:";
            case "done": return "Р“РѕС‚РѕРІРѕ:";
            case "no.requests": return "Р—Р°РїСЂРѕСЃРѕРІ РЅРµС‚.";
            case "request": return "Р·Р°РїСЂРѕСЃ";
            case "no.student": return "РЎС‚СѓРґРµРЅС‚ РЅРµ РЅР°Р№РґРµРЅ. РЎРЅР°С‡Р°Р»Р° РґРѕР±Р°РІСЊС‚Рµ СЃС‚СѓРґРµРЅС‚Р° РІ РїСЂРёР»РѕР¶РµРЅРёРё Р°РґРјРёРЅРёСЃС‚СЂР°С‚РѕСЂР°.";
            case "no.manager": return "РњРµРЅРµРґР¶РµСЂ РЅРµ РЅР°Р№РґРµРЅ. РЎРЅР°С‡Р°Р»Р° РґРѕР±Р°РІСЊС‚Рµ РјРµРЅРµРґР¶РµСЂР° РІ РїСЂРёР»РѕР¶РµРЅРёРё Р°РґРјРёРЅРёСЃС‚СЂР°С‚РѕСЂР°.";
            case "no.teacher": return "РџСЂРµРїРѕРґР°РІР°С‚РµР»СЊ РЅРµ РЅР°Р№РґРµРЅ. РЎРЅР°С‡Р°Р»Р° РґРѕР±Р°РІСЊС‚Рµ РїСЂРµРїРѕРґР°РІР°С‚РµР»СЏ РІ РїСЂРёР»РѕР¶РµРЅРёРё Р°РґРјРёРЅРёСЃС‚СЂР°С‚РѕСЂР°.";
            case "no.support.specialist": return "РЎРїРµС†РёР°Р»РёСЃС‚ С‚РµС…РїРѕРґРґРµСЂР¶РєРё РЅРµ РЅР°Р№РґРµРЅ. РЎРЅР°С‡Р°Р»Р° РґРѕР±Р°РІСЊС‚Рµ РµРіРѕ РІ РїСЂРёР»РѕР¶РµРЅРёРё Р°РґРјРёРЅРёСЃС‚СЂР°С‚РѕСЂР°.";
            case "no.course": return "РљСѓСЂСЃ РЅРµ РЅР°Р№РґРµРЅ.";
            case "no.offering": return "РџСЂРµРґР»РѕР¶РµРЅРёРµ РєСѓСЂСЃР° РЅРµ РЅР°Р№РґРµРЅРѕ. РЎРЅР°С‡Р°Р»Р° СЃРѕР·РґР°Р№С‚Рµ РµРіРѕ РІ РїСЂРёР»РѕР¶РµРЅРёРё РјРµРЅРµРґР¶РµСЂР°.";
            case "no.journal": return "Р–СѓСЂРЅР°Р» РЅРµ РЅР°Р№РґРµРЅ. РЎРЅР°С‡Р°Р»Р° СЃРѕР·РґР°Р№С‚Рµ Р¶СѓСЂРЅР°Р».";
            case "no.researcher": return "РСЃСЃР»РµРґРѕРІР°С‚РµР»СЊ РЅРµ РЅР°Р№РґРµРЅ. РЎРЅР°С‡Р°Р»Р° РґРѕР±Р°РІСЊС‚Рµ РјР°РіРёСЃС‚СЂР°РЅС‚Р° РёР»Рё РїСЂРѕС„РµСЃСЃРѕСЂР°.";
            case "no.graduate.student": return "РњР°РіРёСЃС‚СЂР°РЅС‚ РЅРµ РЅР°Р№РґРµРЅ. РЎРЅР°С‡Р°Р»Р° РґРѕР±Р°РІСЊС‚Рµ РµРіРѕ РІ РїСЂРёР»РѕР¶РµРЅРёРё Р°РґРјРёРЅРёСЃС‚СЂР°С‚РѕСЂР°.";
            case "no.pending": return "РћР¶РёРґР°СЋС‰РёС… СЂРµРіРёСЃС‚СЂР°С†РёР№ РЅРµС‚.";
            case "journal.id": return "Id Р¶СѓСЂРЅР°Р»Р°:";
            case "journal.title": return "РќР°Р·РІР°РЅРёРµ Р¶СѓСЂРЅР°Р»Р°:";
            case "created.journal": return "Р–СѓСЂРЅР°Р» СЃРѕР·РґР°РЅ:";
            case "paper.id": return "Id СЃС‚Р°С‚СЊРё:";
            case "paper.title": return "РќР°Р·РІР°РЅРёРµ СЃС‚Р°С‚СЊРё:";
            case "paper.citations": return "Р¦РёС‚РёСЂРѕРІР°РЅРёСЏ:";
            case "paper.pages": return "РЎС‚СЂР°РЅРёС†С‹:";
            case "paper.doi": return "Doi:";
            case "project.id": return "Id РїСЂРѕРµРєС‚Р°:";
            case "project.topic": return "РўРµРјР°:";
            case "official.description": return "РћРїРёСЃР°РЅРёРµ РѕС„РёС†РёР°Р»СЊРЅРѕРіРѕ Р·Р°РїСЂРѕСЃР°:";
            case "complaint.text": return "РўРµРєСЃС‚ Р¶Р°Р»РѕР±С‹:";
            case "research.create.journal": return "1 - РЎРѕР·РґР°С‚СЊ Р¶СѓСЂРЅР°Р»";
            case "research.publish": return "2 - РћРїСѓР±Р»РёРєРѕРІР°С‚СЊ СЃС‚Р°С‚СЊСЋ";
            case "research.hindex": return "3 - РџРѕРєР°Р·Р°С‚СЊ h-index РїРµСЂРІРѕРіРѕ РёСЃСЃР»РµРґРѕРІР°С‚РµР»СЏ";
            case "research.sorted": return "4 - РќР°РїРµС‡Р°С‚Р°С‚СЊ СЃС‚Р°С‚СЊРё СЃ СЃРѕСЂС‚РёСЂРѕРІРєРѕР№";
            case "research.supervisor": return "5 - РќР°Р·РЅР°С‡РёС‚СЊ РЅР°СѓС‡РЅРѕРіРѕ СЂСѓРєРѕРІРѕРґРёС‚РµР»СЏ";
            case "research.join": return "6 - Р’СЃС‚СѓРїРёС‚СЊ РІ РёСЃСЃР»РµРґРѕРІР°С‚РµР»СЊСЃРєРёР№ РїСЂРѕРµРєС‚";
            case "research.news": return "7 - РџРѕРєР°Р·Р°С‚СЊ РЅРѕРІРѕСЃС‚Рё Рё СѓРІРµРґРѕРјР»РµРЅРёСЏ";
            default: return en(key);
        }
    }

    private static String kz(String key) {
        switch (key) {
            case "choose.language.title": return "=== РўС–Р»РґС– С‚Р°ТЈРґР°ТЈС‹Р· / Р’С‹Р±РµСЂРёС‚Рµ СЏР·С‹Рє / Choose language ===";
            case "choose.language.prompt": return "РўС–Р»:";
            case "selected.language": return "РўР°ТЈРґР°Р»Т“Р°РЅ С‚С–Р»:";
            case "launcher.title": return "=== WSP С–СЃРєРµ Т›РѕСЃТ›С‹С€С‹ ===";
            case "launcher.admin": return "1 - УРєС–РјС€С– Т›РѕСЃС‹РјС€Р°СЃС‹";
            case "launcher.education": return "2 - РћТ›Сѓ Т›РѕСЃС‹РјС€Р°СЃС‹";
            case "launcher.manager": return "3 - РњРµРЅРµРґР¶РµСЂ Т›РѕСЃС‹РјС€Р°СЃС‹";
            case "launcher.research": return "4 - Р—РµСЂС‚С‚РµСѓ Т›РѕСЃС‹РјС€Р°СЃС‹";
            case "launcher.support": return "5 - РўРµС…РЅРёРєР°Р»С‹Т› Т›РѕР»РґР°Сѓ Т›РѕСЃС‹РјС€Р°СЃС‹";
            case "launcher.exit": return "0 - РЁС‹Т“Сѓ";
            case "choose.app": return "ТљРѕСЃС‹РјС€Р°РЅС‹ С‚Р°ТЈРґР°ТЈС‹Р·:";
            case "choose": return "РўР°ТЈРґР°ТЈС‹Р·:";
            case "back": return "0 - РђСЂС‚Т›Р°";
            case "bye": return "РЎР°Сѓ Р±РѕР»С‹ТЈС‹Р·.";
            case "unknown.choice": return "Р‘РµР»РіС–СЃС–Р· С‚Р°ТЈРґР°Сѓ.";
            case "enter.number": return "РЎР°РЅ РµРЅРіС–Р·С–ТЈС–Р·.";
            case "enter.decimal": return "РћРЅРґС‹Т› СЃР°РЅ РµРЅРіС–Р·С–ТЈС–Р·.";
            case "admin.title": return "--- УРєС–РјС€С– ---";
            case "admin.show.users": return "1 - РџР°Р№РґР°Р»Р°РЅСѓС€С‹Р»Р°СЂРґС‹ РєУ©СЂСЃРµС‚Сѓ";
            case "admin.add.user": return "2 - РџР°Р№РґР°Р»Р°РЅСѓС€С‹ Т›РѕСЃСѓ";
            case "admin.remove.user": return "3 - РџР°Р№РґР°Р»Р°РЅСѓС€С‹РЅС‹ Р¶РѕСЋ";
            case "admin.show.logs": return "4 - Р›РѕРіС‚Р°СЂРґС‹ РєУ©СЂСЃРµС‚Сѓ";
            case "admin.save": return "5 - Р”РµСЂРµРєС‚РµСЂРґС– СЃР°Т›С‚Р°Сѓ";
            case "admin.load": return "6 - Р”РµСЂРµРєС‚РµСЂРґС– Р¶ТЇРєС‚РµСѓ";
            case "roles": return "Р У©Р»РґРµСЂ: 1-РЎРўРЈР”Р•РќРў, 2-РњРђР“РРЎРўР РђРќРў, 3-РћТљР«РўРЈРЁР«, 4-РњР•РќР•Р”Р–Р•Р , 5-УРљР†РњРЁР†, 6-РўР•РҐТљРћР›Р”РђРЈ";
            case "role": return "Р У©Р»:";
            case "invalid.role": return "Р У©Р» РґТ±СЂС‹СЃ РµРјРµСЃ.";
            case "id": return "Id:";
            case "name": return "РђС‚С‹:";
            case "email": return "Email:";
            case "password": return "ТљТ±РїРёСЏСЃУ©Р·:";
            case "added": return "ТљРѕСЃС‹Р»РґС‹:";
            case "user.id": return "РџР°Р№РґР°Р»Р°РЅСѓС€С‹ id:";
            case "removed.user": return "РџР°Р№РґР°Р»Р°РЅСѓС€С‹ Р±Р°СЂ Р±РѕР»СЃР° Р¶РѕР№С‹Р»РґС‹:";
            case "data.saved": return "Р”РµСЂРµРєС‚РµСЂ СЃР°Т›С‚Р°Р»РґС‹.";
            case "data.loaded": return "Р”РµСЂРµРєС‚РµСЂ Р¶ТЇРєС‚РµР»РґС–.";
            case "education.title": return "--- РћТ›Сѓ ---";
            case "education.show.courses": return "1 - РљСѓСЂСЃС‚Р°СЂРґС‹ РєУ©СЂСЃРµС‚Сѓ";
            case "education.register": return "2 - РЎС‚СѓРґРµРЅС‚С‚С– OOP РєСѓСЂСЃС‹РЅР° С‚С–СЂРєРµСѓ";
            case "education.approve": return "3 - РљТЇС‚С–Рї С‚Т±СЂТ“Р°РЅ С‚С–СЂРєРµСѓР»РµСЂРґС– РјР°Т›Т±Р»РґР°Сѓ";
            case "education.put.mark": return "4 - Р‘Р°Т“Р° Т›РѕСЋ";
            case "education.transcript": return "5 - РўСЂР°РЅСЃРєСЂРёРїС‚ РєУ©СЂСЃРµС‚Сѓ";
            case "education.teachers": return "6 - РљСѓСЂСЃ РѕТ›С‹С‚СѓС€С‹Р»Р°СЂС‹РЅ РєУ©СЂСЃРµС‚Сѓ";
            case "created.enrollment": return "РўС–СЂРєРµСѓ Р¶Р°СЃР°Р»РґС‹:";
            case "approved": return "РњР°Т›Т±Р»РґР°РЅРґС‹:";
            case "no.enrollment": return "РўС–СЂРєРµСѓ С‚Р°Р±С‹Р»РјР°РґС‹. РђР»РґС‹РјРµРЅ С‚С–СЂРєРµР»С–ТЈС–Р·.";
            case "attestation1": return "1-Р°С‚С‚РµСЃС‚Р°С†РёСЏ:";
            case "attestation2": return "2-Р°С‚С‚РµСЃС‚Р°С†РёСЏ:";
            case "final.exam": return "ТљРѕСЂС‹С‚С‹РЅРґС‹ РµРјС‚РёС…Р°РЅ:";
            case "mark.saved": return "Р‘Р°Т“Р° СЃР°Т›С‚Р°Р»РґС‹:";
            case "lecture": return "Р›РµРєС†РёСЏ:";
            case "practice": return "РџСЂР°РєС‚РёРєР°:";
            case "manager.title": return "--- РњРµРЅРµРґР¶РµСЂ ---";
            case "manager.add.course": return "1 - РўС–СЂРєРµСѓРіРµ РєСѓСЂСЃ Т›РѕСЃСѓ";
            case "manager.assign.teacher": return "2 - OOP РїСЂР°РєС‚РёРєР° РѕТ›С‹С‚СѓС€С‹СЃС‹РЅ С‚Р°Т“Р°Р№С‹РЅРґР°Сѓ";
            case "manager.students.sorted": return "3 - РЎС‚СѓРґРµРЅС‚С‚РµСЂРґС– СЃТ±СЂС‹РїС‚Р°Рї РєУ©СЂСЃРµС‚Сѓ";
            case "manager.teachers.sorted": return "4 - РћТ›С‹С‚СѓС€С‹Р»Р°СЂРґС‹ СЃТ±СЂС‹РїС‚Р°Рї РєУ©СЂСЃРµС‚Сѓ";
            case "manager.report": return "5 - РђРєР°РґРµРјРёСЏР»С‹Т› РµСЃРµРї Р¶Р°СЃР°Сѓ";
            case "manager.news": return "6 - Р–Р°ТЈР°Р»С‹Т›С‚Р°СЂРґС‹ Р±Р°СЃТ›Р°СЂСѓ";
            case "manager.official": return "7 - Р РµСЃРјРё СЃТ±СЂР°РЅС‹СЃ Р¶У™РЅРµ С€Р°Т“С‹Рј";
            case "course.id": return "РљСѓСЂСЃ id:";
            case "course.name": return "РљСѓСЂСЃ Р°С‚Р°СѓС‹:";
            case "credits": return "РљСЂРµРґРёС‚С‚РµСЂ:";
            case "credits.word": return "РєСЂРµРґРёС‚";
            case "status": return "РєТЇР№С–";
            case "added.course": return "РљСѓСЂСЃ Т›РѕСЃС‹Р»РґС‹:";
            case "assigned.practice.teacher": return "РџСЂР°РєС‚РёРєР° РѕТ›С‹С‚СѓС€С‹СЃС‹ С‚Р°Т“Р°Р№С‹РЅРґР°Р»РґС‹:";
            case "sort.students": return "1 - GPA Р±РѕР№С‹РЅС€Р°, 2 - Р°С‚С‹ Р±РѕР№С‹РЅС€Р°";
            case "sort.teachers": return "1 - СЂРµР№С‚РёРЅРі Р±РѕР№С‹РЅС€Р°, 2 - Р°С‚С‹ Р±РѕР№С‹РЅС€Р°";
            case "sort": return "РЎТ±СЂС‹РїС‚Р°Сѓ:";
            case "news.title": return "Р–Р°ТЈР°Р»С‹Т› С‚Р°Т›С‹СЂС‹Р±С‹:";
            case "news.content": return "Р–Р°ТЈР°Р»С‹Т› РјУ™С‚С–РЅС–:";
            case "created.news": return "Р–Р°ТЈР°Р»С‹Т› Р¶Р°СЃР°Р»РґС‹:";
            case "research.title": return "--- Р—РµСЂС‚С‚РµСѓ ---";
            case "hindex": return "H-index:";
            case "supervisor.assigned": return "Т’С‹Р»С‹РјРё Р¶РµС‚РµРєС€С– С‚Р°Т“Р°Р№С‹РЅРґР°Р»РґС‹:";
            case "published.papers": return "РњР°Т›Р°Р»Р°Р»Р°СЂ Р¶Р°СЂРёСЏР»Р°РЅРґС‹.";
            case "sort.papers": return "1 - РґУ™Р№РµРєСЃУ©Р· Р±РѕР№С‹РЅС€Р°, 2 - РєТЇРЅ Р±РѕР№С‹РЅС€Р°, 3 - Р±РµС‚ СЃР°РЅС‹ Р±РѕР№С‹РЅС€Р°";
            case "news": return "Р–Р°ТЈР°Р»С‹Т›С‚Р°СЂ:";
            case "no.news": return "Р–Р°ТЈР°Р»С‹Т›С‚Р°СЂ Р¶РѕТ›.";
            case "student.notifications": return "РЎС‚СѓРґРµРЅС‚ С…Р°Р±Р°СЂР»Р°РјР°Р»Р°СЂС‹:";
            case "support.title": return "--- РўРµС…РЅРёРєР°Р»С‹Т› Т›РѕР»РґР°Сѓ ---";
            case "support.create": return "1 - РўРµС…Т›РѕР»РґР°СѓТ“Р° СЃТ±СЂР°РЅС‹СЃ Р¶Р°СЃР°Сѓ";
            case "support.assign": return "2 - Р‘С–СЂС–РЅС€С– СЃТ±СЂР°РЅС‹СЃС‚С‹ РјР°РјР°РЅТ“Р° С‚Р°Т“Р°Р№С‹РЅРґР°Сѓ";
            case "support.accept": return "3 - Р‘С–СЂС–РЅС€С– СЃТ±СЂР°РЅС‹СЃС‚С‹ Т›Р°Р±С‹Р»РґР°Сѓ";
            case "support.done": return "4 - Р‘С–СЂС–РЅС€С– СЃТ±СЂР°РЅС‹СЃС‚С‹ Р°СЏТ›С‚Р°Сѓ";
            case "support.show": return "5 - РўРµС…Т›РѕР»РґР°Сѓ СЃТ±СЂР°РЅС‹СЃС‚Р°СЂС‹РЅ РєУ©СЂСЃРµС‚Сѓ";
            case "description": return "РЎРёРїР°С‚С‚Р°РјР°:";
            case "created": return "Р–Р°СЃР°Р»РґС‹:";
            case "assigned": return "РўР°Т“Р°Р№С‹РЅРґР°Р»РґС‹:";
            case "accepted": return "ТљР°Р±С‹Р»РґР°РЅРґС‹:";
            case "done": return "РђСЏТ›С‚Р°Р»РґС‹:";
            case "no.requests": return "РЎТ±СЂР°РЅС‹СЃС‚Р°СЂ Р¶РѕТ›.";
            case "request": return "СЃТ±СЂР°РЅС‹СЃ";
            case "no.student": return "РЎС‚СѓРґРµРЅС‚ С‚Р°Р±С‹Р»РјР°РґС‹. РђР»РґС‹РјРµРЅ У™РєС–РјС€С– Т›РѕСЃС‹РјС€Р°СЃС‹РЅРґР° СЃС‚СѓРґРµРЅС‚ Т›РѕСЃС‹ТЈС‹Р·.";
            case "no.manager": return "РњРµРЅРµРґР¶РµСЂ С‚Р°Р±С‹Р»РјР°РґС‹. РђР»РґС‹РјРµРЅ У™РєС–РјС€С– Т›РѕСЃС‹РјС€Р°СЃС‹РЅРґР° РјРµРЅРµРґР¶РµСЂ Т›РѕСЃС‹ТЈС‹Р·.";
            case "no.teacher": return "РћТ›С‹С‚СѓС€С‹ С‚Р°Р±С‹Р»РјР°РґС‹. РђР»РґС‹РјРµРЅ У™РєС–РјС€С– Т›РѕСЃС‹РјС€Р°СЃС‹РЅРґР° РѕТ›С‹С‚СѓС€С‹ Т›РѕСЃС‹ТЈС‹Р·.";
            case "no.support.specialist": return "РўРµС…Т›РѕР»РґР°Сѓ РјР°РјР°РЅС‹ С‚Р°Р±С‹Р»РјР°РґС‹. РђР»РґС‹РјРµРЅ У™РєС–РјС€С– Т›РѕСЃС‹РјС€Р°СЃС‹РЅРґР° Т›РѕСЃС‹ТЈС‹Р·.";
            case "no.course": return "РљСѓСЂСЃ С‚Р°Р±С‹Р»РјР°РґС‹.";
            case "no.offering": return "РљСѓСЂСЃ Т±СЃС‹РЅС‹СЃС‹ С‚Р°Р±С‹Р»РјР°РґС‹. РђР»РґС‹РјРµРЅ РјРµРЅРµРґР¶РµСЂ Т›РѕСЃС‹РјС€Р°СЃС‹РЅРґР° Р¶Р°СЃР°ТЈС‹Р·.";
            case "no.journal": return "Р–СѓСЂРЅР°Р» С‚Р°Р±С‹Р»РјР°РґС‹. РђР»РґС‹РјРµРЅ Р¶СѓСЂРЅР°Р» Р¶Р°СЃР°ТЈС‹Р·.";
            case "no.researcher": return "Р—РµСЂС‚С‚РµСѓС€С– С‚Р°Р±С‹Р»РјР°РґС‹. РђР»РґС‹РјРµРЅ РјР°РіРёСЃС‚СЂР°РЅС‚ РЅРµРјРµСЃРµ РїСЂРѕС„РµСЃСЃРѕСЂ Т›РѕСЃС‹ТЈС‹Р·.";
            case "no.graduate.student": return "РњР°РіРёСЃС‚СЂР°РЅС‚ С‚Р°Р±С‹Р»РјР°РґС‹. РђР»РґС‹РјРµРЅ У™РєС–РјС€С– Т›РѕСЃС‹РјС€Р°СЃС‹РЅРґР° Т›РѕСЃС‹ТЈС‹Р·.";
            case "no.pending": return "РљТЇС‚С–Рї С‚Т±СЂТ“Р°РЅ С‚С–СЂРєРµСѓР»РµСЂ Р¶РѕТ›.";
            case "journal.id": return "Р–СѓСЂРЅР°Р» id:";
            case "journal.title": return "Р–СѓСЂРЅР°Р» Р°С‚Р°СѓС‹:";
            case "created.journal": return "Р–СѓСЂРЅР°Р» Р¶Р°СЃР°Р»РґС‹:";
            case "paper.id": return "РњР°Т›Р°Р»Р° id:";
            case "paper.title": return "РњР°Т›Р°Р»Р° Р°С‚Р°СѓС‹:";
            case "paper.citations": return "Р”У™Р№РµРєСЃУ©Р·РґРµСЂ:";
            case "paper.pages": return "Р‘РµС‚С‚РµСЂ:";
            case "paper.doi": return "Doi:";
            case "project.id": return "Р–РѕР±Р° id:";
            case "project.topic": return "РўР°Т›С‹СЂС‹Рї:";
            case "official.description": return "Р РµСЃРјРё СЃТ±СЂР°РЅС‹СЃ СЃРёРїР°С‚С‚Р°РјР°СЃС‹:";
            case "complaint.text": return "РЁР°Т“С‹Рј РјУ™С‚С–РЅС–:";
            case "research.create.journal": return "1 - Р–СѓСЂРЅР°Р» Р¶Р°СЃР°Сѓ";
            case "research.publish": return "2 - РњР°Т›Р°Р»Р° Р¶Р°СЂРёСЏР»Р°Сѓ";
            case "research.hindex": return "3 - Р‘С–СЂС–РЅС€С– Р·РµСЂС‚С‚РµСѓС€С–РЅС–ТЈ h-index РєУ©СЂСЃРµС‚Сѓ";
            case "research.sorted": return "4 - РњР°Т›Р°Р»Р°Р»Р°СЂРґС‹ СЃТ±СЂС‹РїС‚Р°Рї С€С‹Т“Р°СЂСѓ";
            case "research.supervisor": return "5 - Т’С‹Р»С‹РјРё Р¶РµС‚РµРєС€С– С‚Р°Т“Р°Р№С‹РЅРґР°Сѓ";
            case "research.join": return "6 - Р—РµСЂС‚С‚РµСѓ Р¶РѕР±Р°СЃС‹РЅР° Т›РѕСЃС‹Р»Сѓ";
            case "research.news": return "7 - Р–Р°ТЈР°Р»С‹Т›С‚Р°СЂ РјРµРЅ С…Р°Р±Р°СЂР»Р°РјР°Р»Р°СЂРґС‹ РєУ©СЂСЃРµС‚Сѓ";
            default: return en(key);
        }
    }
}

