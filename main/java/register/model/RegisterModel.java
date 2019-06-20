package register.model;

import hasher.PasswordHasher;
import register.BadDataException;
import user.User;
import user.repository.UserRepository;

import java.util.Optional;

import org.junit.platform.commons.util.StringUtils;

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
    	if(StringUtils.isBlank(passwordFirst)) {
    		return false;
    	}
        return passwordFirst.equals(passwordSecond);
    }
}
