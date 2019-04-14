package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exchange.ExchangePurchase;


public class ExchangePurchaseSQL {
	
	public static int createTableExchangePurchase(Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement ps = conn.prepareStatement("CREATE TABLE exchange_purchase(" + 
				"id serial PRIMARY KEY," + 
				"user_id BIGINT REFERENCES user_repository(id) ON DELETE SET NULL," + 
				"exchange_id BIGINT REFERENCES exchange(id) ON DELETE SET NULL," + 
				"purchuase_price numeric NOT NULL," + 
				"amount BIGINT DEFAULT 1);");
		ps.executeUpdate();
		ps.close();
		 if(DbFunctions.isTableExist("exchange_purchase")) {
			 result =1;
		 }
		return result;
	}
		
	public static void setExchangePurchase(int userId,  int exchangeId, Double purchasePrice, int amount, Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("INSERT INTO exchange_purchase (user_id, exchange_id, purchase_price, amount) VALUE (?,?,?,?)");
		ps.setLong(1,userId);
		ps.setLong(2, exchangeId);
		ps.setDouble(3, purchasePrice);
		ps.setInt(4, amount);
		ps.executeUpdate();
	}
	
	public static ExchangePurchase getExchangePurchaseByUserId(int userId) {
		ExchangePurchase result = null;
		try {
		Connection conn = DbConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM exchange_purchase WHERE user_id =  ?");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			result = new ExchangePurchase(rs.getInt("user_id"), rs.getInt("exchange_id"), rs.getDouble("purchuase_price"), rs.getInt("amount"));
		}
		rs.close();
		ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
