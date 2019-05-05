package buySale.model;

import java.sql.Connection;
import java.sql.SQLException;

import DataBase.DateRepositorySQL;
import DataBase.DbConnection;
import DataBase.ExchangePurchaseSQL;
import DataBase.UserAccountSQL;
import buySale.view.ExchangeBuySaleViewModel;
import time.timeManager.TimeManager;
import user.User;
import user.account.UserAccount;

public class BuySaleModel {

	public BuySaleModel() {
		
	}
	
	public void buy(ExchangeBuySaleViewModel exchangeBuySaleViewModel, User user,int exchangeAmount,UserAccount userAccount){
		Connection conn= null;
		try {
			conn = DbConnection.getConnectionAndStartTransaction();
			ExchangePurchaseSQL.setExchangePurchase(user.getId(), exchangeBuySaleViewModel.getId(),exchangeBuySaleViewModel.getCurrentValueDouble(),exchangeAmount, conn);
			UserAccountSQL.updateUserAccount(user.getId(),( userAccount.getAccountBalanceDouble() - (exchangeBuySaleViewModel.getCurrentValueDouble()*exchangeAmount)), conn);
			DateRepositorySQL.updateTransactionAmount(exchangeBuySaleViewModel.getId(), exchangeBuySaleViewModel.getAmount()-exchangeAmount, TimeManager.getCurrentDate(), conn);
			DbConnection.commitTransactionAndCloseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DbConnection.closeConnectionAndRollBackTransaction(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void buyUpdate(ExchangeBuySaleViewModel exchangeBuySaleViewModel, User user,int exchangeAmount,UserAccount userAccount, int userAmount){
		Connection conn= null;
		try {
			conn = DbConnection.getConnectionAndStartTransaction();
			ExchangePurchaseSQL.updateExchangePurchase(user.getId(), exchangeBuySaleViewModel.getId(),exchangeAmount+userAmount, conn);
			UserAccountSQL.updateUserAccount(user.getId(),( userAccount.getAccountBalanceDouble() - (exchangeBuySaleViewModel.getCurrentValueDouble()*exchangeAmount)), conn);
			DateRepositorySQL.updateTransactionAmount(exchangeBuySaleViewModel.getId(), exchangeBuySaleViewModel.getAmount()-exchangeAmount, TimeManager.getCurrentDate(), conn);
			DbConnection.commitTransactionAndCloseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DbConnection.closeConnectionAndRollBackTransaction(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void sale(ExchangeBuySaleViewModel exchangeBuySaleViewModel,User user,int exchangeAmount,UserAccount userAccount) {
		Connection conn= null;
		try {
			conn = DbConnection.getConnectionAndStartTransaction();
			ExchangePurchaseSQL.closeExchangePurchase(user.getId(), exchangeBuySaleViewModel.getId(),conn);
			UserAccountSQL.updateUserAccount(user.getId(),( userAccount.getAccountBalanceDouble()+(exchangeBuySaleViewModel.getCurrentValueDouble()*exchangeAmount)), conn);
			DateRepositorySQL.updateTransactionAmount(exchangeBuySaleViewModel.getId(), exchangeBuySaleViewModel.getAmount()+exchangeAmount, TimeManager.getCurrentDate(), conn);
			DbConnection.commitTransactionAndCloseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DbConnection.closeConnectionAndRollBackTransaction(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
