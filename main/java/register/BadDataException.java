package register;

public class BadDataException extends Exception{
	private final boolean alreadyExist;
	
	private String message;
	
	public BadDataException(boolean alreadyExist) {
		this.alreadyExist = alreadyExist;
        if (alreadyExist) {
            message = "U�ytkownik o takim Loginie ju� istnieje";
        } else {
            message = "Has�a s� r�ne";
        }
	}
	
	 @Override
	    public String getMessage() {
	        return message;
	    }

	    public boolean isWrongPassword() {
	        return !alreadyExist;
	    }
}
