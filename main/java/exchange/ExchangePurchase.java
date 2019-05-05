package exchange;

import money.Money;

public class ExchangePurchase {

    private final Long userId;
    private final int companyId;
    private final Money purchasePrice;
    private final int amount;

    public ExchangePurchase(Long userId, int companyId, Money purchasePrice, int amount) {

        this.userId = userId;
        this.companyId = companyId;
        this.purchasePrice = purchasePrice;
        this.amount = amount;
    }
    
    public ExchangePurchase(Long userId, int companyId, Double purchasePrice, int amount) {

        this.userId = userId;
        this.companyId = companyId;
        this.purchasePrice = new Money(purchasePrice);
        this.amount = amount;
    }

    public Long getUserId() {
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
