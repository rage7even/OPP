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

    public static void run(Scanner scanner, Manager manager) {
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
                    addCourse(scanner, manager);
                    break;
                case 2:
                    assignPracticeTeacher(scanner, manager);
                    break;
                case 3:
                    showStudents(scanner, manager);
                    break;
                case 4:
                    showTeachers(scanner, manager);
                    break;
                case 5:
                    createReport(manager);
                    break;
                case 6:
                    manageNews(scanner);
                    break;
                case 7:
                    officialRequest(scanner, manager);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }
        }
    }

    private static void addCourse(Scanner scanner, Manager manager) {
        String id = ConsoleInput.readLine(scanner, I18n.t("course.id"));
        String name = ConsoleInput.readLine(scanner, I18n.t("course.name"));
        int credits = ConsoleInput.readInt(scanner, I18n.t("credits"));
        System.out.println(I18n.t("course.type"));
        CourseType type = readCourseType(scanner);
        int capacity = ConsoleInput.readInt(scanner, I18n.t("capacity"));
        String major = ConsoleInput.readLine(scanner, I18n.t("major"));
        int year = ConsoleInput.readInt(scanner, I18n.t("year"));
        Course course = new Course(id, name, credits, type, capacity);
        University.getInstance().addCourse(course);
        System.out.println(I18n.f("added.course", AppFormatter.offering(manager.addCourseForRegistration(course, major, year))));
    }

    private static void assignPracticeTeacher(Scanner scanner, Manager manager) {
        if (University.getInstance().getCourses().isEmpty()) {
            System.out.println(I18n.t("no.course"));
            return;
        }
        for (Course course : University.getInstance().getCourses()) {
            System.out.println(AppFormatter.course(course));
        }
        Course course = findCourse(ConsoleInput.readLine(scanner, I18n.t("course.id")));
        if (course == null) {
            System.out.println(I18n.t("no.course"));
            return;
        }
        for (Teacher teacher : manager.viewTeachers(new SortTeacherByNameStrategy())) {
            System.out.println(teacher.getId() + " - " + teacher.getName());
        }
        Teacher teacher = findTeacher(ConsoleInput.readLine(scanner, I18n.t("teacher.id")));
        if (teacher == null) {
            System.out.println(I18n.t("no.teacher"));
            return;
        }
        System.out.println("1 - LECTURE, 2 - PRACTICE");
        LessonType lessonType = ConsoleInput.readInt(scanner, I18n.t("choose")) == 1
                ? LessonType.LECTURE
                : LessonType.PRACTICE;
        manager.assignCourseToTeacher(course, lessonType, teacher);
        System.out.println(I18n.f("assigned.practice.teacher", teacher));
    }

    private static CourseType readCourseType(Scanner scanner) {
        int type = ConsoleInput.readInt(scanner, I18n.t("choose"));
        if (type == 1) {
            return CourseType.MAJOR;
        }
        if (type == 2) {
            return CourseType.MINOR;
        }
        return CourseType.FREE_ELECTIVE;
    }

    private static Course findCourse(String courseId) {
        for (Course course : University.getInstance().getCourses()) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    private static Teacher findTeacher(String teacherId) {
        for (university.users.User user : University.getInstance().getUsers()) {
            if (user instanceof Teacher && user.getId().equals(teacherId)) {
                return (Teacher) user;
            }
        }
        return null;
    }

    private static void showStudents(Scanner scanner, Manager manager) {
        System.out.println(I18n.t("sort.students"));
        int sort = ConsoleInput.readInt(scanner, I18n.t("sort"));
        if (sort == 2) {
            System.out.println(manager.viewStudents(new SortStudentByNameStrategy()));
        } else {
            System.out.println(manager.viewStudents(new SortStudentByGpaStrategy()));
        }
    }

    private static void showTeachers(Scanner scanner, Manager manager) {
        System.out.println(I18n.t("sort.teachers"));
        int sort = ConsoleInput.readInt(scanner, I18n.t("sort"));
        if (sort == 2) {
            System.out.println(manager.viewTeachers(new SortTeacherByNameStrategy()));
        } else {
            System.out.println(manager.viewTeachers(new SortTeacherByRatingStrategy()));
        }
    }

    private static void createReport(Manager manager) {
        System.out.println(manager.createStatisticalReport());
    }

    private static void manageNews(Scanner scanner) {
        String title = ConsoleInput.readLine(scanner, I18n.t("news.title"));
        String content = ConsoleInput.readLine(scanner, I18n.t("news.content"));
        NewsItem item = University.getInstance().getNewsService().addGeneralNews(title, content);
        System.out.println(I18n.f("created.news", item));
    }

    private static void officialRequest(Scanner scanner, Manager manager) {
        Teacher teacher = AppData.firstTeacher();
        university.users.Student student = AppData.firstStudent();
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

