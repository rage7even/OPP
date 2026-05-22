package university.services;

import java.io.Serializable;

import university.core.University;
import university.education.Course;
import university.education.CourseOffering;
import university.education.Enrollment;
import university.employees.Manager;
import university.enums.RegistrationStatus;
import university.exceptions.CourseNotAvailableException;
import university.exceptions.CreditLimitExceededException;
import university.exceptions.PrerequisiteNotMetException;
import university.exceptions.TooManyFailsException;
import university.users.Student;

public class CourseRegistrationService implements Serializable {
    private static final long serialVersionUID = 1L;

    public Enrollment register(Student student, CourseOffering offering) {
        Course course = offering.getCourse();
        int attempted = selectedCredits(student) + course.getCredits();
        if (attempted > 21) {
            throw new CreditLimitExceededException(selectedCredits(student), attempted);
        }
        if (student.getFailCount() > 3) {
            throw new TooManyFailsException(student.getFailCount());
        }
        if (!course.isAvailable()) {
            throw new CourseNotAvailableException(course.getCourseId());
        }
        if (hasActiveEnrollment(student, course)) {
            throw new IllegalStateException("student already has an active registration for " + course.getCourseId());
        }
        if (!course.hasPrerequisitesFor(student)) {
            throw new PrerequisiteNotMetException(course.getCourseId());
        }
        Enrollment enrollment = new Enrollment("ENR-" + System.nanoTime(), student, offering);
        student.addEnrollment(enrollment);
        University.getInstance().getLogService().log(student, "Registered for " + course.getName());
        return enrollment;
    }

    public void approve(Manager manager, Enrollment enrollment) {
        if (enrollment.getStatus() == RegistrationStatus.APPROVED) {
            return;
        }
        if (enrollment.getStatus() == RegistrationStatus.REJECTED) {
            throw new IllegalStateException("rejected enrollment cannot be approved");
        }
        Course course = enrollment.getOffering().getCourse();
        int attempted = enrollment.getStudent().getCredits() + course.getCredits();
        if (attempted > 21) {
            throw new CreditLimitExceededException(enrollment.getStudent().getCredits(), attempted);
        }
        if (!course.isAvailable()) {
            throw new CourseNotAvailableException(course.getCourseId());
        }
        enrollment.approve();
        enrollment.getStudent().addNotification("Registration approved: "
                + enrollment.getOffering().getCourse().getName()
                + " (" + enrollment.getEnrollmentId() + ")");
        University.getInstance().getLogService().log(manager, "Approved " + enrollment.getEnrollmentId());
    }

    public void reject(Manager manager, Enrollment enrollment) {
        if (enrollment.getStatus() == RegistrationStatus.APPROVED) {
            throw new IllegalStateException("approved enrollment cannot be rejected");
        }
        enrollment.reject();
        enrollment.getStudent().addNotification("Registration rejected: "
                + enrollment.getOffering().getCourse().getName()
                + " (" + enrollment.getEnrollmentId() + ")");
        University.getInstance().getLogService().log(manager, "Rejected " + enrollment.getEnrollmentId());
    }

    private int selectedCredits(Student student) {
        int credits = 0;
        for (Enrollment enrollment : student.getEnrollments()) {
            if (enrollment.getStatus() != RegistrationStatus.REJECTED) {
                credits += enrollment.getOffering().getCourse().getCredits();
            }
        }
        return credits;
    }

    private boolean hasActiveEnrollment(Student student, Course course) {
        for (Enrollment enrollment : student.getEnrollments()) {
            if (enrollment.getStatus() != RegistrationStatus.REJECTED
                    && enrollment.getOffering().getCourse().equals(course)) {
                return true;
            }
        }
        return false;
    }
}
