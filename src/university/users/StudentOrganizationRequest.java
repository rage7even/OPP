package university.users;

import java.io.Serializable;

import university.enums.RegistrationStatus;

public class StudentOrganizationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String requestId;
    private Student student;
    private String organizationId;
    private String organizationName;
    private String description;
    private RegistrationStatus status;

    public StudentOrganizationRequest(String requestId, Student student, String organizationId, String organizationName,
            String description) {
        this.requestId = requestId;
        this.student = student;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.description = description;
        this.status = RegistrationStatus.PENDING;
    }

    public void approve() {
        if (status == RegistrationStatus.REJECTED) {
            throw new IllegalStateException("rejected organization request cannot be approved");
        }
        if (status != RegistrationStatus.APPROVED) {
            status = RegistrationStatus.APPROVED;
            student.addNotification("Organization request approved: " + organizationName + " (" + organizationId + ")");
        }
    }

    public void reject() {
        if (status == RegistrationStatus.APPROVED) {
            throw new IllegalStateException("approved organization request cannot be rejected");
        }
        status = RegistrationStatus.REJECTED;
        student.addNotification("Organization request rejected: " + organizationName + " (" + organizationId + ")");
    }

    public String getRequestId() {
        return requestId;
    }

    public Student getStudent() {
        return student;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getDescription() {
        return description;
    }

    public RegistrationStatus getStatus() {
        return status;
    }
}
