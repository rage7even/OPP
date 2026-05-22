package university.services;

import java.io.Serializable;

import university.exceptions.UnauthorizedActionException;
import university.users.User;

public class AuthService implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserRepository userRepo;

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Session login(String email, String password) {
        User user = userRepo.findByEmail(email);
        if (user == null || !user.passwordMatches(password)) {
            throw new UnauthorizedActionException("login failed for " + email);
        }
        return new Session("S-" + System.nanoTime(), user);
    }

    public void logout(Session session) {
        if (session != null) {
            session.clear();
        }
    }
}
