package user.account;

import exchange.ExchangePurchase;
import money.Money;

import java.util.ArrayList;
import java.util.List;

public class UserAccount {

    private int userId;

    private Money accountBalance;

    private List<ExchangePurchase> exchangePurchaseList = new ArrayList<>();
    
    public UserAccount(int userId, Double accountBalance) {
    	this.userId = userId;
    	this.accountBalance = new Money(accountBalance);
    }

}
