package DataBase;

import csvConverter.ExchangeCsvModel;
import exchange.Exchange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompanySQL {


    public static int createTableCompany(Connection conn) throws SQLException {
        int result = 0;
        PreparedStatement ps = conn.prepareStatement("CREATE TABLE company(" +
                "id serial PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "isin TEXT);");
        ps.executeUpdate();
        ps.close();
        if (DbFunctions.isTableExist("company")) {
            result = 1;
        }
        return result;
    }

    public static void setExchange(java.util.Date date, ExchangeCsvModel exchange, Connection conn) throws SQLException {
        int id = getCompanyId(exchange, conn);

        boolean companyExists = id > 0;

        if (companyExists) {
            saveExchange(date, exchange, conn, id);
        } else {
            int newCompanyId = saveCompany(exchange, conn);
            saveExchange(date, exchange, conn, newCompanyId);
        }
    }

    private static int saveCompany(ExchangeCsvModel exchange, Connection conn) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        ps = conn.prepareStatement("INSERT INTO company (name,isin) VALUES (?,?) RETURNING id ",Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, exchange.getName());
        ps.setString(2, exchange.getIsin());
        ps.executeUpdate();
        rs = ps.getGeneratedKeys();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("id");
        }
        ps.close();
        rs.close();
        return id;
    }

    private static void saveExchange(java.util.Date date, ExchangeCsvModel exchange, Connection conn, int id) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        ps = conn.prepareStatement("SELECT id FROM date_repository WHERE id_exchange = ? AND assign_date = ?");
        ps.setInt(1, id);
        ps.setDate(2, DbFunctions.convertUtilToSql(date));
        rs = ps.executeQuery();
        if (!rs.next()) {
            ps = conn.prepareStatement(
                    "INSERT INTO date_repository (assign_date,id_exchange,opening_price,max_price,min_price,"
                            + "closing_price,currency,change,volume,transaction_amount,trade) VALUES (?,?,?,?,?,?,?,?,?,?,?) ");
            ps.setDate(1, DbFunctions.convertUtilToSql(date));
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

    private static int getCompanyId(ExchangeCsvModel exchange, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT id FROM company WHERE name = ?");
        ps.setString(1, exchange.getName());
        ResultSet rs = ps.executeQuery();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }

    public static ExchangeCsvModel getExchange(Date date, Long id) {
        ExchangeCsvModel result = null;
        try {
            Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT e.*,dr.* FROM company e "
                    + "JOIN date_repository dr ON dr.id_exchange = e.id " + "WHERE assign_date = ? AND e.id = ?");
            ps.setDate(1, DbFunctions.convertUtilToSql(date));
            ps.setLong(2, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = new ExchangeCsvModel(rs.getString("name"), rs.getString("isin"), rs.getString("currency"),
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
    
    public static List<Exchange> getExchanges(java.util.Date date) {
        List<Exchange> result = new ArrayList<>();
        try {
            Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT c.id as company_id,c.*,dr.* FROM company c "
                    + "JOIN date_repository dr ON dr.id_exchange = c.id " + "WHERE assign_date = ?"
                    		+ "ORDER BY c.id");
            ps.setDate(1, DbFunctions.convertUtilToSql(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Exchange(rs.getInt("company_id"),rs.getString("name"),rs.getString("isin")
                		,rs.getDouble("opening_price"),rs.getDouble("max_price"), rs.getDouble("min_price")
                		,rs.getDouble("closing_price"),rs.getInt("transaction_amount")));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
