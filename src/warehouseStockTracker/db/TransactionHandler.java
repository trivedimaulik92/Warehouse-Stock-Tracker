
package warehouseStockTracker.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import warehouseStockTracker.Business.InvalidStockOpException;
import warehouseStockTracker.Business.ProductStockItem;
import warehouseStockTracker.Business.Stock;
import warehouseStockTracker.Business.StockInterface;
import warehouseStockTracker.Business.Transaction;
import warehouseStockTracker.Business.TransactionRecord;
import warehouseStockTracker.Business.TransactionType;
import warehouseStockTracker.Business.Vendor;

public class TransactionHandler extends QueryTool {

	private static ProductTable productTable;
	private static StockTable stockTable;
	private static TransactionTable transactionTable;
	private static VendorTable vendorTable;

	// restrict constructor	
	protected TransactionHandler(String productTableName, String stockTableName,
			String transactionTableName, String vendorTableName){
		productTable = new ProductTable(productTableName);
		stockTable = new StockTable(stockTableName);
		transactionTable = new TransactionTable(transactionTableName);
		vendorTable = new VendorTable(vendorTableName);
	}

	protected TransactionHandler(){
		this("Product", "Stock", "Transaction", "Vendor");
	}

	public static TransactionHandler getInstance(String productTableName, String stockTableName, 
			String transactionTableName, String vendorTableName){
		TransactionHandler transactionHandler = 
				new TransactionHandler(productTableName, stockTableName,
						transactionTableName, vendorTableName);
		return transactionHandler;
	}

	public static TransactionHandler getInstance() {
		TransactionHandler transactionHandler = 
				new TransactionHandler("Product", "Stock", "Transaction", "Vendor");
		return transactionHandler;
	}

