package buySale.view;


public class ExchangeBuySaleViewModel {

	private final int id;
	private final String name;
	private final String isin;
    private final int amount;
    private final String currentValue;
    private final int userAmount;
	
	public ExchangeBuySaleViewModel(int id,String name, String isin,int amount, String currentValue,  int userAmount) {
		this.id= id;
		this.name = name;
		this.isin = isin;
		this.amount = amount;
		this.currentValue = currentValue.replaceAll(" z³", "");
		this.userAmount = userAmount;
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

	public int getUserAmount() {
		return userAmount;
	}

	public Double getCurrentValueDouble() {
		return Double.parseDouble(currentValue);
	}

	public String getIsin() {
		return isin;
	}

	
}
