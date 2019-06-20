package login.model;

import hasher.PasswordHasher;
import login.BadCredentialsException;
import user.User;
import user.repository.UserRepository;

import java.util.Optional;

public class LoginModel {

    private PasswordHasher hasher;
    private UserRepository userRepository;

    public LoginModel(PasswordHasher hasher, UserRepository userRepository) {
        this.hasher = hasher;
        this.userRepository = userRepository;
    }

    public User login(String username, String password) throws BadCredentialsException {

        Optional<User> foundUser = userRepository.getUserByName(username);

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            boolean passwordResult = checkPassword(password, user);

            if (passwordResult) {
                return user;
            } else {
                throw BadCredentialsException.wrongPassword();
            }

        } else {
            throw BadCredentialsException.wrongUsername();
        }

    }

    private boolean checkPassword(String password, User user) {
        String hashed = hasher.hashPassword(password);
        return user.getHashedPassword().equals(hashed);
    }

}
