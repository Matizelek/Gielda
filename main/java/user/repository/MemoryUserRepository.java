package user.repository;

import user.User;
import user.account.UserAccount;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryUserRepository implements UserRepository {

    private List<User> users;
    private List<UserAccount> accountList;

    private static Long userIdCounter = 0l;

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
        return accountList.stream().filter(account -> account.getUserId().equals(user.getId())).collect(Collectors.toList()).get(0);
    }

    @Override
    public Optional<User> getUserByName(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}
