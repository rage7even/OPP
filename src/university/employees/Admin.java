package university.employees;

import java.util.List;

import university.core.University;
import university.reports.LogEntry;
import university.users.User;

public class Admin extends Employee {
    private static final long serialVersionUID = 1L;

    public Admin(String id, String name, String email, String passwordHash, String employeeId, double salary) {
        super(id, name, email, passwordHash, employeeId, salary);
    }

    public void addUser(User user) {
        University.getInstance().getUserManagementService().addUser(user);
    }

    public void removeUser(String userId) {
        University.getInstance().getUserManagementService().removeUser(userId);
    }

    public void updateUser(User user) {
        University.getInstance().getUserManagementService().updateUser(user);
    }

    public List<LogEntry> viewLogFiles() {
        return University.getInstance().getLogService().getEntries();
    }
}
