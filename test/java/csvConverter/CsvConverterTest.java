package csvConverter;

import exchange.Exchange;
import org.junit.Test;

import java.util.List;

public class CsvConverterTest {

    @Test
    public void csvShouldBeConverted() {

        CsvExchangeConverter converter = new CsvExchangeConverter();
        List<Exchange> exchanges = converter.convert(dasdas.content());

        for (Exchange exchange : exchanges) {
            System.out.println(exchange);
        }
        assert (exchanges.size() == 5);
    }
}
