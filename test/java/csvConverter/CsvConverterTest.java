package csvConverter;

import org.junit.Test;

import java.util.List;

public class CsvConverterTest {

    @Test
    public void csvShouldBeConverted() {

        CsvExchangeConverter converter = new CsvExchangeConverter();
        List<ExchangeCsvModel> exchanges = converter.convert(dasdas.content());

        for (ExchangeCsvModel exchange : exchanges) {
            System.out.println(exchange);
        }
        assert (exchanges.size() == 5);
    }
}
