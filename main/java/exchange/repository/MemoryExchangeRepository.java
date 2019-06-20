package exchange.repository;

import exchange.Exchange;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryExchangeRepository implements ExchangeRepository {

    private List<Exchange> exchanges;


    public MemoryExchangeRepository(List<Exchange> exchanges) {
        this.exchanges = exchanges;
    }

    @Override
    public List<Exchange> getAllExchanges() {
        return exchanges;
    }

    @Override
    public List<Exchange> getExchanges(List<Integer> ids) {
        return exchanges
                .stream()
                .filter(exchange -> ids.contains(exchange.getCompanyId()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Exchange> findExchange(int exchangeId) {
    	List<Exchange> list = exchanges
        .stream()
        .filter(exchange -> exchange.getCompanyId() == exchangeId).collect(Collectors.toList());
        return Optional.of(list.get(0));
    }
}
