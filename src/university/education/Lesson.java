package university.education;

import java.io.Serializable;
import java.util.Date;

import university.employees.Teacher;
import university.enums.LessonType;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    private LessonType type;
    private Date dateTime;
    private Teacher instructor;

    public Lesson(LessonType type, Date dateTime, Teacher instructor) {
        this.type = type;
        this.dateTime = new Date(dateTime.getTime());
        this.instructor = instructor;
    }

    public LessonType getType() {
        return type;
    }

    public Date getDateTime() {
        return new Date(dateTime.getTime());
    }

    public Teacher getInstructor() {
        return instructor;
    }

    public void setInstructor(Teacher instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return type + " by " + instructor.getName() + " at " + dateTime;
    }
}
