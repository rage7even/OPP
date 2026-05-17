package university.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentOrganization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orgId;
    private String name;
    private String description;
    private List<Student> members;
    private Student head;

    public StudentOrganization(String orgId, String name, String description) {
        this.orgId = orgId;
        this.name = name;
        this.description = description;
        this.members = new ArrayList<Student>();
    }

    public void addMember(Student student) {
        if (!members.contains(student)) {
            members.add(student);
            student.joinOrganization(this);
        }
    }

    public void removeMember(Student student) {
        members.remove(student);
        student.leaveOrganization(this);
        if (student.equals(head)) {
            head = null;
        }
    }

    public void assignHead(Student student) {
        addMember(student);
        head = student;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Student> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public Student getHead() {
        return head;
    }
}
