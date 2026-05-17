package university.services;

import java.io.Serializable;
import java.util.List;

import university.users.User;

public interface UserRepository extends Serializable {
    User findByEmail(String email);
    User findById(String userId);
    void save(User user);
    void remove(String userId);
    List<User> findAll();
}
