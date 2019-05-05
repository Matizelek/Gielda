package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
				result = new User(rs.getString("hashed_password"), rs.getString("user_name"), rs.getLong("id"));
			}
			
		ps.close();
		rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Long setUser(User user, Connection conn) throws SQLException {
		Long id = 0l;
		 ResultSet rs;
		PreparedStatement ps = conn
				.prepareStatement("INSERT INTO user_repository (user_name, hashed_password) VALUES (?,?) RETURNING id ",Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getHashedPassword());
		ps.executeUpdate();

		 rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	            id = rs.getLong("id");
	        }
		
		ps.close();
		return id;
	}
	
	public static List<User> getUsersRepository(){
		List<User> result = new ArrayList<>();
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM user_repository");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				result.add(new User(rs.getString("hashed_password"), rs.getString("user_name"), rs.getLong("id")));
			}
			
		ps.close();
		rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
