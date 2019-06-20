package boxMessage;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class BoxMessage {

	
	public BoxMessage(Shell parent,int style) {
		new MessageBox(parent, style);
	}
	
	public static int showMessage(Shell parent, String title, String message, int style) {
		MessageBox messageBox = new MessageBox(parent, style);
		messageBox.setMessage(message);
		messageBox.setText(title);
		return messageBox.open();
	}
	
}
