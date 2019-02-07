package user.repository;

import user.User;

import java.util.List;

public class MemoryUserRepository implements UserRepository {

    private List<User> users;

    public MemoryUserRepository(List<User> users) {
        this.users = users;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
    
    @Override
    public User addUser(String hashedPassword, String userName) {
        User user = new User(hashedPassword, userName);
        users.add(user);
        return user;
    }
}
