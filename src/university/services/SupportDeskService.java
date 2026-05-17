package university.services;

import java.io.Serializable;

import university.core.University;
import university.employees.TechSupportSpecialist;
import university.support.SupportRequest;
import university.users.User;

public class SupportDeskService implements Serializable {
    private static final long serialVersionUID = 1L;

    public SupportRequest createRequest(User createdBy, String description) {
        SupportRequest request = new SupportRequest("SUP-" + System.nanoTime(), createdBy, description);
        University.getInstance().addSupportRequest(request);
        University.getInstance().getLogService().log(createdBy, "Created support request");
        return request;
    }

    public void assignToSpecialist(SupportRequest request, TechSupportSpecialist specialist) {
        request.assignTo(specialist);
        request.markViewed();
    }
}
