package intro.view;

import java.util.Objects;

public class ExchangePurchaseViewModel {
    private final String companyName;
    private final int amount;
    private final String currentValue;
    private final String purchaseValue;

    public ExchangePurchaseViewModel(String companyName, int amount, String currentValue, String purchaseValue) {

        this.companyName = companyName;
        this.amount = amount;
        this.currentValue = currentValue;
        this.purchaseValue = purchaseValue;
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
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(currentValue, that.currentValue) &&
                Objects.equals(purchaseValue, that.purchaseValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, amount, currentValue, purchaseValue);
    }
}
