package statistic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import DataBase.StatisticSQL;
import user.User;

public class StatisticManager {

	
	public static void addStatistic(User user, Double accountBalance, Date date,Connection conn) throws SQLException {

		if(isStatisticArlreadyForUserOnDate(user,date)) {
			updateStatistic(user, accountBalance,date, conn);
		}else {
			createStatistic(user, accountBalance,date, conn);
		}
	}
	
	private static boolean isStatisticArlreadyForUserOnDate(User user,Date date) {
		boolean result = false;
		Statistic stat = StatisticSQL.getUserStatisticByDate(user.getId(), date);
		if(stat != null) {
			result = true;
		}
		return result;
	}
	
	private static void updateStatistic(User user, Double accountBalance, Date date, Connection conn) throws SQLException {
			StatisticSQL.updateStatistic(user.getId(), accountBalance, date, conn);
	}
	
	private static void createStatistic(User user, Double accountBalance, Date date,Connection conn) throws SQLException {
			StatisticSQL.setStatistic(user.getId(), accountBalance, date, conn);

	}
}
