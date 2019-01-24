package hasherTest;

import hasher.PasswordHasher;
import hasher.SHAPasswordHasher;
import org.junit.Test;

public class PasswordHasherTest {

    @Test
    public void shouldBeDifferent() {

        PasswordHasher hasher = new SHAPasswordHasher();

        String hashed = hasher.hashPassword("abcd");
        String hashed2 = hasher.hashPassword("bcda");

        assert (!hashed.equals(hashed2));

    }

    @Test
    public void shouldBeTheSame() {

        PasswordHasher hasher = new SHAPasswordHasher();

        String hashed = hasher.hashPassword("abcd");
        String hashed2 = hasher.hashPassword("abcd");

        assert (hashed.equals(hashed2));

    }

    @Test
    public void shouldBeDifferentThanPassword() {

        PasswordHasher hasher = new SHAPasswordHasher();
        String password = "abcd";
        String hashed = hasher.hashPassword(password);


        assert (!hashed.equals(password));

    }

}
