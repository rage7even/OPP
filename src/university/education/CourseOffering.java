package university.education;

import java.io.Serializable;

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
    public String toString() {
        return offeringId + ": " + course + ", major=" + major + ", year=" + year;
    }
}
