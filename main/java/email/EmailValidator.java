package email;

import java.util.regex.Pattern;

public class EmailValidator {

    private static Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validate(String text) {
        return emailPattern.matcher(text).find();
    }
}
