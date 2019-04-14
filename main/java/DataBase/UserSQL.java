package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import user.User;

public class UserSQL {

	public static int createTableUser(Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement ps = conn.prepareStatement("CREATE TABLE user_repository (" + 
				"id serial PRIMARY KEY," + 
				"user_name TEXT NOT NULL," + 
				"hashed_password TEXT NOT NULL)");
		ps.executeUpdate();
		ps.close();
		 if(DbFunctions.isTableExist("user_repository")) {
			 result =1;
		 }
		return result;
	}
	
	public static User getUser(String userName) {
		User result = null;
		
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM user_repository WHERE user_name = ?");
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				result = new User(rs.getString("hashed_password"), rs.getString("user_name"), rs.getInt("id"));
			}
			
		ps.close();
		rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void setUser(String userName, String hashedPassword, Connection conn) throws SQLException {
		PreparedStatement ps = conn
				.prepareStatement("INSERT INTO user_repository (used_name, hashed_password) VALUES (?,?)");
		ps.setString(1, userName);
		ps.setString(2, hashedPassword);
		ps.executeUpdate();

		ps.close();
	}
}
