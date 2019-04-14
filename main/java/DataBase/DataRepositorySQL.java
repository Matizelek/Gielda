package DataBase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import exchange.Exchange;
import time.DateUtils;

public class DataRepositorySQL {

	public static int createTableDateRepository(Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement ps = conn.prepareStatement("CREATE TABLE date_repository(" + 
				"assign_date date NOT NULL," + 
				"id_exchange BIGINT REFERENCES exchange(id) ON DELETE SET NULL," + 
				"opening_price numeric NOT NULL," + 
				"max_price numeric NOT NULL," + 
				"min_price numeric NOT NULL," + 
				"closing_price numeric NOT NULL," + 
				"currency TEXT," + 
				"change numeric," + 
				"volume numeric," + 
				"transaction_amount numeric," + 
				"trade numeric);");
		ps.executeUpdate();
		ps.close();
		 if(DbFunctions.isTableExist("date_repository")) {
			 result =1;
		 }
		return result;
	}
	
	public static List<Exchange> getExchangesByDate(String data) {
		List<Exchange> result = new ArrayList<>();
		List<Long> exchangesId = new ArrayList<>();
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT id_exhcange as id FROM date_repository "
					+ "WHERE assign_date = ? ");
			ps.setDate(1, (Date) DateUtils.toDefaultDate(data));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				exchangesId.add(rs.getLong("id"));
			}
			result = exchangesId.stream().map(e ->ExchangeSQL.getExchange(data,e)).collect(Collectors.toList());
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
