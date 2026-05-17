package university.employees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.support.SupportRequest;

public class TechSupportSpecialist extends Employee {
    private static final long serialVersionUID = 1L;

    private List<SupportRequest> requests;

    public TechSupportSpecialist(String id, String name, String email, String passwordHash, String employeeId, double salary) {
        super(id, name, email, passwordHash, employeeId, salary);
        this.requests = new ArrayList<SupportRequest>();
    }

    public List<SupportRequest> viewRequests() {
        return Collections.unmodifiableList(requests);
    }

    public void addRequest(SupportRequest request) {
        if (!requests.contains(request)) {
            requests.add(request);
        }
    }

    public void acceptRequest(SupportRequest request) {
        request.markViewed();
        request.accept();
    }

    public void rejectRequest(SupportRequest request) {
        request.markViewed();
        request.reject();
    }

    public void markDone(SupportRequest request) {
        request.done();
    }
}
