package user;

import hasher.PasswordHasher;

public class UserUtils {

    public static User withPlainPassword(String plainPassword, String username, PasswordHasher hasher) {
        return new User(hasher.hashPassword(plainPassword),username, 0l);
    }
}
