package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public static void updateUserAccount(Long userId, Double accountBalance, Connection conn) throws SQLException {
		PreparedStatement ps  = conn.prepareStatement(
				"UPDATE user_account SET account_balance = ? WHERE user_id = ? ");
		ps.setDouble(1, accountBalance);
		ps.setLong(2, userId);
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
				result = new UserAccount(rs.getLong("user_id"), rs.getDouble("account_balance"));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<UserAccount> getUserAccounts() {
		List<UserAccount> result = new ArrayList<>();
		Connection conn;
		try {
			conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM user_account ");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.add(new UserAccount(rs.getLong("user_id"), rs.getDouble("account_balance")));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
