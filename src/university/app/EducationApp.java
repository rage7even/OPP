package university.app;

import java.util.List;
import java.util.Scanner;

import university.core.University;
import university.education.Course;
import university.education.Enrollment;
import university.education.Mark;
import university.enums.LessonType;
import university.employees.Manager;
import university.employees.Teacher;
import university.users.Student;

public final class EducationApp {
    private EducationApp() {
    }

    public static void run(Scanner scanner) {
        while (true) {
            System.out.println(I18n.t("education.title"));
            System.out.println(I18n.t("education.show.courses"));
            System.out.println(I18n.t("education.register"));
            System.out.println(I18n.t("education.approve"));
            System.out.println(I18n.t("education.put.mark"));
            System.out.println(I18n.t("education.transcript"));
            System.out.println(I18n.t("education.teachers"));
            System.out.println(I18n.t("back"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose"));
            switch (choice) {
                case 1:
                    printList(University.getInstance().getCourses());
                    break;
                case 2:
                    registerStudent();
                    break;
                case 3:
                    approvePending();
                    break;
                case 4:
                    putMark(scanner);
                    break;
                case 5:
                    showTranscript();
                    break;
                case 6:
                    showCourseTeachers();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }
        }
    }

    private static void registerStudent() {
        Student student = AppData.firstStudent();
        if (student == null) {
            System.out.println(I18n.t("no.student"));
            return;
        }
        university.education.CourseOffering offering = AppData.firstOffering();
        if (offering == null) {
            System.out.println(I18n.t("no.offering"));
            return;
        }
        Enrollment enrollment = student.registerCourse(offering);
        System.out.println(I18n.f("created.enrollment", AppFormatter.enrollment(enrollment)));
    }

    private static void approvePending() {
        Manager manager = AppData.firstManager();
        if (manager == null) {
            System.out.println(I18n.t("no.manager"));
            return;
        }
        boolean approved = false;
        for (Student student : AppData.students()) {
            for (Enrollment enrollment : student.getEnrollments()) {
                if (enrollment.getStatus() == university.enums.RegistrationStatus.PENDING) {
                    manager.approveStudentRegistration(enrollment);
                    System.out.println(I18n.f("approved", AppFormatter.enrollment(enrollment)));
                    approved = true;
                }
            }
        }
        if (!approved) {
            System.out.println(I18n.t("no.pending"));
        }
    }

    private static void putMark(Scanner scanner) {
        Teacher teacher = AppData.firstTeacher();
        if (teacher == null) {
            System.out.println(I18n.t("no.teacher"));
            return;
        }
        Enrollment enrollment = AppData.firstEnrollment();
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

    private static void showTranscript() {
        Student student = AppData.firstStudent();
        if (student == null) {
            System.out.println(I18n.t("no.student"));
            return;
        }
        System.out.println(student.getTranscript());
    }

    private static void showCourseTeachers() {
        Student student = AppData.firstStudent();
        university.education.CourseOffering offering = AppData.firstOffering();
        if (student == null) {
            System.out.println(I18n.t("no.student"));
            return;
        }
        if (offering == null) {
            System.out.println(I18n.t("no.offering"));
            return;
        }
        Course course = offering.getCourse();
        System.out.println(I18n.f("lecture", student.viewTeacherInfo(course, LessonType.LECTURE)));
        System.out.println(I18n.f("practice", student.viewTeacherInfo(course, LessonType.PRACTICE)));
    }

    private static void printList(List<Course> courses) {
        for (Course course : courses) {
            System.out.println(AppFormatter.course(course));
        }
    }
}

