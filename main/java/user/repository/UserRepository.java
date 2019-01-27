package user.repository;

import user.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();
    void addUser(String hashedpassword, String userName);
}
