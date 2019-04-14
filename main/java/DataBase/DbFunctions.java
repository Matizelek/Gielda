package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DbFunctions {

	public static void getNazwe() {
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("Select * from test");
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				System.err.println(rs.getString("nazwa"));
			}
			
			ps.close();
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isTableExist(String tableName) {
		boolean result = false;
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM information_schema.tables WHERE table_name = ?");
			ps.setString(1, tableName);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				result = true;
			}
			
		ps.close();
		rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
