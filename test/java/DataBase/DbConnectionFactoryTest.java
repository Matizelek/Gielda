package DataBase;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.Test;

public class DbConnectionFactoryTest {
	
	@Test
	@Ignore
	public void should_not_null() {
		Connection conn = null;
		try {
			conn = DbConnectionFactory.createConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertThat(conn).isNotNull();
	}

}
