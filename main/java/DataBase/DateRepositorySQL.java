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
import exchangePreview.view.ExchangePreviewChartViewModel;
import exchangePreview.view.ExchangePreviewViewModel;
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
	 
	public static List<ExchangePreviewViewModel> getSortedExchanges(Date date1, Date date2, String sortedMethod) {
		List<ExchangePreviewViewModel> result = new ArrayList<>();
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(" Select c.id,c.name, c.isin,d1.transaction_amount,"
					+ "d1.closing_price, d1.opening_price, d1.min_price, d1.max_price,"
					+ " d1.closing_price- d2.opening_price as difference" + " FROM company c"
					+ "	 JOIN date_repository d1 ON d1.id_exchange = c.id"
					+ "	 JOIN date_repository d2 ON d2.id_exchange = c.id "
					+ " WHERE d2.assign_date = ? AND d1.assign_date = ?" + " AND d1.transaction_amount > 0 "
					+ sortedMethod);
			ps.setDate(1, DbFunctions.convertUtilToSql(date2));
			ps.setDate(2, DbFunctions.convertUtilToSql(date1));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.add(new ExchangePreviewViewModel(rs.getInt("id"), rs.getString("name"), rs.getString("isin"),
						rs.getInt("transaction_amount"), rs.getString("difference"),
						rs.getString("opening_price") + " z³", rs.getString("max_price") + " z³",
						rs.getString("min_price") + " z³", rs.getString("closing_price") + " z³"));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static int getCoutDaysFromStart(Date date) {
		int result = 0;
		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn
					.prepareStatement(" Select assign_date FROM date_repository  WHERE assign_date <= ? ORDER BY id ");
			ps.setDate(1, DbFunctions.convertUtilToSql(date));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result++;
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	 
	public static ExchangePreviewChartViewModel getExchangePreviewChartViewModel(int id, Date date) {
		ExchangePreviewChartViewModel result = null;
		int resultId = 0;
		String resultName = "";
		List<Date> resultDays = new ArrayList<>();
		double[] resultPrices = new double[getCoutDaysFromStart(date)];;
		int i = 0;

		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn
					.prepareStatement(" Select c.id,c.name, c.isin,d.closing_price, d.assign_date FROM company c"
							+ "	 JOIN date_repository d ON d.id_exchange = c.id WHERE c.id = ? AND d.assign_date <= ? "
							+ " AND assign_date::date > date_trunc('day', ?::date - interval '1 month')"
							+ "ORDER BY d.id ");
			ps.setInt(1, id);
			ps.setDate(2, DbFunctions.convertUtilToSql(date));
			ps.setDate(3, DbFunctions.convertUtilToSql(date));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				resultId = rs.getInt("id");
				resultName = rs.getString("name");
				resultDays.add(rs.getDate("assign_date"));
				resultPrices[i++] =  rs.getDouble("closing_price");
			}
			result = new ExchangePreviewChartViewModel(resultId, resultName, resultDays, resultPrices);
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	 
	 
}
