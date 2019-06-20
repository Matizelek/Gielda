package exchange;

import java.util.Objects;

import money.Money;

public class ExchangePurchase {

	private final Long purchaseId;
	private final Long userId;
    private final int companyId;
    private Money purchasePrice;
    private final int amount;

    public ExchangePurchase(Long purchaseId,Long userId, int companyId, Money purchasePrice, int amount) {

    	this.purchaseId = purchaseId;
        this.userId = userId;
        this.companyId = companyId;
        this.purchasePrice = purchasePrice;
        this.amount = amount;
    }
    
    public ExchangePurchase(Long purchaseId,Long userId, int companyId, Double purchasePrice, int amount) {
    	this.purchaseId = purchaseId;
        this.userId = userId;
        this.companyId = companyId;
        this.purchasePrice = new Money(purchasePrice);
        this.amount = amount;
    }

    public Long getPurchaseId() {
		return purchaseId;
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
    
    @Override
	public int hashCode() {
		return Objects.hash(userId,companyId, amount, purchasePrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		ExchangePurchase that = (ExchangePurchase) obj;
		return Objects.equals(amount,that.amount) &&
				Objects.equals(userId, that.userId) &&
				Objects.equals(companyId, that.companyId) &&
				Objects.equals(purchasePrice, that.purchasePrice);

	}
    
}
