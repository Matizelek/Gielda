package login;

@SuppressWarnings("serial")
public class BadCredentialsException extends Exception {
    private final boolean wrongPassword;

    private String message;

    private BadCredentialsException(boolean wrongPassword) {
        this.wrongPassword = wrongPassword;
        if (wrongPassword) {
            message = "Niepoprawne has³o";
        } else {
            message = "Nie znaleziono u¿ytkownika";
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
