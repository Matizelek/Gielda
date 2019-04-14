package exchange.repository;

import exchange.Exchange;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepository {

    List<Exchange> getAllExchanges();

    List<Exchange> getExchanges(List<Integer> ids);

    Optional<Exchange> findExchange(int exchangeId);

}
