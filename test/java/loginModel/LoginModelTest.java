package loginModel;

import hasher.PasswordHasher;
import hasher.SHAPasswordHasher;
import login.BadCredentialsException;
import login.LoginModel;
import org.junit.Test;
import user.User;
import user.repository.MemoryUserRepository;
import user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class LoginModelTest {


    @Test
    public void userShouldBeLoggedIn() {
        PasswordHasher hasher = new SHAPasswordHasher();

        List<User> testUsers = new ArrayList<>();
        testUsers.add(User.withPlainPassword("abcd", "test", hasher));
        UserRepository userRepository = new MemoryUserRepository(testUsers);
        LoginModel model = new LoginModel(hasher, userRepository);

        try {
            User result = model.login("test", "abcd");
            assert (true);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            assert (false);
        }

    }

    @Test
    public void passwordShouldBeWrong() {
        List<User> testUsers = new ArrayList<>();
        testUsers.add(new User("abcde", "test"));

        UserRepository userRepository = new MemoryUserRepository(testUsers);
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


    @Test
    public void loginShouldBeWrong() {
        List<User> testUsers = new ArrayList<>();
        testUsers.add(new User("abcd", "testdsadas"));

        UserRepository userRepository = new MemoryUserRepository(testUsers);
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
