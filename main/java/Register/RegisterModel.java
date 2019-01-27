package Register;

import java.util.List;

import hasher.PasswordHasher;
import hasher.SHAPasswordHasher;
import user.User;
import user.repository.UserRepository;

public class RegisterModel {
	String passwordFirst;
	String passwordSecound;
	String userName;
	private UserRepository userRepository;
	
	public RegisterModel(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserRepository register(String passwordFirst, String passwordSecound, String userName) throws BadDataException{
		if(chceckPasswords(passwordFirst, passwordSecound)) {
			List<User> users = userRepository.getUsers();
			if(!checkUsername(userName, users)) {
				PasswordHasher hasher = new SHAPasswordHasher();
				String hashedPassword = hasher.hashPassword(passwordFirst);
				userRepository.addUser(hashedPassword, userName);
			}else {
				throw new BadDataException(true);
			}
		}else {
			throw new BadDataException(false);
		}
		return userRepository;
	}
	
	private boolean chceckPasswords(String passwordFirst, String passwordSecound) {
		return passwordFirst.equals(passwordSecound)?true:false;
	}
	
	private boolean checkUsername(String username, List<User> users) {
        boolean contains = false;
        for (User user : users) {
            contains = user.getUsername().equals(username);
        }
        return contains;
    }
}
