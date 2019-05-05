package exchange;

import money.Money;
import time.timeOfDay.TimeOfDay;

import java.util.Random;

public class Exchange {

    private final int companyId;
    private final String companyName;
    private final String isin;
    private final double openingPrice;
    private final double maxPrice;
    private final double minPrice;
    private final double closingPrice;
    private final int amount;

    public Exchange(int companyId, String companyName, String isin, double openingPrice, double maxPrice, double minPrice, double closingPrice, int amount) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.isin = isin;
        this.openingPrice = openingPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.closingPrice = closingPrice;
        this.amount = amount;
    }


    public Money getCurrentPrice(TimeOfDay timeOfDay) {

        if (timeOfDay == TimeOfDay.FIRST_Q) {
            return new Money(getOpeningPrice());
        }

        if (timeOfDay == TimeOfDay.FORTH_Q) {
            return new Money(getClosingPrice());
        }

        boolean takeMin = new Random().nextBoolean();
        if (takeMin) return new Money(getMinPrice());
        else return new Money(getMaxPrice());

    }

    public ExchangeSale createExchangeSale(TimeOfDay timeOfDay) {
        Money price = getCurrentPrice(timeOfDay);
        return new ExchangeSale(getCompanyName(), price);
    }


    public int getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
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
    
	public int getAmount() {
		return amount;
	}

	public String getIsin() {
		return isin;
	}
    
	
}
