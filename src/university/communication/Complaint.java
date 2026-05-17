package university.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import university.employees.Manager;
import university.employees.Teacher;
import university.enums.Urgency;
import university.users.Student;

public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;

    private String complaintId;
    private Teacher sender;
    private Manager dean;
    private List<Student> students;
    private Urgency urgency;
    private String text;
    private Date date;

    public Complaint(String complaintId, Teacher sender, Manager dean, List<Student> students, Urgency urgency, String text) {
        this.complaintId = complaintId;
        this.sender = sender;
        this.dean = dean;
        this.students = new ArrayList<Student>(students);
        this.urgency = urgency;
        this.text = text;
        this.date = new Date();
    }

    public String getComplaintId() {
        return complaintId;
    }

    public Teacher getSender() {
        return sender;
    }

    public Manager getDean() {
        return dean;
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    @Override
    public String toString() {
        return urgency + " complaint from " + sender.getName() + ": " + text;
    }
}
