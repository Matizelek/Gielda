package emailValidationTest;

import email.EmailValidator;
import org.junit.Test;

public class EmailValidationTest {


    @Test
    public void textShouldBeCorrect() {

        String exampleText = "foobar@gmail.com";
        EmailValidator validator = new EmailValidator();
        boolean result = validator.validate(exampleText);
        assert (result);
    }

    @Test
    public void textShouldNotBeCorrect() {

        String exampleText = "foobargmail.com";
        EmailValidator validator = new EmailValidator();
        boolean result = validator.validate(exampleText);
        assert (!result);
    }

}
