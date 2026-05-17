package university.services;

import java.io.Serializable;

import university.core.University;
import university.education.Course;
import university.education.CourseOffering;
import university.education.Enrollment;
import university.employees.Manager;
import university.exceptions.CourseNotAvailableException;
import university.exceptions.CreditLimitExceededException;
import university.exceptions.PrerequisiteNotMetException;
import university.exceptions.TooManyFailsException;
import university.users.Student;

public class CourseRegistrationService implements Serializable {
    private static final long serialVersionUID = 1L;

    public Enrollment register(Student student, CourseOffering offering) {
        Course course = offering.getCourse();
        int attempted = student.getCredits() + course.getCredits();
        if (attempted > 21) {
            throw new CreditLimitExceededException(student.getCredits(), attempted);
        }
        if (student.getFailCount() > 3) {
            throw new TooManyFailsException(student.getFailCount());
        }
        if (!course.isAvailable()) {
            throw new CourseNotAvailableException(course.getCourseId());
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
        enrollment.approve();
        University.getInstance().getLogService().log(manager, "Approved " + enrollment.getEnrollmentId());
    }
}
