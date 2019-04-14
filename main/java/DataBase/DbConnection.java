package DataBase;

import java.sql.Connection;
import java.sql.SQLException;

public class DbConnection {
	
	private static Connection conn = null;
	
	public static Connection getConnection() throws SQLException {
		if(conn == null || conn.isClosed()) {
			conn = createNewConnection();
		}
		return conn;
	}
	
	private static Connection createNewConnection() throws SQLException {
		Connection conn = DbConnectionFactory.createConnection();
		conn.setAutoCommit(true);
		return conn;
	}
	
	public static Connection getConnectionAndStartTransaction() throws SQLException {
		Connection conn = createNewConnection();
		conn.setAutoCommit(false);
		return conn;
	}
	
	public static void commitTransactionAndCloseConnection(Connection conn) throws SQLException {
		if(conn != null) {
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
		}
	}
	
	public static void closeConnectionAndRollBackTransaction(Connection conn) throws SQLException {
		if(conn != null) {
			conn.rollback();
			conn.setAutoCommit(true);
			if(!conn.isClosed()) {
				conn.close();
			}
		}
	}
	
}
