package exchange;

import money.Money;

public class ExchangePurchase {

    private final int userId;
    private final int exchangeId;
    private final Money purchasePrice;
    private final int amount;

    public ExchangePurchase(int userId, int exchangeId, Money purchasePrice, int amount) {

        this.userId = userId;
        this.exchangeId = exchangeId;
        this.purchasePrice = purchasePrice;
        this.amount = amount;
    }
    
    public ExchangePurchase(int userId, int exchangeId, Double purchasePrice, int amount) {

        this.userId = userId;
        this.exchangeId = exchangeId;
        this.purchasePrice = new Money(purchasePrice);
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public int getExchangeId() {
        return exchangeId;
    }

    public Money getPurchasePrice() {
        return purchasePrice;
    }

    public int getAmount() {
        return amount;
    }
}
