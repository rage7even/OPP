package university.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.core.University;
import university.education.Course;
import university.education.CourseOffering;
import university.education.Enrollment;
import university.education.Lesson;
import university.education.Mark;
import university.employees.Teacher;
import university.enums.LessonType;
import university.enums.RegistrationStatus;
import university.exceptions.TooManyFailsException;
import university.exceptions.UnauthorizedActionException;

public class Student extends User {
    private static final long serialVersionUID = 1L;

    private String studentId;
    private double gpa;
    private int credits;
    private int failCount;
    private List<Enrollment> enrollments;
    private List<StudentOrganization> organizations;

    public Student(String id, String name, String email, String passwordHash, String studentId) {
        super(id, name, email, passwordHash);
        this.studentId = studentId;
        this.enrollments = new ArrayList<Enrollment>();
        this.organizations = new ArrayList<StudentOrganization>();
    }

    public Enrollment registerCourse(CourseOffering offering) {
        return University.getInstance().getRegistrationService().register(this, offering);
    }

    public List<Mark> viewMarks() {
        List<Mark> marks = new ArrayList<Mark>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getMark() != null) {
                marks.add(enrollment.getMark());
            }
        }
        return marks;
    }

    public List<Teacher> viewTeachers(Course course) {
        List<Teacher> teachers = new ArrayList<Teacher>();
        for (Lesson lesson : course.getLessons()) {
            if (!teachers.contains(lesson.getInstructor())) {
                teachers.add(lesson.getInstructor());
            }
        }
        return teachers;
    }

    public Teacher viewTeacherInfo(Course course, LessonType lessonType) {
        for (Lesson lesson : course.getLessons()) {
            if (lesson.getType() == lessonType) {
                return lesson.getInstructor();
            }
        }
        return null;
    }

    public String getTranscript() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(getName()).append('\n');
        for (Enrollment enrollment : enrollments) {
            sb.append(enrollment).append('\n');
        }
        sb.append("GPA: ").append(String.format("%.2f", gpa));
        return sb.toString();
    }

    public void rateTeacher(Teacher teacher, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("rating must be between 1 and 5");
        }
        if (!hasApprovedEnrollmentWith(teacher)) {
            throw new UnauthorizedActionException("cannot rate teacher without approved enrollment");
        }
        teacher.addRating(new TeacherRating("R-" + getId() + "-" + teacher.getId(), this, teacher, rating));
    }

    public void recordFail() {
        failCount++;
        if (failCount > 3) {
            throw new TooManyFailsException(failCount);
        }
    }

    public void removeFail() {
        if (failCount > 0) {
            failCount--;
        }
    }

    public void addEnrollment(Enrollment enrollment) {
        if (!enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
        }
    }

    public void addCredits(int value) {
        credits += value;
        recalculateGpa();
    }

    public void recalculateGpa() {
        double sum = 0;
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            Mark mark = enrollment.getMark();
            if (mark != null) {
                sum += mark.getFinalGrade() / 25.0;
                count++;
            }
        }
        gpa = count == 0 ? 0 : Math.min(4.0, sum / count);
    }

    public String getStudentId() {
        return studentId;
    }

    public double getGpa() {
        return gpa;
    }

    public int getCredits() {
        return credits;
    }

    public int getFailCount() {
        return failCount;
    }

    public List<Enrollment> getEnrollments() {
        return Collections.unmodifiableList(enrollments);
    }

    public List<StudentOrganization> getOrganizations() {
        return Collections.unmodifiableList(organizations);
    }

    void joinOrganization(StudentOrganization organization) {
        if (!organizations.contains(organization)) {
            organizations.add(organization);
        }
    }

    void leaveOrganization(StudentOrganization organization) {
        organizations.remove(organization);
    }

    private boolean hasApprovedEnrollmentWith(Teacher teacher) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStatus() != RegistrationStatus.APPROVED) {
                continue;
            }
            for (Lesson lesson : enrollment.getOffering().getCourse().getLessons()) {
                if (teacher.equals(lesson.getInstructor())) {
                    return true;
                }
            }
        }
        return false;
    }
}
