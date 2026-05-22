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
import university.users.StudentOrganization;
import university.users.StudentOrganizationRequest;

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
            System.out.println(I18n.t("manager.organization.requests"));
            System.out.println(I18n.t("manager.organization.approve"));
            System.out.println(I18n.t("manager.organization.reject"));
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
                case 8:
                    showOrganizationRequests();
                    break;
                case 9:
                    approveOrganizationRequest(scanner, manager);
                    break;
                case 10:
                    rejectOrganizationRequest(scanner, manager);
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
        if (findCourse(id) != null) {
            System.out.println(I18n.t("duplicate.course"));
            return;
        }
        String name = ConsoleInput.readLine(scanner, I18n.t("course.name"));
        int credits = ConsoleInput.readInt(scanner, I18n.t("credits"));
        System.out.println(I18n.t("course.type"));
        CourseType type = readCourseType(scanner);
        int capacity = ConsoleInput.readInt(scanner, I18n.t("capacity"));
        String major = ConsoleInput.readLine(scanner, I18n.t("major"));
        int year = ConsoleInput.readInt(scanner, I18n.t("year"));
        if (findOffering("OFF-" + id + "-" + year) != null) {
            System.out.println(I18n.t("duplicate.offering"));
            return;
        }
        Course course = new Course(id, name, credits, type, capacity);
        University.getInstance().addCourse(course);
        try {
            System.out.println(I18n.f("added.course", AppFormatter.offering(manager.addCourseForRegistration(course, major, year))));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
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
        LessonType lessonType = ConsoleInput.readIntInRange(scanner, I18n.t("choose"), 1, 2) == 1
                ? LessonType.LECTURE
                : LessonType.PRACTICE;
        manager.assignCourseToTeacher(course, lessonType, teacher);
        System.out.println(I18n.f("assigned", teacher));
    }

    private static CourseType readCourseType(Scanner scanner) {
        int type = ConsoleInput.readIntInRange(scanner, I18n.t("choose"), 1, 3);
        switch (type) {
            case 1:
                return CourseType.MAJOR;
            case 2:
                return CourseType.MINOR;
            case 3:
                return CourseType.FREE_ELECTIVE;
            default:
                throw new IllegalArgumentException("Invalid course type: " + type);
        }
    }

    private static Course findCourse(String courseId) {
        for (Course course : University.getInstance().getCourses()) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    private static university.education.CourseOffering findOffering(String offeringId) {
        for (university.education.CourseOffering offering : University.getInstance().getCourseOfferings()) {
            if (offering.getOfferingId().equals(offeringId)) {
                return offering;
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
        Teacher teacher = selectTeacher(scanner, manager);
        university.users.Student student = selectStudent(scanner);
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
        Complaint complaint = teacher.sendComplaint(manager, Arrays.asList(student), readUrgency(scanner), complaintText);
        System.out.println(request);
        System.out.println(complaint);
    }

    private static void showOrganizationRequests() {
        if (University.getInstance().getStudentOrganizationRequests().isEmpty()) {
            System.out.println(I18n.t("no.organization.requests"));
            return;
        }
        for (StudentOrganizationRequest request : University.getInstance().getStudentOrganizationRequests()) {
            System.out.println(AppFormatter.studentOrganizationRequest(request));
        }
    }

    private static void approveOrganizationRequest(Scanner scanner, Manager manager) {
        StudentOrganizationRequest request = selectPendingOrganizationRequest(scanner);
        if (request == null) {
            return;
        }
        if (organizationExists(request.getOrganizationId(), request.getOrganizationName())) {
            System.out.println(I18n.t("duplicate.organization"));
            return;
        }
        StudentOrganization organization = new StudentOrganization(
                request.getOrganizationId(),
                request.getOrganizationName(),
                request.getDescription());
        organization.assignHead(request.getStudent());
        University.getInstance().addStudentOrganization(organization);
        request.approve();
        University.getInstance().getLogService().log(manager, "Approved organization request " + request.getRequestId());
        System.out.println(I18n.f("approved.organization.request", AppFormatter.studentOrganizationRequest(request)));
    }

    private static void rejectOrganizationRequest(Scanner scanner, Manager manager) {
        StudentOrganizationRequest request = selectPendingOrganizationRequest(scanner);
        if (request == null) {
            return;
        }
        request.reject();
        University.getInstance().getLogService().log(manager, "Rejected organization request " + request.getRequestId());
        System.out.println(I18n.f("rejected.organization.request", AppFormatter.studentOrganizationRequest(request)));
    }

    private static Urgency readUrgency(Scanner scanner) {
        System.out.println("1 - LOW, 2 - MEDIUM, 3 - HIGH");
        int urgencyNumber = ConsoleInput.readIntInRange(scanner, I18n.t("choose"), 1, 3);
        switch (urgencyNumber) {
            case 1:
                return Urgency.LOW;
            case 2:
                return Urgency.MEDIUM;
            case 3:
                return Urgency.HIGH;
            default:
                throw new IllegalArgumentException("Invalid urgency: " + urgencyNumber);
        }
    }

    private static Teacher selectTeacher(Scanner scanner, Manager manager) {
        java.util.List<Teacher> teachers = manager.viewTeachers(new SortTeacherByNameStrategy());
        if (teachers.isEmpty()) {
            return null;
        }
        for (Teacher teacher : teachers) {
            System.out.println(teacher.getId() + " - " + teacher.getName());
        }
        return findTeacher(ConsoleInput.readLine(scanner, I18n.t("teacher.id")));
    }

    private static university.users.Student selectStudent(Scanner scanner) {
        java.util.List<university.users.Student> students = AppData.students();
        if (students.isEmpty()) {
            return null;
        }
        for (university.users.Student student : students) {
            System.out.println(student.getId() + " - " + student.getName());
        }
        String studentId = ConsoleInput.readLine(scanner, I18n.t("student.id"));
        for (university.users.Student student : students) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    private static StudentOrganizationRequest selectPendingOrganizationRequest(Scanner scanner) {
        java.util.List<StudentOrganizationRequest> pending = new java.util.ArrayList<StudentOrganizationRequest>();
        for (StudentOrganizationRequest request : University.getInstance().getStudentOrganizationRequests()) {
            if (request.getStatus() == university.enums.RegistrationStatus.PENDING) {
                pending.add(request);
            }
        }
        if (pending.isEmpty()) {
            System.out.println(I18n.t("no.pending.organization.requests"));
            return null;
        }
        for (StudentOrganizationRequest request : pending) {
            System.out.println(AppFormatter.studentOrganizationRequest(request));
        }
        String requestId = ConsoleInput.readLine(scanner, I18n.t("organization.request.id"));
        for (StudentOrganizationRequest request : pending) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }
        System.out.println(I18n.t("no.organization.request"));
        return null;
    }

    private static boolean organizationExists(String organizationId, String organizationName) {
        for (StudentOrganization organization : University.getInstance().getStudentOrganizations()) {
            if (organization.getOrgId().equalsIgnoreCase(organizationId)
                    || organization.getName().equalsIgnoreCase(organizationName)) {
                return true;
            }
        }
        return false;
    }
}
