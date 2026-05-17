package university.education;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import university.enums.CourseType;
import university.enums.RegistrationStatus;
import university.users.Student;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseId;
    private String name;
    private int credits;
    private CourseType type;
    private int capacity;
    private List<Course> prerequisites;
    private List<Lesson> lessons;

    public Course(String courseId, String name, int credits, CourseType type, int capacity) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.type = type;
        this.capacity = capacity;
        this.prerequisites = new ArrayList<Course>();
        this.lessons = new ArrayList<Lesson>();
    }

    public boolean isAvailable() {
        return capacity > 0;
    }

    public boolean hasPrerequisitesFor(Student student) {
        for (Course prerequisite : prerequisites) {
            boolean passed = false;
            for (Enrollment enrollment : student.getEnrollments()) {
                Mark mark = enrollment.getMark();
                if (enrollment.getOffering().getCourse().equals(prerequisite)
                        && enrollment.getStatus() == RegistrationStatus.APPROVED
                        && mark != null && mark.getFinalGrade() >= 50) {
                    passed = true;
                    break;
                }
            }
            if (!passed) {
                return false;
            }
        }
        return true;
    }

    public void addPrerequisite(Course course) {
        if (!prerequisites.contains(course)) {
            prerequisites.add(course);
        }
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void occupySeat() {
        if (capacity > 0) {
            capacity--;
        }
    }

    public String getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public CourseType getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Course> getPrerequisites() {
        return Collections.unmodifiableList(prerequisites);
    }

    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(lessons);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return courseId + " - " + name + " (" + credits + " credits, " + type + ")";
    }
}
