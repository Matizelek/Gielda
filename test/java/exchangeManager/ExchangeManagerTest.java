package exchangeManager;

import exchange.Exchange;
import exchange.ExchangeManager;
import exchange.ExchangeSale;
import org.junit.Test;
import time.timeOfDay.TimeOfDay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeManagerTest {

    @Test
    public void onFirstQShouldBe15ZL() {


        double openingPrice = 15;
        List<Exchange> exchanges = new ArrayList<>();
        Exchange exchange = new Exchange(1, "11BIT", openingPrice, 100, 8, 10);
        exchanges.add(exchange);


        ExchangeManager manager = new ExchangeManager(exchanges);

        Optional<ExchangeSale> foundFirstQ = manager.getByName("11BIT", TimeOfDay.FIRST_Q);
        boolean firstQResult = foundFirstQ.get().getPrice().getCount() == openingPrice * 100L;
        assert (firstQResult);

    }

    @Test
    public void onForthQShouldBe10ZL() {


        double closingPrice = 10;
        List<Exchange> exchanges = new ArrayList<>();
        Exchange exchange = new Exchange(1, "11BIT", 15, 100, 8, closingPrice);
        exchanges.add(exchange);


        ExchangeManager manager = new ExchangeManager(exchanges);

        Optional<ExchangeSale> foundFirstQ = manager.getByName("11BIT", TimeOfDay.FORTH_Q);
        long count = foundFirstQ.get().getPrice().getCount();
        long expected = (long)closingPrice * 100L;

        assertThat(count).isEqualTo(expected);


    }
}
