package buySale.view;


public class ExchangeBuySaleViewModel {

	private final int id;
	private final String name;
	private final String isin;
    private final int amount;
    private final String currentValue;
    private final int userAmount;
    private final String userValue;
	
	public ExchangeBuySaleViewModel(int id,String name, String isin,int amount, String currentValue,  int userAmount, String userValue) {
		this.id= id;
		this.name = name;
		this.isin = isin;
		this.amount = amount;
		this.currentValue = currentValue.replaceAll(" z³", "");
		this.userAmount = userAmount;
		this.userValue = userValue.replaceAll(" z³", "");
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

	public String getUserValue() {
		return userValue;
	}

	public Double getCurrentValueDouble() {
		return Double.parseDouble(currentValue);
	}

	public String getIsin() {
		return isin;
	}

	
}
