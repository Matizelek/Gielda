package register.model;

import java.util.List;

import hasher.PasswordHasher;
import hasher.SHAPasswordHasher;
import register.BadDataException;
import user.User;
import user.repository.UserRepository;

public class RegisterModel {
	String passwordFirst;
	String passwordSecond;
	String userName;
	private UserRepository userRepository;
	
	public RegisterModel(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User register(String passwordFirst, String passwordSecond, String userName) throws BadDataException {
		if(checkPasswords(passwordFirst, passwordSecond)) {
			List<User> users = userRepository.getUsers();
			if(!checkUsername(userName, users)) {
				PasswordHasher hasher = new SHAPasswordHasher();
				String hashedPassword = hasher.hashPassword(passwordFirst);
				return userRepository.addUser(hashedPassword, userName);
			}else {
				throw new BadDataException(true);
			}
		}else {
			throw new BadDataException(false);
		}

	}
	
	private boolean checkPasswords(String passwordFirst, String passwordSecond) {
		return passwordFirst.equals(passwordSecond);
	}
	
	private boolean checkUsername(String username, List<User> users) {
        boolean contains = false;
        for (User user : users) {
            contains = user.getUsername().equals(username);
            if(contains) return contains;
        }
        return contains;
    }
}
