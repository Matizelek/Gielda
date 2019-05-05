package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TimeManagerSQL {

	public static int createTableTimeManager(Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement ps = conn.prepareStatement("CREATE TABLE time_manager(" + 
				"last_simulated_date date," + 
				"last_real_date date );");
		ps.executeUpdate();
		ps.close();
		 if(DbFunctions.isTableExist("time_manager")) {
			 result =1;
		 }
		return result;
	}
	
	public static void setTimeManager(Date lastSimulatedDate, Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("INSERT INTO time_manager (last_simulated_date, last_real_date) VALUES (?, now())");
		ps.setDate(1, DbFunctions.convertUtilToSql(lastSimulatedDate));
		ps.executeUpdate();
	}
	
	public static Date getLastSimulatedDate() {
		Date result = null;
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM time_manager");
			ResultSet rs= ps.executeQuery();
			if (rs.next()) {
				result = rs.getDate("last_simulated_date");
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Date getLastRealDate() {
		Date result = null;
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM time_manager");
			ResultSet rs= ps.executeQuery();
			if (rs.next()) {
				result = rs.getDate("last_real_date");
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
