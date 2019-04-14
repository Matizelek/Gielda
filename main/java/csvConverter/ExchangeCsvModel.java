package csvConverter;

public class ExchangeCsvModel {

    private final String name;
    private final String isin;
    private final String currency;
    private final double openingPrice;
    private final double maxPrice;
    private final double minPrice;
    private final double closingPrice;
    private final double change;
    private final double volume;
    private final int transactionAmount;
    private final double trade;

    public ExchangeCsvModel(String name,
                            String ISIN,
                            String currency,
                            double openingPrice,
                            double maxPrice,
                            double minPrice,
                            double closingPrice,
                            double change,
                            double volume,
                            int transactionAmount,
                            double trade
    ) {

        this.name = name;
        isin = ISIN;
        this.currency = currency;
        this.openingPrice = openingPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.closingPrice = closingPrice;
        this.change = change;
        this.volume = volume;
        this.transactionAmount = transactionAmount;
        this.trade = trade;
    }


    public String getName() {
        return name;
    }

    public String getIsin() {
        return isin;
    }

    public String getCurrency() {
        return currency;
    }

    public double getOpeningPrice() {
        return openingPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getClosingPrice() {
        return closingPrice;
    }

    public double getChange() {
        return change;
    }

    public double getVolume() {
        return volume;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public double getTrade() {
        return trade;
    }

    @Override
    public String toString() {
        return "ExchangeCsvModel{" +
                "name='" + name + '\'' +
                ", isin='" + isin + '\'' +
                ", currency='" + currency + '\'' +
                ", openingPrice=" + openingPrice +
                ", maxPrice=" + maxPrice +
                ", minPrice=" + minPrice +
                ", closingPrice=" + closingPrice +
                ", change=" + change +
                ", volume=" + volume +
                ", transactionAmount=" + transactionAmount +
                ", trade=" + trade +
                '}';
    }
}
