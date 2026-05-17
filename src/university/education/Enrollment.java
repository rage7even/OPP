package university.education;

import java.io.Serializable;

import university.enums.RegistrationStatus;
import university.users.Student;

public class Enrollment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String enrollmentId;
    private Student student;
    private CourseOffering offering;
    private RegistrationStatus status;
    private Mark mark;

    public Enrollment(String enrollmentId, Student student, CourseOffering offering) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.offering = offering;
        this.status = RegistrationStatus.PENDING;
    }

    public void approve() {
        if (status != RegistrationStatus.APPROVED) {
            status = RegistrationStatus.APPROVED;
            offering.getCourse().occupySeat();
            student.addCredits(offering.getCourse().getCredits());
        }
    }

    public void reject() {
        status = RegistrationStatus.REJECTED;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
        if (mark.getFinalGrade() < 50) {
            student.recordFail();
        }
        student.recalculateGpa();
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public CourseOffering getOffering() {
        return offering;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public Mark getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return enrollmentId + " " + offering.getCourse().getName() + " " + status
                + (mark == null ? "" : " " + mark);
    }
}
