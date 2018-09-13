

package warehouseStockTracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtilNew{

	// declaring connection variable
	private static Connection connection;

	// restrict constructor
	private DBUtilNew(){}


	// private method to get and close connection
	static synchronized Connection getConnection() {
		if(connection != null){
			return connection;
		}
		else {
			try {
				// set the db url, username, and password
				String url = "jdbc:mysql://localhost:3306/wst";
				//String username = "mma_user";
				//String password = "CSCI_504";
				
				String username = "root";
				String password = "Sunnyday_92";

				// get and return connection
				connection = DriverManager.getConnection(url, username, password);
				return connection;
			} catch(SQLException e) {
				System.out.println(e);
				return null;
			}
		}
	}

	static synchronized void closeConnection() throws SQLException {
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




	/* Implemented in separate class - ProductHandler */
//	// method to get complex business objects from table join
//	public static List<ProductStockItem> getAll(ProductTable productTable,
//			StockTable stockTable) throws DBException, SQLException{
//		if(productTable == null || stockTable == null){
//			throw new DBException();
//		}
//		else{
//			List<ProductStockItem> productStockItems = new ArrayList<>();
//			Table table1 = productTable;
//			Table table2 = stockTable;
//			ArrayList<Integer> table1Columns = table1.getColumnIdArrayList();
//			ArrayList<Integer> table2Columns = table2.getColumnIdArrayList();
//			String sql = "SELECT ";
//			for(Integer i: table1Columns){
//				sql += table1.getTableColumnName(i) + ", ";
//			}
//			sql += " \n";
//			for(Integer i: table2Columns){
//				sql += table2.getTableColumnName(i) +", ";
//			}
//			sql = sql.substring(0,sql.length()-2);
//			sql += "\nFROM " + table1 + " " +
//					"\nLEFT JOIN " + table2 + "ON  " + table1.getTableColumnName(0) +
//					" = " + table2.getTableColumnName(1);
//			ResultSet rs = DBUtilNew.SelectQ(sql);
//			while(rs.next()){
//				ProductStockItem p = new ProductStockItem();
//				p.setId(rs.getInt(table1.getTableColumnName(0)));
//				p.setName(rs.getString(table1.getTableColumnName(1)));
//				p.setType(ProductType.valueOf(table1.getTableColumnName(2)));
//				p.setBrand(rs.getString(table1.getTableColumnName(3)));
//				p.setModel(rs.getString(table1.getTableColumnName(4)));
//				p.setAveragePrice(rs.getDouble(table1.getTableColumnName(5)));
//				p.setStatus(rs.getBoolean(table1.getTableColumnName(6)));
//				p.setStockId(rs.getInt(table2.getTableColumnName(0)));
//				p.setQuantity(rs.getInt(table2.getTableColumnName(2)));
//				p.setStockCap(rs.getInt(table2.getTableColumnName(3)));
//
//				productStockItems.add(p);
//			}
//			connection.close();
//			return productStockItems;
//		}
//	}

	/* Implemented in separate class - TransactionHandler */
//	public static List<TransactionRecord> get(TransactionTable transactionTable,
//			StockTable stockTable, ProductTable productTable, VendorTable vendorTable) throws
//			DBException, SQLException {
//		if(transactionTable == null || stockTable == null || productTable == null ||
//				vendorTable == null){
//			throw new DBException();
//		}
//		else{
//			ArrayList<TransactionRecord> records = new ArrayList<>();
//			Table table1 = transactionTable;
//			Table table2 = stockTable;
//			Table table3 = productTable;
//			Table table4 = vendorTable;
//			ArrayList<Integer> table1Columns = table1.getColumnIdArrayList();
//			
//			String sql = "SELECT ";
//			for(Integer i: table1Columns){
//				sql += table1.getTableColumnName(i) + ", ";
//			}
//			sql += "\n";
//			// No need to select all columns for table2, 3, 4
////			for(Integer i: table2Columns){
////				sql += table2.getTableColumnName(i) + ", ";
////			}
////			sql += "\n";
////			for(Integer i: table3Columns){
////				sql += table3.getTableColumnName(i) + ", ";
////			}
////			sql = sql.substring(0, sql.length()-2);
//			sql += "\n "+ table3.getTableColumnName(1) +", ";
//			sql += table4.getTableColumnName(1) + " ";
//			sql += "\nFROM (((" + table1.getName() +" " +
//					"\nJOIN " + table2.getName() + " ON " + table1.getTableColumnName(1) +
//													" = " + table2.getTableColumnName(0) + ") " +
//					"\nJOIN " + table3.getName() + " ON " + table2.getTableColumnName(1) +
//													" = " + table3.getTableColumnName(0) + ") " +
//					"\nJOIN " + table4.getName() + " ON " + table1.getTableColumnName(2) +
//													" = " + table4.getTableColumnName(0) + ") ";
//			ResultSet rs = DBUtilNew.SelectQ(sql);
//			while(rs.next()){
//				TransactionRecord r = new TransactionRecord();
//				r.setId(rs.getInt(table1.getTableColumnName(0)));
//				r.setStockId(rs.getInt(table1.getTableColumnName(1)));
//				r.setVendorId(rs.getInt(table1.getTableColumnName(2)));
//				r.setType(TransactionType.valueOf(rs.getString(table1.getTableColumnName(3))));
//				r.setQuantity(rs.getInt(table1.getTableColumnName(4)));
//				r.setDatetime(rs.getTimestamp(table1.getTableColumnName(5)));
//				r.setProductName(rs.getString(table3.getTableColumnName(1)));
//				r.setVendorName(rs.getString(table4.getTableColumnName(1)));
//				
//				records.add(r);
//			}
//			connection.close();
//			return records;
//		}
//	}

	/* Implemented in separate class - QueryTool */
//	// private method to run select query  
//	static ResultSet SelectQ(String sql){
//		ResultSet rs = null;
//		Connection connection = getConnection();
//		try {
//			PreparedStatement query = connection.prepareStatement(sql);
//			rs = query.executeQuery();
//			return rs;
//		} catch (SQLException e) {
//			System.out.println(e);
//			return null;
//		}
//	}
//
//	// public method to run queries in batch
//	public static void BatchQ(List<String> queries){
//		Connection connection = getConnection();
//		PreparedStatement ps = null;
//		try{
//			for(String s: queries){
//				ps = connection.prepareStatement(s);
//				ps.addBatch();
//			}
//			ps.executeBatch();
//		}
//		catch(SQLException e){
//			System.out.println(e);
//		}
//	}
	
	/* Implementation not required for current version of application */
//	// method to get plain business object list from table - Listed private since not to be used
//	@SuppressWarnings("unused")
//	private static List<Object> getAll(Table table) throws DBException, SQLException {
//		if(table == null ){
//			throw new DBException();
//		}
//		else{
//			if(table instanceof ProductTable ){
//				List<Object> products = new ArrayList<>();
//				ResultSet rs = SelectQ(table.getSelectQ());
//				while(rs.next()){
//					Product p = new Product();
//					p.setId(rs.getInt(1));
//					p.setName(rs.getString(2));
//					p.setType(ProductType.valueOf(rs.getString(3)));
//					p.setBrand(rs.getString(4));
//					p.setModel(rs.getString(5));
//					p.setAveragePrice(rs.getDouble(6));
//					p.setStatus(rs.getBoolean(7));
//
//					products.add(p);
//				}
//				return products;
//			} 
//		}
//		connection.close();
//		return null;
//	}




}