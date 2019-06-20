package user.account;

import exchange.ExchangePurchase;
import money.Money;

import java.util.ArrayList;
import java.util.List;

import DataBase.ExchangePurchaseSQL;

public class UserAccount {

    private Long userId;

    public final Money accountBalance;

    private List<ExchangePurchase> exchangePurchaseList = new ArrayList<>();

    public UserAccount(Long userId, Double accountBalance) {
        this.userId = userId;
        this.accountBalance = new Money(accountBalance);
    }

    public List<ExchangePurchase> getExchangePurchaseList() {
        return ExchangePurchaseSQL.getExchangePurchaseUser(userId);
    }
    
    public List<ExchangePurchase> getExchangePurchaseListDetail(int companyId) {
        return ExchangePurchaseSQL.getExchangePurchaseUserDetail(userId, companyId);
    }

    public void addExchangePurchase(ExchangePurchase exchangePurchase) {
        exchangePurchaseList.add(exchangePurchase);
    }
    
    public String getAccountBalance() {
    	return accountBalance.toString();
    }
    
    public Double getAccountBalanceDouble() {
    	return Double.parseDouble(accountBalance.toString());
    }
    
    public Long getUserId() {
    	return userId;
    }
}
