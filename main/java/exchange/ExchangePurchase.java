package exchange;

import money.Money;

public class ExchangePurchase {

    private final int userId;
    private final int companyId;
    private final Money purchasePrice;
    private final int amount;

    public ExchangePurchase(int userId, int companyId, Money purchasePrice, int amount) {

        this.userId = userId;
        this.companyId = companyId;
        this.purchasePrice = purchasePrice;
        this.amount = amount;
    }
    
    public ExchangePurchase(int userId, int companyId, Double purchasePrice, int amount) {

        this.userId = userId;
        this.companyId = companyId;
        this.purchasePrice = new Money(purchasePrice);
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public Money getPurchasePrice() {
        return purchasePrice;
    }

    public int getAmount() {
        return amount;
    }
}
