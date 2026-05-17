package university.app;

import java.util.Arrays;
import java.util.Scanner;

import university.communication.Complaint;
import university.communication.OfficialRequest;
import university.core.University;
import university.education.Course;
import university.employees.Manager;
import university.employees.Teacher;
import university.enums.CourseType;
import university.enums.LessonType;
import university.enums.Urgency;
import university.news.NewsItem;
import university.patterns.SortStudentByGpaStrategy;
import university.patterns.SortStudentByNameStrategy;
import university.patterns.SortTeacherByNameStrategy;
import university.patterns.SortTeacherByRatingStrategy;

public final class ManagerApp {
    private ManagerApp() {
    }

    public static void run(Scanner scanner) {
        while (true) {
            System.out.println(I18n.t("manager.title"));
            System.out.println(I18n.t("manager.add.course"));
            System.out.println(I18n.t("manager.assign.teacher"));
            System.out.println(I18n.t("manager.students.sorted"));
            System.out.println(I18n.t("manager.teachers.sorted"));
            System.out.println(I18n.t("manager.report"));
            System.out.println(I18n.t("manager.news"));
            System.out.println(I18n.t("manager.official"));
            System.out.println(I18n.t("back"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose"));
            switch (choice) {
                case 1:
                    addCourse(scanner);
                    break;
                case 2:
                    assignPracticeTeacher();
                    break;
                case 3:
                    showStudents(scanner);
                    break;
                case 4:
                    showTeachers(scanner);
                    break;
                case 5:
                    createReport();
                    break;
                case 6:
                    manageNews(scanner);
                    break;
                case 7:
                    officialRequest(scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }
        }
    }

    private static void addCourse(Scanner scanner) {
        Manager manager = AppData.firstManager();
        if (manager == null) {
            System.out.println(I18n.t("no.manager"));
            return;
        }
        String id = ConsoleInput.readLine(scanner, I18n.t("course.id"));
        String name = ConsoleInput.readLine(scanner, I18n.t("course.name"));
        int credits = ConsoleInput.readInt(scanner, I18n.t("credits"));
        Course course = new Course(id, name, credits, CourseType.FREE_ELECTIVE, 20);
        University.getInstance().addCourse(course);
        manager.addCourseForRegistration(course, "SITE", 2);
        System.out.println(I18n.f("added.course", AppFormatter.course(course)));
    }

    private static void assignPracticeTeacher() {
        Manager manager = AppData.firstManager();
        Teacher teacher = AppData.firstTeacher();
        if (manager == null) {
            System.out.println(I18n.t("no.manager"));
            return;
        }
        if (teacher == null) {
            System.out.println(I18n.t("no.teacher"));
            return;
        }
        if (University.getInstance().getCourses().isEmpty()) {
            System.out.println(I18n.t("no.course"));
            return;
        }
        Course course = University.getInstance().getCourses().get(0);
        manager.assignCourseToTeacher(course, LessonType.PRACTICE, teacher);
        System.out.println(I18n.f("assigned.practice.teacher", teacher));
    }

    private static void showStudents(Scanner scanner) {
        System.out.println(I18n.t("sort.students"));
        int sort = ConsoleInput.readInt(scanner, I18n.t("sort"));
        Manager manager = AppData.firstManager();
        if (manager == null) {
            System.out.println(I18n.t("no.manager"));
            return;
        }
        if (sort == 2) {
            System.out.println(manager.viewStudents(new SortStudentByNameStrategy()));
        } else {
            System.out.println(manager.viewStudents(new SortStudentByGpaStrategy()));
        }
    }

    private static void showTeachers(Scanner scanner) {
        System.out.println(I18n.t("sort.teachers"));
        int sort = ConsoleInput.readInt(scanner, I18n.t("sort"));
        Manager manager = AppData.firstManager();
        if (manager == null) {
            System.out.println(I18n.t("no.manager"));
            return;
        }
        if (sort == 2) {
            System.out.println(manager.viewTeachers(new SortTeacherByNameStrategy()));
        } else {
            System.out.println(manager.viewTeachers(new SortTeacherByRatingStrategy()));
        }
    }

    private static void createReport() {
        Manager manager = AppData.firstManager();
        if (manager == null) {
            System.out.println(I18n.t("no.manager"));
            return;
        }
        System.out.println(manager.createStatisticalReport());
    }

    private static void manageNews(Scanner scanner) {
        String title = ConsoleInput.readLine(scanner, I18n.t("news.title"));
        String content = ConsoleInput.readLine(scanner, I18n.t("news.content"));
        NewsItem item = University.getInstance().getNewsService().addGeneralNews(title, content);
        System.out.println(I18n.f("created.news", item));
    }

    private static void officialRequest(Scanner scanner) {
        Manager manager = AppData.firstManager();
        Teacher teacher = AppData.firstTeacher();
        university.users.Student student = AppData.firstStudent();
        if (manager == null) {
            System.out.println(I18n.t("no.manager"));
            return;
        }
        if (teacher == null) {
            System.out.println(I18n.t("no.teacher"));
            return;
        }
        if (student == null) {
            System.out.println(I18n.t("no.student"));
            return;
        }
        String officialDescription = ConsoleInput.readLine(scanner, I18n.t("official.description"));
        String complaintText = ConsoleInput.readLine(scanner, I18n.t("complaint.text"));
        OfficialRequest request = teacher.createOfficialRequest(officialDescription);
        manager.receiveOfficialRequest(request);
        request.sign(manager);
        Complaint complaint = teacher.sendComplaint(manager, Arrays.asList(student),
                Urgency.MEDIUM, complaintText);
        System.out.println(request);
        System.out.println(complaint);
    }
}

