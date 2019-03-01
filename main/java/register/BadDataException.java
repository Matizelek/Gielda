package register;

public class BadDataException extends Exception{
	private final boolean alreadyExist;
	
	private String message;
	
	private BadDataException(boolean alreadyExist) {
		this.alreadyExist = alreadyExist;
        if (alreadyExist) {
            message = "U�ytkownik o takim Loginie ju� istnieje";
        } else {
            message = "Has�a s� r�ne";
        }
	}

	public static BadDataException userAlreadyExists(){
		return new BadDataException(true);
	}

	public static BadDataException repeatPasswordIncorrect(){
		return new BadDataException(false);
	}
	
	 @Override
	    public String getMessage() {
	        return message;
	    }

	    public boolean isRepeatPasswordIncorrect() {
	        return !alreadyExist;
	    }
	    public boolean doesUserAlreadyExists() {
	        return alreadyExist;
	    }
}
