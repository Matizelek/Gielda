package login;

public class BadCredentialsException extends Exception {
    private final boolean wrongPassword;

    private String message;

    private BadCredentialsException(boolean wrongPassword) {
        this.wrongPassword = wrongPassword;
        if (wrongPassword) {
            message = "Niepoprawne hasło";
        } else {
            message = "Nie znaleziono użytkownika";
        }
    }

    public static BadCredentialsException wrongPassword(){
        return new BadCredentialsException(true);
    }

    public static BadCredentialsException wrongUsername(){
        return new BadCredentialsException(false);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public boolean isWrongPassword() {
        return wrongPassword;
    }
}
