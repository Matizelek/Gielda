package login;

public class BadCredentialsException extends Exception {
    private final boolean wrongPassword;

    private String message;

    public BadCredentialsException(boolean wrongPassword) {
        this.wrongPassword = wrongPassword;
        if (wrongPassword) {
            message = "Niepoprawne hasło";
        } else {
            message = "Nie znaleziono użytkownika";
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    public boolean isWrongPassword() {
        return wrongPassword;
    }
}
