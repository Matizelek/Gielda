package user.repository;

import user.User;
import user.account.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> getUsers();

    User addUser(String hashedPassword, String userName);

    UserAccount getAccountForUser(User user);

    Optional<User> getUserByName(String username);
}

