package exchange;

import money.Money;

public class ExchangeSale {

    private final String name;
    private final Money price;

    public ExchangeSale(String name,
                        Money price) {

        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}
