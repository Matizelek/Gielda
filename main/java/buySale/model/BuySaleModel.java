package buySale.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DataBase.DateRepositorySQL;
import DataBase.DbConnection;
import DataBase.ExchangePurchaseSQL;
import DataBase.UserAccountSQL;
import buySale.view.ExchangeBuySaleViewModel;
import exchange.ExchangePurchase;
import money.Money;
import time.timeManager.TimeManager;
import user.User;
import user.account.UserAccount;

public class BuySaleModel {

	public BuySaleModel() {
		
	}
	
	public ExchangeBuySaleViewModel buy(ExchangeBuySaleViewModel exchangeBuySaleViewModel, User user,int exchangeAmount,UserAccount userAccount){
		Connection conn= null;
		try {
			conn = DbConnection.getConnectionAndStartTransaction();
			ExchangePurchaseSQL.setExchangePurchase(user.getId(), exchangeBuySaleViewModel,exchangeAmount, conn);
			UserAccountSQL.updateUserAccount(user.getId(),( userAccount.getAccountBalanceDouble() - (exchangeBuySaleViewModel.getCurrentValueDouble()*exchangeAmount)), conn);
			DateRepositorySQL.updateTransactionAmount(exchangeBuySaleViewModel.getId(), exchangeBuySaleViewModel.getAmount()-exchangeAmount, TimeManager.getCurrentDate(), conn);
			DbConnection.commitTransactionAndCloseConnection(conn);
			
			exchangeBuySaleViewModel = new ExchangeBuySaleViewModel(exchangeBuySaleViewModel.getId(),exchangeBuySaleViewModel.getName(), 
					exchangeBuySaleViewModel.getIsin(),exchangeBuySaleViewModel.getAmount()-exchangeAmount,exchangeBuySaleViewModel.getCurrentValue(),
					exchangeBuySaleViewModel.getUserAmount()+exchangeAmount);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DbConnection.closeConnectionAndRollBackTransaction(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return exchangeBuySaleViewModel;
	}
	
	public ExchangeBuySaleViewModel buyUpdate(ExchangeBuySaleViewModel exchangeBuySaleViewModel, User user,int exchangeAmount,UserAccount userAccount, int userAmount){
		Connection conn= null;
		try {
			conn = DbConnection.getConnectionAndStartTransaction();
			buyInDataBase(user,exchangeBuySaleViewModel, exchangeAmount,conn);
			UserAccountSQL.updateUserAccount(user.getId(),( userAccount.getAccountBalanceDouble() - (exchangeBuySaleViewModel.getCurrentValueDouble()*exchangeAmount)), conn);
			DateRepositorySQL.updateTransactionAmount(exchangeBuySaleViewModel.getId(), exchangeBuySaleViewModel.getAmount()-exchangeAmount, TimeManager.getCurrentDate(), conn);
			DbConnection.commitTransactionAndCloseConnection(conn);
			
			exchangeBuySaleViewModel = new ExchangeBuySaleViewModel(exchangeBuySaleViewModel.getId(),exchangeBuySaleViewModel.getName(), exchangeBuySaleViewModel.getIsin(),
					exchangeBuySaleViewModel.getAmount()-exchangeAmount,exchangeBuySaleViewModel.getCurrentValue(), exchangeBuySaleViewModel.getUserAmount()+exchangeAmount);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DbConnection.closeConnectionAndRollBackTransaction(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return exchangeBuySaleViewModel;
	}
	
	public ExchangeBuySaleViewModel sale(ExchangeBuySaleViewModel exchangeBuySaleViewModel,User user,int exchangeAmount,UserAccount userAccount) {
		Connection conn= null;
		try {
			conn = DbConnection.getConnectionAndStartTransaction();
			saleInDataBase(exchangeBuySaleViewModel,user,exchangeAmount,userAccount,conn);
			UserAccountSQL.updateUserAccount(user.getId(),( userAccount.getAccountBalanceDouble()+(exchangeBuySaleViewModel.getCurrentValueDouble()*exchangeAmount)), conn);
			DateRepositorySQL.updateTransactionAmount(exchangeBuySaleViewModel.getId(), exchangeBuySaleViewModel.getAmount()+exchangeAmount, TimeManager.getCurrentDate(), conn);
			DbConnection.commitTransactionAndCloseConnection(conn);
			exchangeBuySaleViewModel = new ExchangeBuySaleViewModel(exchangeBuySaleViewModel.getId(),exchangeBuySaleViewModel.getName(), exchangeBuySaleViewModel.getIsin(),
					exchangeBuySaleViewModel.getAmount()+exchangeAmount,exchangeBuySaleViewModel.getCurrentValue(), exchangeBuySaleViewModel.getUserAmount()-exchangeAmount);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DbConnection.closeConnectionAndRollBackTransaction(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return exchangeBuySaleViewModel;
	}
	
	private void saleInDataBase(ExchangeBuySaleViewModel exchangeBuySaleViewModel,User user,int exchangeAmount, UserAccount userAccount,Connection conn) throws SQLException {
		List<ExchangePurchase> exchangePurchaseList = userAccount.getExchangePurchaseListDetail(exchangeBuySaleViewModel.getId());
		for(ExchangePurchase exchangePurchase : exchangePurchaseList) {
			if(exchangeAmount>=exchangePurchase.getAmount()) {
				ExchangePurchaseSQL.closeExchangePurchase(exchangePurchase.getPurchaseId(),conn);
				exchangeAmount-=exchangePurchase.getAmount();
			}else {
				int newAmount = exchangePurchase.getAmount()-exchangeAmount;
				ExchangePurchaseSQL.updateExchangePurchaseByPurchaseId(exchangePurchase.getPurchaseId(),newAmount, conn);
				exchangeAmount-=exchangePurchase.getAmount();
			}
			if(exchangeAmount<=0) break;
		}
	}
	
	private void buyInDataBase(User user,ExchangeBuySaleViewModel exchangeBuySaleViewModel, int exchangeAmount,Connection conn) throws SQLException {
		List<ExchangePurchase> exchangePurchases = ExchangePurchaseSQL.getExchangePurchaseUserDetail(user.getId(), exchangeBuySaleViewModel.getId());
		for(ExchangePurchase exchangePurchase : exchangePurchases) {
			if(new Money(exchangeBuySaleViewModel.getCurrentValueDouble()).equals(exchangePurchase.getPurchasePrice())) {
				exchangeAmount+=exchangePurchase.getAmount();
				ExchangePurchaseSQL.updateExchangePurchaseByPurchaseId(exchangePurchase.getPurchaseId(),exchangeAmount, conn);
			}
		}
	}
	
}
