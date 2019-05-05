package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import csvConverter.ExchangeCsvModel;
import time.exchangeDate.ExchangeDate;

public class DateRepositorySQL {

	public static int createTableDateRepository(Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement ps = conn.prepareStatement("CREATE TABLE date_repository(" + 
				"id serial PRIMARY KEY,"+
				"assign_date date NOT NULL," + 
				"id_exchange BIGINT REFERENCES company(id) ON DELETE SET NULL," + 
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
	
	public static List<ExchangeCsvModel> getExchangesByDate(Date date) {
		List<ExchangeCsvModel> result = new ArrayList<>();
		List<Long> exchangesId = new ArrayList<>();
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT id_exhcange as id FROM date_repository "
					+ "WHERE assign_date = ? ");
			ps.setDate(1, DbFunctions.convertUtilToSql(date));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				exchangesId.add(rs.getLong("id"));
			}
			result = exchangesId.stream().map(e ->CompanySQL.getExchange(date,e)).collect(Collectors.toList());
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<ExchangeDate> getExchangeDate(){
		List<ExchangeDate> result = new ArrayList<>();
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT assign_date as datesql FROM date_repository "
					+ "GROUP BY assign_date ORDER BY assign_date");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.add(new ExchangeDate(rs.getDate("datesql")));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	 public static void updateTransactionAmount(int companyId, int amount, Date date,Connection conn) throws SQLException {
	    	PreparedStatement ps  = conn.prepareStatement(
					"UPDATE date_repository SET transaction_amount = ? WHERE id_exchange = ? AND assign_date = ?");
			ps.setInt(1, amount);
			ps.setLong(2, companyId);
			ps.setDate(3, DbFunctions.convertUtilToSql(date));
			ps.executeUpdate();
			ps.close();
	    }
	
}
