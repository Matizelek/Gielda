package input;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class InputDirectoryTest {


    @Test
    public void should_exist_return_true() {
        InputDirectory inputDirectory = new InputDirectory();
        assertThat(inputDirectory.checkIfDirectoryExist()).isEqualTo(true);
    }



}
