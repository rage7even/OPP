package university.users;

import java.io.Serializable;
import java.util.Date;

import university.employees.Teacher;
import university.exceptions.InvalidRatingException;

public class TeacherRating implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ratingId;
    private Student author;
    private Teacher teacher;
    private int value;
    private Date date;

    public TeacherRating(String ratingId, Student author, Teacher teacher, int value) {
        this.ratingId = ratingId;
        this.author = author;
        this.teacher = teacher;
        this.value = value;
        this.date = new Date();
        validate();
    }

    public void validate() {
        if (value < 1 || value > 5) {
            throw new InvalidRatingException(value);
        }
    }

    public String getRatingId() {
        return ratingId;
    }

    public Student getAuthor() {
        return author;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public int getValue() {
        return value;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }
}
