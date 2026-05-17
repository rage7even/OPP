package university.patterns;

import java.io.Serializable;

import university.enums.UserRole;
import university.users.User;

public interface UserFactory extends Serializable {
    User create(UserRole role, String id, String name, String email, String passwordHash);
}
