package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import user.account.UserAccount;


public class UserAccountSQL {

	
	public static int createTableUserAccount(Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement ps = conn.prepareStatement("CREATE TABLE user_account(" + 
				"id serial PRIMARY KEY," + 
				"user_id BIGINT REFERENCES user_repository(id) ON DELETE SET NULL," + 
				"account_balance numeric);");
		ps.executeUpdate();
		ps.close();
		 if(DbFunctions.isTableExist("user_account")) {
			 result =1;
		 }
		return result;
	}
	
	public static void setUserAccount(Long userId, Double accountBalance, Connection conn) throws SQLException {
		PreparedStatement ps  = conn.prepareStatement(
				"INSERT INTO user_account (user_id,account_balance) VALUES (?,?) ");
		ps.setLong(1, userId);
		ps.setDouble(2, accountBalance);
		ps.executeUpdate();
		ps.close();
		
	}
	
	public static UserAccount getUserAccount(Long userId) {
		UserAccount result = null;
		Connection conn;
		try {
			conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM user_account WHERE user_id = ? ");
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = new UserAccount(rs.getInt("user_id"), rs.getDouble("account_balance"));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
