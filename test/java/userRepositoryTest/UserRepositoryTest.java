package userRepositoryTest;

import org.junit.Test;
import user.User;
import user.repository.MemoryUserRepository;
import user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UserRepositoryTest {


    @Test
    public void should_return_3_users() {

        List<User> users = new ArrayList<>();
        users.add(new User("abc", "abc", 0l));
        users.add(new User("abc", "abc", 1l));
        users.add(new User("abc", "abc", 2l));
        UserRepository repository = new MemoryUserRepository(users,new ArrayList<>());
        assertThat(repository.getUsers()).hasSize(3);

    }

    @Test
    public void should_return_account_for_user(){
        User user = new User("abc", "abc", 0l);
        List<User> users = new ArrayList<>();
        users.add(user);
        UserRepository repository = new MemoryUserRepository(users,new ArrayList<>());

        repository.getAccountForUser(user);
    }

}
