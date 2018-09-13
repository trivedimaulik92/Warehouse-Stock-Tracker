

package warehouseStockTracker.db.Old_Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtilOld{
	
	// declaring connection variable
	private static Connection connection;
	
	private DBUtilOld(){}
	
	public static synchronized Connection getConnection() {
		if(connection != null){
			return connection;
		}
		else {
			try {
				// set the db url, username, and password
				String url = "jdbc:mysql://localhost:3306/wst";
				String username = "mma_user";
				String password = "CSCI_504";
				
				// get and return connection
				connection = DriverManager.getConnection(url, username, password);
				return connection;
			} catch(SQLException e) {
				System.out.println(e);
				return null;
			}
		}
	}
	
	public static synchronized void closeConnection() throws SQLException {
		if (connection != null) {
			try {
				connection.close();
				
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				connection = null;
			}
		}
	}
	
}
