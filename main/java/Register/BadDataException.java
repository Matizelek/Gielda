package Register;

public class BadDataException extends Exception{
	private final boolean arleadyExist;
	
	private String message;
	
	public BadDataException(boolean arleadyExist) {
		this.arleadyExist = arleadyExist;
        if (arleadyExist) {
            message = "U¿ytkownik o takim Loginie ju¿ istnieje";
        } else {
            message = "Has³a s¹ ró¿ne";
        }
	}
	
	 @Override
	    public String getMessage() {
	        return message;
	    }

	    public boolean isWrongPassword() {
	        return arleadyExist;
	    }
}