	// public method to get all ProductStockItems
	public ArrayList<TransactionRecord> getAll() throws DBException {
		String tableColumnName = null;
		Object value = null;
		try {
			ArrayList<TransactionRecord> records = (ArrayList<TransactionRecord>) 
					get(tableColumnName, value);
			return records;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	// public method to get ProductStockItem with condition
	public ArrayList<TransactionRecord> getByStockId(int stockId)
			throws DBException {
		String tableColumnName = stockTable.getTableColumnName(0);
		//		Object value = new Integer(productId);
		try {
			ArrayList<TransactionRecord> records = (ArrayList<TransactionRecord>) 
					get(tableColumnName, new Integer(stockId));
			return records;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	public ArrayList<TransactionRecord> getByVendorId(int vendorId)
			throws DBException {
		String tableColumnName = vendorTable.getTableColumnName(0);
		//		Object value = new Integer(productId);
		try {
			ArrayList<TransactionRecord> records = (ArrayList<TransactionRecord>) 
					get(tableColumnName, new Integer(vendorId));
			return records;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	// methods for stock operation
	// Stock In method
	public boolean stockIn(StockInterface stock, Vendor vendor, int quantity) 
											throws DBException, InvalidStockOpException {
		boolean status = false;
		if(stock != null){
			if (stock instanceof ProductStockItem){
				stock = ((ProductStockItem) stock).getStock();
			}
				stock.addStock(quantity);
			Transaction transaction = new Transaction();
			transaction.setStockId(((Stock) stock).getId());
			transaction.setVendorId(vendor.getId());
			transaction.setType(TransactionType.valueOf("stock_in"));
			transaction.setQuantity(quantity);
			//			TransactionDB.add(transaction); -Old Code
			//			StockDB.update(stock);	-Old Code
			ArrayList<String> queries = new ArrayList<>();
			queries.add(transactionTable.getInsertQ(transaction));
			queries.add(stockTable.getUpdateQ((Stock) stock));
			batchQ(queries);
			
			status = true;
		}
		return status;
	}

	// Stock out method
	public boolean stockOut(StockInterface stock, Vendor vendor, int quantity) 
												throws DBException, InvalidStockOpException {
		boolean status = false;
		if(stock != null){
			if (stock instanceof ProductStockItem){
				stock = ((ProductStockItem) stock).getStock();
			}
				stock.removeStock(quantity);
			Transaction transaction = new Transaction();
			transaction.setStockId(((Stock) stock).getId());
			transaction.setVendorId(vendor.getId());
			transaction.setType(TransactionType.valueOf("stock_out"));
			transaction.setQuantity(quantity);
			//			TransactionDB.add(transaction); -Old Code
			//			StockDB.update(stock);	-Old Code
			ArrayList<String> queries = new ArrayList<>();
			queries.add(transactionTable.getInsertQ(transaction));
			queries.add(stockTable.getUpdateQ((Stock) stock));
			batchQ(queries);
			status = true;
		}
		return status;
	}

	// Stock take method
	public boolean stockTake(StockInterface stock, int quantity) 
								throws DBException, InvalidStockOpException {
		boolean status = false;
			if (stock instanceof ProductStockItem){
				stock = ((ProductStockItem) stock).getStock();
			}
			stock.removeStock(quantity);
		Transaction transaction = new Transaction();
		transaction.setStockId(((Stock) stock).getId());
		transaction.setQuantity(quantity);
		transaction.setType(TransactionType.valueOf("stock_take"));
		transaction.setVendorId(1);
		//		TransactionDB.add(transaction); -Old Code
		//		StockDB.update(stock); -Old Code
		ArrayList<String> queries = new ArrayList<>();
		queries.add(transactionTable.getInsertQ(transaction));
		queries.add(stockTable.getUpdateQ((Stock) stock));
		batchQ(queries);
		status = true;
		return status;
	}

	// revert method
	public boolean revert(Transaction transaction) 
											throws DBException, InvalidStockOpException {
		boolean status = false;
		if(transaction != null) {
			//			int lastRecordId = TransactionDB.getLastRecord(transaction.getStockId()); - Old Code
			int lastRecordId = transactionTable.getLastRecordId(transaction.getStockId());
			if(lastRecordId == transaction.getId()){
				//				Stock stock = StockDB.getByStockId(transaction.getStockId()); - Old Code
				Stock stock = getStock(transaction.getStockId());
				Transaction revertTransaction = new Transaction();
					switch (transaction.getType()){
					case stock_in:
						stock.removeStock(transaction.getQuantity());
						revertTransaction.setType(TransactionType.valueOf("revert_stock_in"));
						break;
					case stock_out:
						stock.addStock(transaction.getQuantity());
						revertTransaction.setType(TransactionType.valueOf("revert_stock_out"));
						break;
					case stock_take:
						stock.addStock(transaction.getQuantity());
						revertTransaction.setType(TransactionType.valueOf("revert_stock_take"));
						break;
					default:
						break;
					}
				revertTransaction.setStockId(transaction.getStockId());
				revertTransaction.setQuantity(transaction.getQuantity());
				revertTransaction.setVendorId(transaction.getVendorId());
				//				StockDB.update(stock); -Old Code
				//				TransactionDB.add(revertTransaction); -Old Code
				ArrayList<String> queries = new ArrayList<>();
				queries.add(transactionTable.getInsertQ(revertTransaction));
				queries.add(stockTable.getUpdateQ(stock));
				batchQ(queries);
				status = true;
			}
		}
		return status;
	}

	// get stock by stockId
	private Stock getStock(int stockId) throws DBException {
		ArrayList<Integer> columnList = stockTable.getColumnIdArrayList();
		ResultSet rs = selectQ(stockTable.getSelectQ(columnList, 0, new Integer(stockId)));
		try {
			if(rs.next()){
				Stock stock = new Stock();
				stock.setId(rs.getInt(1));
				stock.setProductId(rs.getInt(2));
				stock.setQuantity(rs.getInt(3));
				stock.setCapacity(rs.getInt(4));
				return stock;
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		return null;
	}

	// get TransactionRecords from table join
	private List<TransactionRecord> get(String tableColumnName, Object value) throws
	DBException, SQLException {
		ArrayList<TransactionRecord> records = new ArrayList<>();
		Table table1 = transactionTable;
		Table table2 = stockTable;
		Table table3 = productTable;
		Table table4 = vendorTable;
		ArrayList<Integer> table1Columns = table1.getColumnIdArrayList();

		String sql = "SELECT ";
		for(Integer i: table1Columns){
			sql += table1.getTableColumnName(i) + ", ";
		}
		sql += "\n";
		// No need to select all columns for table2, 3, 4
		//			for(Integer i: table2Columns){
		//				sql += table2.getTableColumnName(i) + ", ";
		//			}
		//			sql += "\n";
		//			for(Integer i: table3Columns){
		//				sql += table3.getTableColumnName(i) + ", ";
		//			}
		//			sql = sql.substring(0, sql.length()-2);
		sql += "\n "+ table3.getTableColumnName(1) +", ";
		sql += table4.getTableColumnName(1) + " ";
		sql += "\nFROM (((" + table1.getName() +" " +
				"\nJOIN " + table2.getName() + " ON " + table1.getTableColumnName(1) +
				" = " + table2.getTableColumnName(0) + ") " +
				"\nJOIN " + table3.getName() + " ON " + table2.getTableColumnName(1) +
				" = " + table3.getTableColumnName(0) + ") " +
				"\nJOIN " + table4.getName() + " ON " + table1.getTableColumnName(2) +
				" = " + table4.getTableColumnName(0) + ") ";
		if(tableColumnName != null && value != null && !tableColumnName.isEmpty()){
			ArrayList<Table> tables = new ArrayList<>();
			tables.add(table1);
			tables.add(table2);
			tables.add(table3);
			tables.add(table4);
			sql += getCondition( tables, tableColumnName, value);
			sql += "\nORDER BY " + table1.getTableColumnName(0)+ " ";
		}
		ResultSet rs = QueryTool.selectQ(sql);
		while(rs.next()){
			TransactionRecord r = new TransactionRecord();
			r.setId(rs.getInt(table1.getTableColumnName(0)));
			r.setStockId(rs.getInt(table1.getTableColumnName(1)));
			r.setVendorId(rs.getInt(table1.getTableColumnName(2)));
			r.setType(TransactionType.valueOf(rs.getString(table1.getTableColumnName(3))));
			r.setQuantity(rs.getInt(table1.getTableColumnName(4)));
			r.setDatetime(rs.getTimestamp(table1.getTableColumnName(5)));
			r.setProductName(rs.getString(table3.getTableColumnName(1)));
			r.setVendorName(rs.getString(table4.getTableColumnName(1)));

			records.add(r);
		}
		DBUtilNew.closeConnection();
		Collections.sort(records, TransactionRecord.compareById());
		return records;
	}
	
	// proxy methods of other tables
	public int getLastRecord(int stockId) throws DBException {
		return transactionTable.getLastRecordId(stockId);
	}
	
	public Vendor getVendorByName(String vendorName) throws DBException {
		return vendorTable.getByVendorName(vendorName);
	}
}