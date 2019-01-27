package RegisterTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Register.BadDataException;
import Register.RegisterModel;
import user.User;
import user.repository.MemoryUserRepository;
import user.repository.UserRepository;

public class RegisterModelTest {

	@Test
	public void userShouldBeRegister() {
		List<User> testUsers = new ArrayList<>();
		UserRepository userRepository = new MemoryUserRepository(testUsers);
		RegisterModel registerTest = new RegisterModel(userRepository);
		try {
			userRepository = registerTest.register("dada", "dada", "test");
			assert(true);
		} catch (BadDataException e) {
			e.printStackTrace();
			assert(false);
		}
	}
	
	@Test
	public void userRegisterShouldBeWrong() {
		List<User> testUsers = new ArrayList<>();
		UserRepository userRepository = new MemoryUserRepository(testUsers);
		RegisterModel registerTest = new RegisterModel(userRepository);
		try {
			userRepository = registerTest.register("dada", "mama", "test");
			assert(false);
		} catch (BadDataException e) {
			e.printStackTrace();
			assert(true);
		}
	}
	
	@Test
	public void userRegisterLoginShouldBeCorrect() {
		List<User> testUsers = new ArrayList<>();
		UserRepository userRepository = new MemoryUserRepository(testUsers);
		RegisterModel registerTest = new RegisterModel(userRepository);
		try {
			userRepository = registerTest.register("dada", "dada", "test");
			userRepository = registerTest.register("dada", "dada", "test2");
			assert(true);
		} catch (BadDataException e) {
			e.printStackTrace();
			assert(false);
		}
	}
	
	@Test
	public void userRegisterLoginShouldBeWrong() {
		List<User> testUsers = new ArrayList<>();
		UserRepository userRepository = new MemoryUserRepository(testUsers);
		RegisterModel registerTest = new RegisterModel(userRepository);
		try {
			userRepository = registerTest.register("dada", "dada", "test");
			userRepository = registerTest.register("dada", "dada", "test");
			assert(false);
		} catch (BadDataException e) {
			e.printStackTrace();
			assert(true);
		}
	}
}
