package user.repository;

import user.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();
    User addUser(String hashedPassword, String userName);
}
