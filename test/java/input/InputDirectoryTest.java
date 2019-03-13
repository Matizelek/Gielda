package input;

import time.exchangeDate.ExchangeDate;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class InputDirectoryTest {


    @Test
    public void should_exist_return_true() {
        InputDirectory inputDirectory = new InputDirectory();
        assertThat(inputDirectory.checkIfDirectoryExist()).isEqualTo(true);
    }


    @Test
    public void test() {
        InputDirectory inputDirectory = new InputDirectory();
        inputDirectory.loadDirectory();

        ExchangeDate exchangeDate = new ExchangeDate("12_313_4129_akcje");
        ExchangeDate exchangeDate2 = new ExchangeDate("2019-12-11");
    }


}
