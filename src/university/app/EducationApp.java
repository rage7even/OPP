package university.app;

import java.util.List;
import java.util.Scanner;

import university.core.University;
import university.education.Course;
import university.education.CourseOffering;
import university.education.Enrollment;
import university.education.Mark;
import university.enums.LessonType;
import university.enums.RegistrationStatus;
import university.employees.Manager;
import university.employees.Teacher;
import university.users.Student;
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
                    if (currentUser instanceof Manager) {
                        rejectPending(scanner, (Manager) currentUser);
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
        List<Enrollment> approved = approvedEnrollments();
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
        teacher.putMark(enrollment, new Mark(a1, a2, exam));
        enrollment.getStudent().rateTeacher(teacher, 5);
        System.out.println(I18n.f("mark.saved", enrollment.getMark()));
    }

    private static void showTranscript(Student student) {
        System.out.println(student.getTranscript());
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

    private static List<Enrollment> approvedEnrollments() {
        List<Enrollment> approved = new java.util.ArrayList<Enrollment>();
        for (Student student : AppData.students()) {
            for (Enrollment enrollment : student.getEnrollments()) {
                if (enrollment.getStatus() == RegistrationStatus.APPROVED) {
                    approved.add(enrollment);
                }
            }
        }
        return approved;
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
}

