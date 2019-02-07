package login.model;

import hasher.PasswordHasher;
import login.BadCredentialsException;
import user.User;
import user.repository.UserRepository;

import java.util.List;

public class LoginModel {

    private PasswordHasher hasher;
    private UserRepository userRepository;

    public LoginModel(PasswordHasher hasher, UserRepository userRepository) {
        this.hasher = hasher;
        this.userRepository = userRepository;
    }

    public User login(String username, String password) throws BadCredentialsException {
        List<User> users = userRepository.getUsers();
        if (checkUsername(username, users)) {
            String hashed = hasher.hashPassword(password);
            boolean passwordResult = checkHashedPassword(hashed, users);
            if (passwordResult) {
                return new User(hashed, username);
            } else
                throw new BadCredentialsException(true);
        } else {
            throw new BadCredentialsException(false);
        }
    }

    private boolean checkHashedPassword(String hashed, List<User> users) {
        boolean contains = false;
        for (User user : users) {
            contains = user.getHashedPassword().equals(hashed);
            if (contains) break;
        }
        return contains;
    }


    private boolean checkUsername(String username, List<User> users) {
        boolean contains = false;
        for (User user : users) {
            contains = user.getUsername().equals(username);
        }
        return contains;
    }

}
