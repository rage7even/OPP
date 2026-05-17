package university.services;

import java.io.Serializable;

import university.core.University;
import university.users.User;

public class UserManagementService implements Serializable {
    private static final long serialVersionUID = 1L;

    public void addUser(User user) {
        University.getInstance().addUser(user);
        University.getInstance().getLogService().log(user, "User added");
    }

    public void removeUser(String userId) {
        University.getInstance().removeUser(userId);
        University.getInstance().getLogService().log(null, "User removed: " + userId);
    }

    public void updateUser(User user) {
        University.getInstance().removeUser(user.getId());
        University.getInstance().addUser(user);
        University.getInstance().getLogService().log(user, "User updated");
    }
}
