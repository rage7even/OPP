package university.support;

import java.io.Serializable;

import university.employees.TechSupportSpecialist;
import university.enums.RequestStatus;
import university.exceptions.UnauthorizedActionException;
import university.users.User;

public class SupportRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String requestId;
    private User createdBy;
    private TechSupportSpecialist assignedTo;
    private String description;
    private RequestStatus status;

    public SupportRequest(String requestId, User createdBy, String description) {
        this.requestId = requestId;
        this.createdBy = createdBy;
        this.description = description;
        this.status = RequestStatus.NEW;
    }

    public void assignTo(TechSupportSpecialist specialist) {
        this.assignedTo = specialist;
        specialist.addRequest(this);
        notifyCreator("Support request assigned: " + requestId + " -> " + specialist.getName());
    }

    public void markViewed() {
        if (status == RequestStatus.NEW) {
            status = RequestStatus.VIEWED;
        }
    }

    public void accept() {
        if (assignedTo == null) {
            throw new UnauthorizedActionException("accept unassigned support request");
        }
        status = RequestStatus.ACCEPTED;
        notifyCreator("Support request accepted: " + requestId);
    }

    public void reject() {
        status = RequestStatus.REJECTED;
        notifyCreator("Support request rejected: " + requestId);
    }

    public void done() {
        if (status != RequestStatus.ACCEPTED) {
            throw new UnauthorizedActionException("complete request before acceptance");
        }
        status = RequestStatus.DONE;
        notifyCreator("Support request completed: " + requestId);
    }

    public String getRequestId() {
        return requestId;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public TechSupportSpecialist getAssignedTo() {
        return assignedTo;
    }

    public String getDescription() {
        return description;
    }

    public RequestStatus getStatus() {
        return status;
    }

    private void notifyCreator(String message) {
        if (createdBy != null) {
            createdBy.addNotification(message);
        }
    }

    @Override
    public String toString() {
        return requestId + " [" + status + "] " + description;
    }
}
