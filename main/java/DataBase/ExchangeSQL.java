package DataBase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exchange.Exchange;
import time.DateUtils;

public class ExchangeSQL {

	
	public static int createTableExchange(Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement ps = conn.prepareStatement("CREATE TABLE exchange(" + 
				"id serial PRIMARY KEY," + 
				"name TEXT NOT NULL," + 
				"isin TEXT);");
		ps.executeUpdate();
		ps.close();
		 if(DbFunctions.isTableExist("exchange")) {
			 result =1;
		 }
		return result;
	}
	
	@SuppressWarnings("resource")
	public static void setExchange(String data, Exchange exchange, Connection conn) throws SQLException {
		int id = 0;
		PreparedStatement ps = conn.prepareStatement("SELECT id FROM exchange WHERE name = ?");
		ps.setString(1, exchange.getName());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			id = rs.getInt("id");
		}
		if (id > 0) {
			ps = conn.prepareStatement("SELECT id FROM date_repository WHERE id_exchange = ? AND assign_date = ?");
			ps.setInt(1, id);
			ps.setString(2, data);
			rs = ps.executeQuery();
			if (!rs.next()) {
				ps = conn.prepareStatement(
						"INSERT INTO date_repository (assign_date,id_exchange,opening_price,max_price,min_price,"
								+ "closing_price,currency,change,volume,transaction_amount,trade) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ");
				ps.setDate(1, (Date) DateUtils.toDefaultDate(data));
				ps.setLong(2, id);
				ps.setDouble(3, exchange.getOpeningPrice());
				ps.setDouble(4, exchange.getMaxPrice());
				ps.setDouble(5, exchange.getMinPrice());
				ps.setDouble(6, exchange.getClosingPrice());
				ps.setString(7, exchange.getCurrency());
				ps.setDouble(8, exchange.getChange());
				ps.setDouble(9, exchange.getVolume());
				ps.setInt(10, exchange.getTransactionAmount());
				ps.setDouble(11, exchange.getTrade());
				ps.executeUpdate();
			}
		} else {
			ps = conn.prepareStatement("INSERT INTO exchange (name,isin) VALUES (?,?) RETURNING id ");
			ps.setString(1, exchange.getName());
			ps.setString(2, exchange.getIsin());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt("id");
			}
			ps = conn.prepareStatement(
					"INSERT INTO date_repository (assign_date,id_exchange,opening_price,max_price,min_price,"
							+ "closing_price,currency,change,volume,transaction_amount,trade) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ");
			ps.setDate(1, (Date) DateUtils.toDefaultDate(data));
			ps.setLong(2, id);
			ps.setDouble(3, exchange.getOpeningPrice());
			ps.setDouble(4, exchange.getMaxPrice());
			ps.setDouble(5, exchange.getMinPrice());
			ps.setDouble(6, exchange.getClosingPrice());
			ps.setString(7, exchange.getCurrency());
			ps.setDouble(8, exchange.getChange());
			ps.setDouble(9, exchange.getVolume());
			ps.setInt(10, exchange.getTransactionAmount());
			ps.setDouble(11, exchange.getTrade());
			ps.executeUpdate();
		}
		ps.close();
		rs.close();
	}
	
	public static Exchange getExchange(String data, Long id) {
		Exchange result = null;
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT e.*,dr.* FROM exchange e "
					+ "JOIN date_repository dr ON dr.id_exchange = e.id " + "WHERE assign_date = ? AND e.id = ?");
			ps.setDate(1, (Date) DateUtils.toDefaultDate(data));
			ps.setLong(2, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = new Exchange(rs.getString("name"), rs.getString("isin"), rs.getString("currency"),
						rs.getDouble("opening_price"), rs.getDouble("max_price"), rs.getDouble("min_price"),
						rs.getDouble("closing_price"), rs.getDouble("change"), rs.getDouble("volume"),
						rs.getInt("transaction_amount"), rs.getDouble("trade"));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
