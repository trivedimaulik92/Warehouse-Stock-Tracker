package warehouseStockTracker.db.Old_Classes;
//
//
//package warehouseStockTracker.db;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Timestamp;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.time.LocalDateTime;
//import warehouseStockTracker.business.Stock;
//import warehouseStockTracker.business.Transaction;
//import warehouseStockTracker.business.TransactionRecord;
//
//public class TransactionDB{
//
//	// get all transactions
//	public static List<TransactionRecord> getAllRecords() throws DBException {
//		String sql = "Select * FROM Transaction ORDER BY TransactionID";
//		List<TransactionRecord> transactions = new ArrayList<>();
//		Connection connection = DBUtil.getConnection();
//		try(PreparedStatement ps = connection.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery()){
//			while (rs.next()){
//				int transactionId = rs.getInt("TransactionID");
//				int stockId = rs.getInt("StockID");
//				int vendorId = rs.getInt("VendorID");
//				String type = rs.getString("Type");
//				int quantity = rs.getInt("Quantity");
//				String datetime = rs.getTimestamp("Datetime").toString();
//				Stock stock = StockDB.getByStockId(stockId);
//				String productName = ProductDB.getByStock(stock).getName();
//				String vendorName = VendorDB.getByVendorID(vendorId).getName();
//
//				TransactionRecord t = new TransactionRecord();
//				t.setId(transactionId);
//				t.setStockId(stockId);
//				t.setVendorId(vendorId);
//				t.setType(type);
//				t.setQuantity(quantity);
//				t.setDatetime(datetime);
//				t.setProductName(productName);
//				t.setVendorName(vendorName);
//
//				transactions.add(t);
//			}
//			return transactions;
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// get all transactions
//	public static List<Transaction> getAll() throws DBException {
//		String sql = "Select * FROM Transaction ORDER BY TransactionID";
//		List<Transaction> transactions = new ArrayList<>();
//		Connection connection = DBUtil.getConnection();
//		try(PreparedStatement ps = connection.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery()){
//			while (rs.next()){
//				int transactionId = rs.getInt("TrasnactionID");
//				int stockId = rs.getInt("StockID");
//				int vendorId = rs.getInt("VendorID");
//				String type = rs.getString("Type");
//				int quantity = rs.getInt("Quantity");
//				String datetime = rs.getTimestamp("Datetime").toString();
//
//				Transaction t = new Transaction();
//				t.setId(transactionId);
//				t.setStockId(stockId);
//				t.setVendorId(vendorId);
//				t.setType(type);
//				t.setQuantity(quantity);
//				t.setDatetime(datetime);
//
//				transactions.add(t);
//			}
//			return transactions;
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// get transaction record by transactionId
//	public static Transaction getBYTransactionId(int transactionId) throws DBException {
//		String sql = "SELECT * FROM Transaction WHERE TransactionID = ? ";
//		Connection connection = DBUtil.getConnection();
//		Transaction t = new Transaction();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, transactionId);
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()){
//				int stockId = rs.getInt("StockID");
//				int vendorId = rs.getInt("VendorID");
//				int quantity = rs.getInt("Quantity");
//				String type = rs.getString("Type");
//				String datetime = rs.getTimestamp("Datetime").toString();
//				t.setId(transactionId);
//				t.setStockId(stockId);
//				t.setVendorId(vendorId);
//				t.setType(type);
//				t.setQuantity(quantity);
//				t.setDatetime(datetime);
//				return t;
//			}
//			else{
//				return null;
//			}
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// get transaction records by stockId
//	public static List<Transaction> getByStockId(int stockId) throws DBException {
//		String sql = "SELECT * FROM Transaction WHERE StockID = ?";
//		Connection connection = DBUtil.getConnection();
//		List<Transaction> transactions = new ArrayList<>();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, stockId);
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()){
//				int transactionId = rs.getInt("TransactionID");
//				int vendorId = rs.getInt("VendorID");
//				String type = rs.getString("Type");
//				int quantity = rs.getInt("Quantity");
//				String datetime = rs.getTimestamp("Datetime").toString();
//				Transaction t = new Transaction();
//				t.setId(transactionId);
//				t.setStockId(stockId);
//				t.setVendorId(vendorId);
//				t.setType(type);
//				t.setQuantity(quantity);
//				t.setDatetime(datetime);
//
//				transactions.add(t);
//			}
//			return transactions;
//		} 
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//
//	// get transactions record by Vendor Id
//	public static List<Transaction> getByVendorId(int vendorId) throws DBException {
//		String sql = "SELECT * FROM Transaction WHERE VendorID = ?";
//		Connection connection = DBUtil.getConnection();
//		List<Transaction> transactions = new ArrayList<>();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, vendorId);
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()){
//				int transactionId = rs.getInt("TransactionID");
//				int stockId = rs.getInt("StockID");
//				String type = rs.getString("Type");
//				int quantity = rs.getInt("Quantity");
//				String datetime = rs.getTimestamp("Datetime").toString();
//				Transaction t = new Transaction();
//				t.setId(transactionId);
//				t.setStockId(stockId);
//				t.setVendorId(vendorId);
//				t.setType(type);
//				t.setQuantity(quantity);
//				t.setDatetime(datetime);
//
//				transactions.add(t);
//			}
//			return transactions;
//		} 
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// get latest transaction record transctionId by StockId
//	public static int getLastRecord(int stockId) throws DBException {
//		String sql = "SELECT TransactionID as lastRecord " +
//				"FROM Transaction " +
//				"WHERE StockID = ? " +
//				"ORDER BY TransactionID DESC " +
//				"LIMIT 1";
//		Connection connection = DBUtil.getConnection();
//		int lastRecord = -1;
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, stockId);
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()){
//				lastRecord = rs.getInt("lastRecord");
//			}
//			return	lastRecord;		
//		}
//		catch (SQLException e) {
//			throw new DBException(e);
//		}
//	}
//
//	// add transaction record
//	public static void add(Transaction transaction) throws DBException {
//		String sql = "Insert INTO Transaction(StockId, VendorID, Type, Quantity, Datetime)"+
//				"VALUES(?, ?, ?, ?, ?)";
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, transaction.getStockId());
//			ps.setInt(2, transaction.getVendorId());
//			ps.setString(3,  transaction.getType());
//			ps.setInt(4, transaction.getQuantity());
//			ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
//			ps.executeUpdate();
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// update transaction record
//	public static void update(Transaction transaction) throws DBException {
//		String sql = "UPDATE Transaction SET " +
//				"StockId = ?, " +
//				"VendorID = ?, " +
//				"Type = ?, " +
//				"Quantity = ?,  " +
//				"Datetime = ? " +
//				"WHERE TransactionID = ? ";
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, transaction.getStockId());
//			ps.setInt(2, transaction.getVendorId());
//			ps.setString(3, transaction.getType());
//			ps.setInt(4, transaction.getQuantity());
//			ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
//			ps.setInt(6, transaction.getId());
//			ps.executeUpdate();
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// delete transaction record
//	public static void delete(Transaction transaction) throws DBException {
//		String sql = "DELETE FROM Transaction " +
//				"WHERE TransactionID = ? ";
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, transaction.getId());
//			ps.executeUpdate();
//		}
//		catch(SQLException e){
//			throw new DBException(e);
//		}
//	}
//}