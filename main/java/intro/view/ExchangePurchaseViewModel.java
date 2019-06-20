package intro.view;

import java.util.Objects;

public class ExchangePurchaseViewModel {
	private final int companyId;
    private final String companyName;
    private final int amount;
    private final String currentValue;
    private final String purchaseValue;
    private final String currentValueMultiplyAmount;
    private final String purchaseValueMultiplyAmount;

    public ExchangePurchaseViewModel( int companyId, String companyName, int amount, String currentValue, String purchaseValue, String currentValueMultiplyAmount, String purchaseValueMultiplyAmount ) {
    	this.companyId = companyId;
        this.companyName = companyName;
        this.amount = amount;
        this.currentValue = currentValue;
        this.purchaseValue = purchaseValue;
        this.currentValueMultiplyAmount = currentValueMultiplyAmount;
        this.purchaseValueMultiplyAmount = purchaseValueMultiplyAmount;
    }

    public int getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
        return companyName;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public String getPurchaseValue() {
        return purchaseValue;
    }

    public String getCurrentValueMultiplyAmount() {
		return currentValueMultiplyAmount;
	}

	public String getPurchaseValueMultiplyAmount() {
		return purchaseValueMultiplyAmount;
	}

	@Override
    public String toString() {
        return "ExchangePurchaseViewModel{" +
                "companyName='" + companyName + '\'' +
                ", amount=" + amount +
                ", currentValue='" + currentValue + '\'' +
                ", purchaseValue='" + purchaseValue + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangePurchaseViewModel that = (ExchangePurchaseViewModel) o;
        return amount == that.amount &&
        		companyId == that.companyId &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(currentValue, that.currentValue) &&
                Objects.equals(purchaseValue, that.purchaseValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId,companyName, amount, currentValue, purchaseValue);
    }
}
