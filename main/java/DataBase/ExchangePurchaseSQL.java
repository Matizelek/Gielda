package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exchange.Exchange;
import exchange.ExchangePurchase;


public class ExchangePurchaseSQL {
	
	public static int createTableExchangePurchase(Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement ps = conn.prepareStatement("CREATE TABLE exchange_purchase(" + 
				"id serial PRIMARY KEY," + 
				"user_id BIGINT REFERENCES user_repository(id) ON DELETE SET NULL," + 
				"exchange_id BIGINT REFERENCES company(id) ON DELETE SET NULL," + 
				"purchase_price numeric NOT NULL," + 
				"amount BIGINT DEFAULT 1,"+
				"active boolean);");
		ps.executeUpdate();
		ps.close();
		 if(DbFunctions.isTableExist("exchange_purchase")) {
			 result =1;
		 }
		return result;
	}
		
	public static void setExchangePurchase(Long userId,  int exchangeId, Double purchasePrice, int amount, Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("INSERT INTO exchange_purchase (user_id, exchange_id, purchase_price, amount, active) VALUES (?,?,?,?,true)");
		ps.setLong(1,userId);
		ps.setLong(2, exchangeId);
		ps.setDouble(3, purchasePrice);
		ps.setInt(4, amount);
		ps.executeUpdate();
	}
	
	public static void updateExchangePurchase(Long userId,  int exchangeId,int amount,Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("UPDATE exchange_purchase SET amount = ? WHERE user_id = ? AND exchange_id = ?");
		ps.setInt(1, amount);
		ps.setLong(2,userId);
		ps.setLong(3, exchangeId);
		ps.executeUpdate();
	}
	
	public static void closeExchangePurchase(Long userId,  int exchangeId,Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("UPDATE exchange_purchase SET active = false WHERE user_id = ? AND exchange_id = ?");
		ps.setLong(1,userId);
		ps.setLong(2, exchangeId);
		ps.executeUpdate();
	}
	
	public static List<ExchangePurchase> getExchangePurchaseUser(Long userId){
		List<ExchangePurchase> result = new ArrayList<>();
		 try {
	            Connection conn = DbConnection.getConnection();
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM exchange_purchase WHERE user_id = ? AND active = true");
	            ps.setLong(1, userId);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                result.add(new ExchangePurchase(userId, rs.getInt("exchange_id"), rs.getDouble("purchase_price"), rs.getInt("amount")));
	            }
	            ps.close();
	            rs.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return result;
	}


}
