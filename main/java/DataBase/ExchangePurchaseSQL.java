package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import buySale.view.ExchangeBuySaleViewModel;
import exchange.ExchangePurchase;
import time.timeManager.TimeManager;
import user.User;


public class ExchangePurchaseSQL {
	
	public static int createTableExchangePurchase(Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement ps = conn.prepareStatement("CREATE TABLE exchange_purchase(" + 
				"id serial PRIMARY KEY," + 
				"user_id BIGINT REFERENCES user_repository(id) ON DELETE SET NULL," + 
				"exchange_id BIGINT REFERENCES company(id) ON DELETE SET NULL," + 
				"purchase_price numeric NOT NULL," + 
				"amount BIGINT DEFAULT 1,"+
				"active boolean,"+
				"purchase_date date NOT NULL)");
		ps.executeUpdate();
		ps.close();
		 if(DbFunctions.isTableExist("exchange_purchase")) {
			 result =1;
		 }
		return result;
	}
		
	public static void setExchangePurchase(Long userId,  ExchangeBuySaleViewModel exchangeBuySaleViewModel, int amount, Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("INSERT INTO exchange_purchase (user_id, exchange_id, purchase_price, amount, active,purchase_date) VALUES (?,?,?,?,true,?)");
		ps.setLong(1,userId);
		ps.setLong(2, exchangeBuySaleViewModel.getId());
		ps.setDouble(3, exchangeBuySaleViewModel.getCurrentValueDouble());
		ps.setInt(4, amount);
		ps.setDate(5, DbFunctions.convertUtilToSql(TimeManager.getCurrentDate()));
		ps.executeUpdate();
	}
	
	public static void updateExchangePurchase(Long userId,  int exchangeId,int amount,Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("UPDATE exchange_purchase SET amount = ? WHERE user_id = ? AND exchange_id = ?");
		ps.setInt(1, amount);
		ps.setLong(2,userId);
		ps.setLong(3, exchangeId);
		ps.executeUpdate();
	}
	
	public static void updateExchangePurchaseByPurchaseId(Long purchaseId, int amount,Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("UPDATE exchange_purchase SET amount = ? WHERE id = ? ");
		ps.setInt(1, amount);
		ps.setLong(2,purchaseId);
		ps.executeUpdate();
	}
	
	public static void closeExchangePurchase(Long purchaseId, Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("UPDATE exchange_purchase SET active = false WHERE id = ? ");
		ps.setLong(1,purchaseId);
		ps.executeUpdate();
	}
	
	public static List<ExchangePurchase> getExchangePurchaseUser(Long userId){
		List<ExchangePurchase> result = new ArrayList<>();
		 try {
	            Connection conn = DbConnection.getConnection();
	            PreparedStatement ps = conn.prepareStatement("SELECT exchange_id as exchange_id,SUM(amount) as amount "
	            		+ "FROM exchange_purchase WHERE user_id = ? AND active = true GROUP BY exchange_id");
	            ps.setLong(1, userId);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                result.add(new ExchangePurchase(0l,userId, rs.getInt("exchange_id"),0d , rs.getInt("amount")));
	            }
	            ps.close();
	            rs.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return result;
	}
	
	public static List<ExchangePurchase> getExchangePurchaseUserDetail(Long userId, int companyId){
		List<ExchangePurchase> result = new ArrayList<>();
		 try {
	            Connection conn = DbConnection.getConnection();
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM exchange_purchase WHERE user_id = ? "
	            		+ "AND active = true AND exchange_id = ?");
	            ps.setLong(1, userId);
	            ps.setLong(2, companyId);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                result.add(new ExchangePurchase(rs.getLong("id"),userId, rs.getInt("exchange_id"), rs.getDouble("purchase_price"), rs.getInt("amount")));
	            }
	            ps.close();
	            rs.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return result;
	}

	public static Optional<Date> getFirsPurchaseDateUser(Long userId){
		Optional<Date> result =null;
		 try {
	            Connection conn = DbConnection.getConnection();
	            PreparedStatement ps = conn.prepareStatement("SELECT purchase_date FROM exchange_purchase WHERE user_id = ? ORDER BY purchase_date LIMIT 1");
	            ps.setLong(1, userId);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                result =  Optional.of(rs.getDate("purchase_date"));
	            } else {
	            	result = Optional.empty();
	            }
	            ps.close();
	            rs.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return result;
	}
	
	public static List<Double> getPurchasePrice(User user, int exchangeId){
		List<Double> result = new ArrayList<>();
		
		try {
            Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id,purchase_price FROM exchange_purchase WHERE user_id = ? AND exchange_id = ? ");
            ps.setLong(1, user.getId());
            ps.setInt(2, exchangeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	result.add(rs.getDouble("purchase_price"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return result;
	}

}
