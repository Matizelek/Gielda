package loginTest;

import hasher.PasswordHasher;
import hasher.SHAPasswordHasher;
import login.BadCredentialsException;
import login.model.LoginModel;
import org.junit.Test;
import user.User;
import user.UserUtils;
import user.repository.MemoryUserRepository;
import user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class LoginModelTest {


    @SuppressWarnings("unused")
	@Test
    public void userShouldBeLoggedIn() {
        PasswordHasher hasher = new SHAPasswordHasher();

        List<User> testUsers = new ArrayList<>();
        testUsers.add(UserUtils.withPlainPassword("abcd", "test", hasher));
        UserRepository userRepository = new MemoryUserRepository(testUsers,new ArrayList<>());
        LoginModel model = new LoginModel(hasher, userRepository);

        try {
            User result = model.login("test", "abcd");
            assert (true);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            assert (false);
        }

    }

    @SuppressWarnings("unused")
	@Test
    public void passwordShouldBeWrong() {
        List<User> testUsers = new ArrayList<>();
        testUsers.add(new User("abcde", "test", 0l));

        UserRepository userRepository = new MemoryUserRepository(testUsers,new ArrayList<>());
        PasswordHasher hasher = new SHAPasswordHasher();
        LoginModel model = new LoginModel(hasher, userRepository);

        try {
            User result = model.login("test", "abcd");
            assert (false);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            assert (e.isWrongPassword());
        }
    }


    @SuppressWarnings("unused")
	@Test
    public void loginShouldBeWrong() {
        List<User> testUsers = new ArrayList<>();
        testUsers.add(new User("abcd", "testdsadas", 0l));

        UserRepository userRepository = new MemoryUserRepository(testUsers,new ArrayList<>());
        PasswordHasher hasher = new SHAPasswordHasher();
        LoginModel model = new LoginModel(hasher, userRepository);

        try {
            User result = model.login("test", "abcd");
            assert (false);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            assert (!e.isWrongPassword());
        }
    }

}
