package university.communication;

import java.io.Serializable;
import java.util.Date;

import university.employees.Employee;
import university.employees.Manager;

public class OfficialRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String requestId;
    private Employee createdBy;
    private Manager signedBy;
    private String description;
    private Date date;
    private boolean rejected;

    public OfficialRequest(String requestId, Employee createdBy, String description) {
        this.requestId = requestId;
        this.createdBy = createdBy;
        this.description = description;
        this.date = new Date();
    }

    public void sign(Manager manager) {
        this.signedBy = manager;
        this.rejected = false;
        createdBy.addNotification("Official request signed: " + requestId + " by " + manager.getName());
    }

    public void reject() {
        this.signedBy = null;
        this.rejected = true;
        createdBy.addNotification("Official request rejected: " + requestId);
    }

    public String getRequestId() {
        return requestId;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public Manager getSignedBy() {
        return signedBy;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public boolean isRejected() {
        return rejected;
    }

    @Override
    public String toString() {
        String state = rejected ? "REJECTED" : signedBy == null ? "WAITING" : "SIGNED by " + signedBy.getName();
        return requestId + " " + state + ": " + description;
    }
}
