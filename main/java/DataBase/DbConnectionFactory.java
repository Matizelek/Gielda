package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionFactory {

	private static String host = "localhost:5432";
	private static String dbName = "postgres";
	private static String dbUserName = "postgres";
	private static String dbPassword = "postgres";
	private static String driver= "org.postgresql.Driver";

	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://"+host+"/"+dbName,dbUserName,dbPassword);
	}
	
	public static Connection createConnection () throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName(driver);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = getConnection();
		return conn;
	}
}
