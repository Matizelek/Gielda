package usersPreview.view;

import org.junit.platform.commons.util.StringUtils;

import money.Money;

public class UsersPreviewViewModel {

	private final String username;
	private final String purchaseDateOfFirsExchange;
	private final Money money;

	public UsersPreviewViewModel(String username, String purchaseDateOfFirsExchange, Money money) {
		this.username = username;
		this.purchaseDateOfFirsExchange = purchaseDateOfFirsExchange;
		this.money = money;
	}

	public String getUsername() {
		return username;
	}

	public String getPurchaseDateOfFirsExchange() {
		return purchaseDateOfFirsExchange;
	}

	public Money getMoney() {
		return money;
	}
	
	public String getMoneyString() {
		String sumString = money.toString("z³");
    	if(StringUtils.isBlank(sumString)) {
    		sumString = " - ";
    	}
		return sumString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result + ((purchaseDateOfFirsExchange == null) ? 0 : purchaseDateOfFirsExchange.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersPreviewViewModel other = (UsersPreviewViewModel) obj;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (purchaseDateOfFirsExchange == null) {
			if (other.purchaseDateOfFirsExchange != null)
				return false;
		} else if (!purchaseDateOfFirsExchange.equals(other.purchaseDateOfFirsExchange))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
