package register.model;

import hasher.PasswordHasher;
import register.BadDataException;
import user.User;
import user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class RegisterModel {

    private PasswordHasher hasher;
    private UserRepository userRepository;

    public RegisterModel(PasswordHasher hasher, UserRepository userRepository) {
        this.hasher = hasher;
        this.userRepository = userRepository;
    }

    public User register(String passwordFirst, String passwordSecond, String username) throws BadDataException {
        if (checkPasswords(passwordFirst, passwordSecond)) {
            Optional<User> foundUser = userRepository.getUserByName(username);
            if (foundUser.isPresent()) {
                throw BadDataException.userAlreadyExists();
            } else {
                String hashedPassword = hasher.hashPassword(passwordFirst);
                return userRepository.addUser(hashedPassword, username);
            }
        } else {
            throw BadDataException.repeatPasswordIncorrect();
        }
    }

    private boolean checkPasswords(String passwordFirst, String passwordSecond) {
        return passwordFirst.equals(passwordSecond);
    }

    private boolean checkUsername(String username, List<User> users) {
        boolean contains = false;
        for (User user : users) {
            contains = user.getUsername().equals(username);
            if (contains) return contains;
        }
        return contains;
    }
}
