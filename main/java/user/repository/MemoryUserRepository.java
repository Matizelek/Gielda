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
    public void addUser(String hashedPassword, String userName) {
    	 users.add(new User(hashedPassword, userName));
    }
}
