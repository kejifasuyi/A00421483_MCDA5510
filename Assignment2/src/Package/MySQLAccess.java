package Package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLAccess {

	public Connection setupConnection() throws Exception {

		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager
					.getConnection("jdbc:mysql://localhost/assignment2?" + "user=root&password=1730" + "&useSSL=false"
							+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");

		} catch (SQLException se) {
			Logger.getLogger("Main").log(Level.WARNING, se.getLocalizedMessage().toString());
		} catch (Exception e) {
			Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());
			throw e;
		} finally {

		}
		return connection;
	}	

}
