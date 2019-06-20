package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import statistic.Statistic;
import statisticPreview.view.StatisticPreviewChartViewModel;

public class StatisticSQL {

	public static int createStatisticTable(Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement ps = conn.prepareStatement("CREATE TABLE statistic(" + 
				"id serial PRIMARY KEY,"
				+ "assaing_date date," + 
				"user_id bigint,"
				+ "account_balance bigint );");
		ps.executeUpdate();
		ps.close();
		return result;
	}
	
	
	public static void setStatistic(Long userId, Double accountBalance, Date date,Connection conn) throws SQLException {
		PreparedStatement ps  = conn.prepareStatement(
				"INSERT INTO statistic (user_id,account_balance,assaing_date) VALUES (?,?,?) ");
		ps.setLong(1, userId);
		ps.setDouble(2, accountBalance);
		ps.setDate(3, DbFunctions.convertUtilToSql(date));
		ps.executeUpdate();
		ps.close();
		
	}
	
	public static void updateStatistic(Long userId, Double accountBalance, Date date, Connection conn) throws SQLException {
		PreparedStatement ps  = conn.prepareStatement(
				"UPDATE statistic SET account_balance = ? WHERE user_id = ? AND assaing_date = ?");
		ps.setDouble(1, accountBalance);
		ps.setLong(2, userId);
		ps.setDate(3, DbFunctions.convertUtilToSql(date));
		ps.executeUpdate();
		ps.close();
		
	}
	
	public static List<Statistic> getUserStatistics(Long userId) {
		List<Statistic> result = new ArrayList<>();
		Connection conn;
		try {
			conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM statistic WHERE user_id = ?");
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.add(new Statistic(rs.getLong("id"),rs.getDate("assaing_date"),rs.getLong("user_id"), rs.getDouble("account_balance")));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Statistic getUserStatisticByDate(Long userId, Date date) {
		Statistic result = null;
		Connection conn;
		try {
			conn = DbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM statistic WHERE user_id = ? AND assaing_date = ?");
			ps.setLong(1, userId);
			ps.setDate(2, DbFunctions.convertUtilToSql(date));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = new Statistic(rs.getLong("id"),rs.getDate("assaing_date"),rs.getLong("user_id"), rs.getDouble("account_balance"));
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static StatisticPreviewChartViewModel getStatisticPreviewChartViewModel(Long id, Date date, int months) {
		StatisticPreviewChartViewModel result = null;
		int resultId = 0;
		Long resultUserId = 0l;
		List<Date> resultDays = new ArrayList<>();
		double[] resultAccountBalance = new double[getCoutDaysFromStart(date)];;
		int i = 0;

		try {
			Connection conn = DbConnection.getConnection();
			PreparedStatement ps = conn
					.prepareStatement(" SELECT * FROM statistic WHERE user_id = ? "
							+ "AND assaing_date::date > date_trunc('day', ?::date - interval '"+months+" month') "
							+ " ORDER BY id,assaing_date ");
			ps.setLong(1, id);
			ps.setDate(2, DbFunctions.convertUtilToSql(date));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				resultId = rs.getInt("id");
				resultUserId = rs.getLong("user_id");
				resultDays.add(rs.getDate("assaing_date"));
				resultAccountBalance[i++] =  rs.getDouble("account_balance");
			}
			result = new StatisticPreviewChartViewModel(resultId, resultUserId, resultDays, resultAccountBalance);
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
	
}
