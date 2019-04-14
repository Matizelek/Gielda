package user.account;

import exchange.ExchangePurchase;
import money.Money;

import java.util.ArrayList;
import java.util.List;

public class UserAccount {

    private int userId;

    public final Money accountBalance;

    private List<ExchangePurchase> exchangePurchaseList = new ArrayList<>();

    public UserAccount(int userId, Double accountBalance) {
        this.userId = userId;
        this.accountBalance = new Money(accountBalance);
    }

    public List<ExchangePurchase> getExchangePurchaseList() {
        return new ArrayList<>(exchangePurchaseList);
    }

    public void addExchangePurchase(ExchangePurchase exchangePurchase) {
        exchangePurchaseList.add(exchangePurchase);
    }
}
