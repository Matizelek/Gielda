package exchange;

import money.Money;
import time.timeOfDay.TimeOfDay;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class ExchangeManager {

    private final List<Exchange> allExchanges;

    public ExchangeManager(List<Exchange> allExchanges) {
        this.allExchanges = allExchanges;
    }

    public Optional<ExchangeSale> getByName(String companyName, TimeOfDay timeOfDay) {
        return allExchanges
                .stream()
                .filter(exchange -> exchange.getName().equals(companyName))
                .map(exchange -> mapToExchangeSale(timeOfDay, exchange))
                .findFirst();
    }

    public List<ExchangeSale> getAll(TimeOfDay timeOfDay) {
        return allExchanges
                .stream()
                .map(exchange -> mapToExchangeSale(timeOfDay, exchange))
                .collect(Collectors.toList());
    }

    private ExchangeSale mapToExchangeSale(TimeOfDay timeOfDay, Exchange exchange) {
        Money price = getPrice(exchange, timeOfDay);
        return new ExchangeSale(exchange.getName(), price);
    }

    private Money getPrice(Exchange exchange, TimeOfDay timeOfDay) {

        if (timeOfDay == TimeOfDay.FIRST_Q) {
            return new Money(exchange.getOpeningPrice());
        }

        if (timeOfDay == TimeOfDay.FORTH_Q) {
            return new Money(exchange.getClosingPrice());
        }

        boolean takeMin = new Random().nextBoolean();
        if (takeMin) return new Money(exchange.getMinPrice());
        else return new Money(exchange.getMaxPrice());

    }


}
