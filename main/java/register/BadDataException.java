package register;

@SuppressWarnings("serial")
public class BadDataException extends Exception{
	private final boolean alreadyExist;
	
	private String message;
	
	private BadDataException(boolean alreadyExist) {
		this.alreadyExist = alreadyExist;
        if (alreadyExist) {
            message = "U¿ytkownik o takim loginie ju¿ istnieje!";
        } else {
            message = "Has³a s¹ ró¿ne!";
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
