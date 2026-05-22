package university.education;

import java.io.Serializable;
import java.util.Objects;

public class CourseOffering implements Serializable {
    private static final long serialVersionUID = 1L;

    private String offeringId;
    private Course course;
    private String major;
    private int year;

    public CourseOffering(String offeringId, Course course, String major, int year) {
        this.offeringId = offeringId;
        this.course = course;
        this.major = major;
        this.year = year;
    }

    public String getOfferingId() {
        return offeringId;
    }

    public Course getCourse() {
        return course;
    }

    public String getMajor() {
        return major;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseOffering)) {
            return false;
        }
        CourseOffering that = (CourseOffering) o;
        return Objects.equals(offeringId, that.offeringId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offeringId);
    }

    @Override
    public String toString() {
        return offeringId + ": " + course + ", major=" + major + ", year=" + year;
    }
}
