package exchange;

import time.timeOfDay.TimeOfDay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExchangeManager {

    private final List<Exchange> exchanges;

    public ExchangeManager(List<Exchange> exchanges) {
        this.exchanges = exchanges;
    }

    public Optional<ExchangeSale> getByName(String companyName, TimeOfDay timeOfDay) {
        return exchanges
                .stream()
                .filter(exchange -> exchange.getCompanyName().equals(companyName))
                .map(exchange -> exchange.createExchangeSale(timeOfDay))
                .findFirst();
    }

    public List<ExchangeSale> getAll(TimeOfDay timeOfDay) {
        return exchanges
                .stream()
                .map(exchange -> exchange.createExchangeSale(timeOfDay))
                .collect(Collectors.toList());
    }


}
