package university.app;

import java.util.List;
import java.util.Scanner;

import university.core.University;
import university.education.Course;
import university.education.CourseOffering;
import university.education.Enrollment;
import university.education.Lesson;
import university.education.Mark;
import university.enums.LessonType;
import university.enums.RegistrationStatus;
import university.employees.Manager;
import university.employees.Teacher;
import university.users.Student;
import university.users.StudentOrganization;
import university.users.StudentOrganizationRequest;
import university.users.User;

public final class EducationApp {
    private EducationApp() {
    }

    public static void run(Scanner scanner, User currentUser) {
        while (true) {
            System.out.println(I18n.t("education.title"));
            System.out.println(I18n.t("education.show.courses"));
            if (currentUser instanceof Student) {
                System.out.println(I18n.t("education.register"));
                System.out.println(I18n.t("education.transcript"));
                System.out.println(I18n.t("education.teachers"));
                System.out.println(I18n.t("education.rate.teacher"));
                System.out.println(I18n.t("education.organizations.show"));
                System.out.println(I18n.t("education.organizations.join"));
                System.out.println(I18n.t("education.organizations.leave"));
                System.out.println(I18n.t("education.organizations.request"));
            }
            if (currentUser instanceof Manager) {
                System.out.println(I18n.t("education.approve"));
                System.out.println(I18n.t("education.reject"));
            }
            if (currentUser instanceof Teacher) {
                System.out.println(I18n.t("education.put.mark"));
            }
            System.out.println(I18n.t("back"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose"));
            switch (choice) {
                case 1:
                    printList(University.getInstance().getCourses());
                    break;
                case 2:
                    if (currentUser instanceof Student) {
                        registerStudent(scanner, (Student) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 3:
                    if (currentUser instanceof Manager) {
                        approvePending(scanner, (Manager) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 4:
                    if (currentUser instanceof Teacher) {
                        putMark(scanner, (Teacher) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 5:
                    if (currentUser instanceof Student) {
                        showTranscript((Student) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 6:
                    if (currentUser instanceof Student) {
                        showCourseTeachers(scanner);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 7:
                    if (currentUser instanceof Student) {
                        rateTeacher(scanner, (Student) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 8:
                    if (currentUser instanceof Manager) {
                        rejectPending(scanner, (Manager) currentUser);
                    } else if (currentUser instanceof Student) {
                        showOrganizations((Student) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 9:
                    if (currentUser instanceof Student) {
                        joinOrganization(scanner, (Student) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 10:
                    if (currentUser instanceof Student) {
                        leaveOrganization(scanner, (Student) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 11:
                    if (currentUser instanceof Student) {
                        requestOrganization(scanner, (Student) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }
        }
    }

    private static void registerStudent(Scanner scanner, Student student) {
        if (University.getInstance().getCourseOfferings().isEmpty()) {
            System.out.println(I18n.t("no.offering"));
            return;
        }
        for (CourseOffering offering : University.getInstance().getCourseOfferings()) {
            System.out.println(AppFormatter.offering(offering));
        }
        CourseOffering offering = findOffering(ConsoleInput.readLine(scanner, I18n.t("offering.id")));
        if (offering == null) {
            System.out.println(I18n.t("no.offering"));
            return;
        }
        try {
            Enrollment enrollment = student.registerCourse(offering);
            System.out.println(I18n.f("created.enrollment", AppFormatter.enrollment(enrollment)));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void approvePending(Scanner scanner, Manager manager) {
        List<Enrollment> pending = new java.util.ArrayList<Enrollment>();
        for (Student student : AppData.students()) {
            for (Enrollment enrollment : student.getEnrollments()) {
                if (enrollment.getStatus() == RegistrationStatus.PENDING) {
                    pending.add(enrollment);
                }
            }
        }
        if (pending.isEmpty()) {
            System.out.println(I18n.t("no.pending"));
            return;
        }
        for (Enrollment enrollment : pending) {
            System.out.println(AppFormatter.enrollment(enrollment));
        }
        Enrollment enrollment = findEnrollment(pending, ConsoleInput.readLine(scanner, I18n.t("enrollment.id")));
        if (enrollment == null) {
            System.out.println(I18n.t("no.enrollment"));
            return;
        }
        try {
            manager.approveStudentRegistration(enrollment);
            System.out.println(I18n.f("approved", AppFormatter.enrollment(enrollment)));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void rejectPending(Scanner scanner, Manager manager) {
        List<Enrollment> pending = pendingEnrollments();
        if (pending.isEmpty()) {
            System.out.println(I18n.t("no.pending"));
            return;
        }
        for (Enrollment enrollment : pending) {
            System.out.println(AppFormatter.enrollment(enrollment));
        }
        Enrollment enrollment = findEnrollment(pending, ConsoleInput.readLine(scanner, I18n.t("enrollment.id")));
        if (enrollment == null) {
            System.out.println(I18n.t("no.enrollment"));
            return;
        }
        try {
            manager.rejectStudentRegistration(enrollment);
            System.out.println(I18n.f("rejected", AppFormatter.enrollment(enrollment)));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void putMark(Scanner scanner, Teacher teacher) {
        List<Enrollment> approved = approvedEnrollments(teacher);
        if (approved.isEmpty()) {
            System.out.println(I18n.t("no.approved.enrollment"));
            return;
        }
        for (Enrollment item : approved) {
            System.out.println(AppFormatter.enrollment(item));
        }
        Enrollment enrollment = findEnrollment(approved, ConsoleInput.readLine(scanner, I18n.t("enrollment.id")));
        if (enrollment == null) {
            System.out.println(I18n.t("no.enrollment"));
            return;
        }
        double a1 = ConsoleInput.readDouble(scanner, I18n.t("attestation1"));
        double a2 = ConsoleInput.readDouble(scanner, I18n.t("attestation2"));
        double exam = ConsoleInput.readDouble(scanner, I18n.t("final.exam"));
        try {
            teacher.putMark(enrollment, new Mark(a1, a2, exam));
            System.out.println(I18n.f("mark.saved", enrollment.getMark()));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showTranscript(Student student) {
        System.out.println(student.getTranscript());
    }

    private static void showOrganizations(Student student) {
        if (University.getInstance().getStudentOrganizations().isEmpty()) {
            System.out.println(I18n.t("no.organization"));
            return;
        }
        System.out.println(I18n.t("my.organizations"));
        if (student.getOrganizations().isEmpty()) {
            System.out.println("-");
        } else {
            for (StudentOrganization organization : student.getOrganizations()) {
                System.out.println("- " + AppFormatter.studentOrganization(organization));
            }
        }
        for (StudentOrganization organization : University.getInstance().getStudentOrganizations()) {
            System.out.println(AppFormatter.studentOrganization(organization));
        }
    }

    private static void joinOrganization(Scanner scanner, Student student) {
        StudentOrganization organization = selectOrganization(scanner);
        if (organization == null) {
            return;
        }
        organization.addMember(student);
        System.out.println(I18n.f("joined.organization", organization.getName()));
    }

    private static void leaveOrganization(Scanner scanner, Student student) {
        StudentOrganization organization = selectOrganization(scanner);
        if (organization == null) {
            return;
        }
        if (!student.getOrganizations().contains(organization)) {
            System.out.println(I18n.t("not.organization.member"));
            return;
        }
        organization.removeMember(student);
        System.out.println(I18n.f("left.organization", organization.getName()));
    }

    private static void requestOrganization(Scanner scanner, Student student) {
        String organizationId = ConsoleInput.readLine(scanner, I18n.t("organization.id"));
        String organizationName = ConsoleInput.readLine(scanner, I18n.t("organization.name"));
        String description = ConsoleInput.readLine(scanner, I18n.t("description"));
        if (organizationExists(organizationId, organizationName)) {
            System.out.println(I18n.t("duplicate.organization"));
            return;
        }
        if (pendingOrganizationRequestExists(organizationId, organizationName)) {
            System.out.println(I18n.t("organization.request.exists"));
            return;
        }
        StudentOrganizationRequest request = new StudentOrganizationRequest(
                "ORGREQ-" + System.nanoTime(),
                student,
                organizationId,
                organizationName,
                description);
        University.getInstance().addStudentOrganizationRequest(request);
        student.addNotification("Organization request submitted: " + organizationName + " (" + organizationId + ")");
        University.getInstance().getLogService().log(student, "Submitted organization request " + request.getRequestId());
        System.out.println(I18n.f("created.organization.request", AppFormatter.studentOrganizationRequest(request)));
    }

    private static void showCourseTeachers(Scanner scanner) {
        if (University.getInstance().getCourseOfferings().isEmpty()) {
            System.out.println(I18n.t("no.offering"));
            return;
        }
        for (CourseOffering item : University.getInstance().getCourseOfferings()) {
            System.out.println(AppFormatter.offering(item));
        }
        CourseOffering offering = findOffering(ConsoleInput.readLine(scanner, I18n.t("offering.id")));
        if (offering == null) {
            System.out.println(I18n.t("no.offering"));
            return;
        }
        Course course = offering.getCourse();
        System.out.println(I18n.f("lecture", findTeacher(course, LessonType.LECTURE)));
        System.out.println(I18n.f("practice", findTeacher(course, LessonType.PRACTICE)));
    }

    private static void rateTeacher(Scanner scanner, Student student) {
        if (University.getInstance().getCourseOfferings().isEmpty()) {
            System.out.println(I18n.t("no.offering"));
            return;
        }
        for (CourseOffering item : University.getInstance().getCourseOfferings()) {
            System.out.println(AppFormatter.offering(item));
        }
        CourseOffering offering = findOffering(ConsoleInput.readLine(scanner, I18n.t("offering.id")));
        if (offering == null) {
            System.out.println(I18n.t("no.offering"));
            return;
        }

        System.out.println("1 - LECTURE, 2 - PRACTICE");
        LessonType lessonType = ConsoleInput.readIntInRange(scanner, I18n.t("choose"), 1, 2) == 1
                ? LessonType.LECTURE
                : LessonType.PRACTICE;
        Teacher teacher = findTeacher(offering.getCourse(), lessonType);
        if (teacher == null) {
            System.out.println(I18n.t("no.teacher"));
            return;
        }

        System.out.println(I18n.f(lessonType == LessonType.LECTURE ? "lecture" : "practice", teacher));

        int rating;
        while (true) {
            rating = ConsoleInput.readInt(scanner, I18n.t("teacher.rating"));
            if (rating >= 1 && rating <= 5) {
                break;
            }
            System.out.println(I18n.t("rating.out.of.range"));
        }

        try {
            student.rateTeacher(teacher, rating);
            System.out.println(I18n.f("teacher.rated", teacher.getName() + " (" + rating + ")"));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printList(List<Course> courses) {
        for (Course course : courses) {
            System.out.println(AppFormatter.course(course));
        }
    }

    private static CourseOffering findOffering(String id) {
        for (CourseOffering offering : University.getInstance().getCourseOfferings()) {
            if (offering.getOfferingId().equals(id) || offering.getCourse().getCourseId().equals(id)) {
                return offering;
            }
        }
        return null;
    }

    private static Enrollment findEnrollment(List<Enrollment> enrollments, String id) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getEnrollmentId().equals(id)) {
                return enrollment;
            }
        }
        return null;
    }

    private static Teacher findTeacher(Course course, LessonType lessonType) {
        for (university.education.Lesson lesson : course.getLessons()) {
            if (lesson.getType() == lessonType) {
                return lesson.getInstructor();
            }
        }
        return null;
    }

    private static List<Enrollment> approvedEnrollments(Teacher teacher) {
        List<Enrollment> approved = new java.util.ArrayList<Enrollment>();
        for (Student student : AppData.students()) {
            for (Enrollment enrollment : student.getEnrollments()) {
                if (enrollment.getStatus() == RegistrationStatus.APPROVED
                        && teacherTeaches(teacher, enrollment.getOffering().getCourse())) {
                    approved.add(enrollment);
                }
            }
        }
        return approved;
    }

    private static boolean teacherTeaches(Teacher teacher, Course course) {
        if (teacher.viewCourses().contains(course)) {
            return true;
        }
        for (Lesson lesson : course.getLessons()) {
            if (teacher.equals(lesson.getInstructor())) {
                return true;
            }
        }
        return false;
    }

    private static List<Enrollment> pendingEnrollments() {
        List<Enrollment> pending = new java.util.ArrayList<Enrollment>();
        for (Student student : AppData.students()) {
            for (Enrollment enrollment : student.getEnrollments()) {
                if (enrollment.getStatus() == RegistrationStatus.PENDING) {
                    pending.add(enrollment);
                }
            }
        }
        return pending;
    }

    private static StudentOrganization selectOrganization(Scanner scanner) {
        if (University.getInstance().getStudentOrganizations().isEmpty()) {
            System.out.println(I18n.t("no.organization"));
            return null;
        }
        for (StudentOrganization organization : University.getInstance().getStudentOrganizations()) {
            System.out.println(AppFormatter.studentOrganization(organization));
        }
        String organizationId = ConsoleInput.readLine(scanner, I18n.t("organization.id"));
        for (StudentOrganization organization : University.getInstance().getStudentOrganizations()) {
            if (organization.getOrgId().equals(organizationId)) {
                return organization;
            }
        }
        System.out.println(I18n.t("no.organization"));
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

    private static boolean pendingOrganizationRequestExists(String organizationId, String organizationName) {
        for (StudentOrganizationRequest request : University.getInstance().getStudentOrganizationRequests()) {
            if (request.getStatus() == RegistrationStatus.PENDING
                    && (request.getOrganizationId().equalsIgnoreCase(organizationId)
                    || request.getOrganizationName().equalsIgnoreCase(organizationName))) {
                return true;
            }
        }
        return false;
    }
}
