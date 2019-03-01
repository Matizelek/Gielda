package user.repository;

import user.User;
import user.account.UserAccount;

import java.util.List;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private List<User> users;
    private List<UserAccount> accountList;

    private static int userIdCounter = 0;

    public MemoryUserRepository(List<User> users, List<UserAccount> accountList) {
        this.users = users;
        this.accountList = accountList;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User addUser(String hashedPassword, String userName) {
        User user = new User(hashedPassword, userName, ++userIdCounter);
        users.add(user);
        return user;
    }

    @Override
    public UserAccount getAccountForUser(User user) {
        return null;
    }

    @Override
    public Optional<User> getUserByName(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}
