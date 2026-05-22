package university.employees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.communication.Complaint;
import university.education.Course;
import university.education.Enrollment;
import university.education.Lesson;
import university.education.Mark;
import university.enums.TeacherPosition;
import university.enums.Urgency;
import university.exceptions.UnauthorizedActionException;
import university.research.ResearcherProfile;
import university.users.Student;
import university.users.TeacherRating;

public class Teacher extends Employee {
    private static final long serialVersionUID = 1L;

    private TeacherPosition position;
    private List<Course> courses;
    private List<TeacherRating> ratings;

    public Teacher(String id, String name, String email, String passwordHash, String employeeId, double salary,
            TeacherPosition position) {
        super(id, name, email, passwordHash, employeeId, salary);
        this.position = position;
        this.courses = new ArrayList<Course>();
        this.ratings = new ArrayList<TeacherRating>();
        if (position == TeacherPosition.PROFESSOR) {
            setResearcherProfile(new ResearcherProfile("RP-" + id, this, "Faculty"));
        }
    }

    public List<Course> viewCourses() {
        return Collections.unmodifiableList(courses);
    }

    public void assignCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void promoteToProfessor() {
        position = TeacherPosition.PROFESSOR;
        if (getResearcherProfile() == null) {
            setResearcherProfile(new ResearcherProfile("RP-" + getId(), this, "Faculty"));
        }
    }

    public void putMark(Enrollment enrollment, Mark mark) {
        if (!teaches(enrollment.getOffering().getCourse())) {
            throw new UnauthorizedActionException("put mark for course not assigned to teacher");
        }
        enrollment.setMark(mark);
    }

    private boolean teaches(Course course) {
        if (courses.contains(course)) {
            return true;
        }
        for (Lesson lesson : course.getLessons()) {
            if (this.equals(lesson.getInstructor())) {
                return true;
            }
        }
        return false;
    }

    public Complaint sendComplaint(Manager dean, List<Student> students, Urgency urgency, String text) {
        return new Complaint("CMP-" + System.nanoTime(), this, dean, students, urgency, text);
    }

    public List<Student> viewStudents(Course course) {
        List<Student> result = new ArrayList<Student>();
        for (university.users.User user : university.core.University.getInstance().getUsers()) {
            if (user instanceof Student) {
                Student student = (Student) user;
                for (Enrollment enrollment : student.getEnrollments()) {
                    if (enrollment.getOffering().getCourse().equals(course) && !result.contains(student)) {
                        result.add(student);
                    }
                }
            }
        }
        return result;
    }

    public void addRating(TeacherRating rating) {
        ratings.add(rating);
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for (TeacherRating rating : ratings) {
            sum += rating.getValue();
        }
        return (double) sum / ratings.size();
    }

    public TeacherPosition getPosition() {
        return position;
    }

    public List<TeacherRating> getRatings() {
        return Collections.unmodifiableList(ratings);
    }
}
