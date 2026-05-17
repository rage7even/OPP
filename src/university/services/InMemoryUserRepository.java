package university.services;

import java.util.List;

import university.core.University;
import university.users.User;

public class InMemoryUserRepository implements UserRepository {
    private static final long serialVersionUID = 1L;

    @Override
    public User findByEmail(String email) {
        for (User user : University.getInstance().getUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findById(String userId) {
        for (User user : University.getInstance().getUsers()) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void save(User user) {
        University.getInstance().addUser(user);
    }

    @Override
    public void remove(String userId) {
        University.getInstance().removeUser(userId);
    }

    @Override
    public List<User> findAll() {
        return University.getInstance().getUsers();
    }
}
