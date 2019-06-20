package exchangePreview.view;

import java.util.Objects;
import java.util.Random;

import time.timeOfDay.TimeOfDay;

public class ExchangePreviewViewModel {
	
	private final int id;
	private final String name;
	private final String isin;
    private final int amount;
    private String currentValue;
    private final String difference;
    private String openPrice;
    private String maxPrice;
    private String minPrice;
    private String closePrice;

	public ExchangePreviewViewModel(int id,String name, String isin,int amount, String currentValue,String difference ) {
		this.id= id;
		this.name = name;
		this.isin = isin;
		this.amount = amount;
		this.currentValue = currentValue;
		this.difference = difference;
	}
	
	public ExchangePreviewViewModel(int id,String name, String isin,int amount, String difference, String openPrice, 
			String maxPrice, String minPrice, String closePrice) {
		this.id= id;
		this.name = name;
		this.isin = isin;
		this.amount = amount;
		this.difference = difference;
		this.openPrice = openPrice;
		this.maxPrice = maxPrice;
		this.minPrice = minPrice;
		this.closePrice = closePrice;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAmount() {
		return amount;
	}

	public String getCurrentValue() {
		return currentValue;
	}
	
	public String getIsin() {
		return isin;
	}

	public String getDifference() {
		return difference;
	}

	public String getOpenPrice() {
		return openPrice;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public String getClosePrice() {
		return closePrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id,name, amount, currentValue, isin);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		ExchangePreviewViewModel that = (ExchangePreviewViewModel) obj;
		return Objects.equals(amount,that.amount) &&
				Objects.equals(currentValue, that.currentValue) &&
				Objects.equals(id, that.id) &&
				Objects.equals(isin, that.isin) &&
				Objects.equals(name, that.name);

	}
	
	public String getCurrentPrice(TimeOfDay timeOfDay) {

        if (timeOfDay == TimeOfDay.FIRST_Q) {
            return openPrice;
        }

        if (timeOfDay == TimeOfDay.FORTH_Q) {
            return closePrice;
        }

        boolean takeMin = new Random().nextBoolean();
        if (takeMin) return minPrice;
        else return maxPrice;

    }
}
