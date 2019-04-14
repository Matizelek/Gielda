package DataBase;


import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class DbConnectionTest {


    @Test
    public void should_get_connnection_return_true() {
        Connection conn = null;
        Boolean result = false;
        try {
            conn = DbConnection.getConnection();
            result = conn.getAutoCommit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void should_get_connnection_and_start_transaction_return_false() {
        Connection conn = null;
        Boolean result = true;
        try {
            conn = DbConnection.getConnectionAndStartTransaction();
            result = conn.getAutoCommit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void should_commit_tranaction_and_close_connection_true() {
        Connection conn = null;
        Boolean result = false;
        try {
            conn = DbConnection.getConnectionAndStartTransaction();
            DbConnection.commitTransactionAndCloseConnection(conn);
            result = conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void should_close_connection_and_rollback_transaction_true() {
        Connection conn = null;
        Boolean result = false;
        try {
            conn = DbConnection.getConnectionAndStartTransaction();
            DbConnection.closeConnectionAndRollBackTransaction(conn);
            result = conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void should_close_connection_and_rollback_transaction_false() {
        Connection conn = null;
        Boolean result = true;
        try {
            conn = DbConnection.getConnectionAndStartTransaction();
            result = conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertThat(result).isEqualTo(false);
    }

}
