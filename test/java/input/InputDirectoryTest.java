package input;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;



public class InputDirectoryTest {

	
	 @Test
	 public void should_exist_return_true() {
	       InputDirectory inputDirectory = new InputDirectory();
	        assertThat(inputDirectory.checkIfDirectoryExist()).isEqualTo(true);
	    }
	 
	 
	 
}
