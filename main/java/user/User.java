package user;

import hasher.PasswordHasher;

import java.util.Objects;

public class User {
    private final String hashedPassword;
    private final String username;

    public User(String hashedPassword, String username) {
        this.hashedPassword = hashedPassword;
        this.username = username;
    }

    public static User withPlainPassword(String plainPassword, String username, PasswordHasher hasher) {
        return new User(hasher.hashPassword(plainPassword),username);
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username);
    }
}