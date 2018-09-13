
package warehouseStockTracker.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import warehouseStockTracker.Business.ProductStockItem;
import warehouseStockTracker.Business.ProductType;
import warehouseStockTracker.Business.Stock;
import warehouseStockTracker.Business.Transaction;
import warehouseStockTracker.Business.TransactionRecord;

public class ProductHandler extends QueryTool {

	private static ProductTable productTable;
	private static StockTable stockTable;
	private static TransactionTable transactionTable;

	// restrict contructor
	protected ProductHandler(String productTableName, String stockTableName,
								String transactionTableName){
		productTable = new ProductTable(productTableName);
		stockTable = new StockTable(stockTableName);
		transactionTable = new TransactionTable(transactionTableName);
	}

	protected ProductHandler(){
		this("Product", "Stock", "Transaction");
	}

	public static ProductHandler getInstance(String productTableName, String stockTableName, 
										String transactionTableName){
		ProductHandler productHandler = new ProductHandler(productTableName, stockTableName,
															transactionTableName);
		return productHandler;
	}

	public static ProductHandler getInstance() {
		ProductHandler productHandler = new ProductHandler("Product", "Stock", "Transaction");
		return productHandler;
	}

	// public method to get all ProductStockItems
	public  ArrayList<ProductStockItem> getAll() throws DBException {
		String tableColumnName = null;
		Object value = null;
		try {
			ArrayList<ProductStockItem> products = (ArrayList<ProductStockItem>) 
					get(tableColumnName, value);
			return products;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	// public method to get ProductStockItem with condition
	public ProductStockItem getByProductId(int productId) throws DBException {
		String tableColumnName = productTable.getTableColumnName(0);
		//		Object value = new Integer(productId);
		try {
			ArrayList<ProductStockItem> products = (ArrayList<ProductStockItem>) 
					get(tableColumnName, new Integer(productId));
			if(products.size() == 1){
				return products.get(0);
			}
			else{
				throw new DBException();
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	public ProductStockItem getByStockId(int stockId) throws DBException {
		String tableColumnName = stockTable.getTableColumnName(0);
		//		Object value = new Integer(stockId);
		try {
			ArrayList<ProductStockItem> products = (ArrayList<ProductStockItem>) 
					get(tableColumnName, new Integer(stockId));
			if(products.size() == 1){
				return products.get(0);
			}
			else{
				throw new DBException();
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	public ProductStockItem getByProductName(String productName) throws DBException {
		String tableColumnName = productTable.getTableColumnName(1);
		//		Object value = new Integer(stockId);
		try {
			ArrayList<ProductStockItem> products = (ArrayList<ProductStockItem>) 
					get(tableColumnName, productName);
			if(products.size() == 1){
				return products.get(0);
			}
			else{
				throw new DBException();
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	// create product method - add product and respective stock entry depending on capacity
	public ProductStockItem createProduct(ProductStockItem productStockItem) throws DBException {
		if(productStockItem != null){
			ArrayList<String> queryArray = new ArrayList<>();
			
			//			Old Code - productStockItem.setId(ProductDB.generateProductId(productStockItem.getType()));
			int productId = productTable.generateProductId(productStockItem.getType());
			productStockItem.setId(productId);
			//			Old Code - ProductDB.add(productStockItem);
			queryArray.add(productTable.getInertQ(productStockItem));
			batchQ(queryArray);
			if (productStockItem.getCapacity() > 0){
				Stock stock = new Stock();
				stock.setProductId(productStockItem.getId());
				stock.setCapacity(productStockItem.getCapacity());
				stock.setQuantity(0);
				productStockItem.setStatus(true);
				//		Old Code - ProductDB.update(productStockItem);
				//				StockDB.add(stock);	
				queryArray = new ArrayList<String>();
				queryArray.add(stockTable.getInsertQ(stock));
				queryArray.add(productTable.getUpdateQ(productStockItem));
				batchQ(queryArray); // create product and stock entry with batch query processing
				//		Old Code - stock = StockDB.getByProductId(productStockItem.getId());
				productStockItem = getByProductId(productId);
			}
		}
		return productStockItem;
	}


		// update product info
		public ProductStockItem updateProduct(ProductStockItem productStockItem) throws DBException, CloneNotSupportedException {	
			ProductStockItem p = new ProductStockItem();
			if(productStockItem != null){
				ArrayList<String> queryArray = new ArrayList<>();
				p = (ProductStockItem)productStockItem.clone();
				if(!p.isStatus() && p.getCapacity() > 0){
					p.setStatus(true);
//					ProductDB.update(p); -Old Code
					queryArray.add(productTable.getUpdateQ(p));
//					batchQ(queryArray);
					Stock stock = new Stock();
					stock.setProductId(p.getId());
					stock.setQuantity(0);
					stock.setCapacity(p.getCapacity());
//					StockDB.add(stock); -Old Code
					queryArray.add(stockTable.getInsertQ(stock));
					batchQ(queryArray);
//					stock = StockDB.getByProductId(p.getId()); -Old Code
					p = getByProductId(p.getId());
				}
				else if(p.isStatus() && p.getCapacity() > 0){
//					Stock stock = StockDB.getByProductId(p.getId()); -Old Code - Redundant
					Stock stock = new Stock();
					stock.setId(p.getStockId());
					stock.setProductId(p.getId());
					stock.setQuantity(p.getQuantity());
					stock.setCapacity(p.getCapacity());
//					StockDB.update(stock); -Old Code
					queryArray.add(productTable.getUpdateQ(p));
					queryArray.add(stockTable.getUpdateQ(stock));
					batchQ(queryArray);
				}
				else if(p.isStatus() &&  p.getCapacity() <= 0){
					p.setStatus(false);
//					ProductDB.update(p); -Old Code
					queryArray.add(productTable.getUpdateQ(p));
//					Stock stock = StockDB.getByProductId(p.getId()); -Old Code
//					StockDB.delete(stock); -Old Code
					Stock stock = new Stock();
					stock.setId(p.getStockId());
					stock.setProductId(p.getId());
					stock.setQuantity(p.getQuantity());
					queryArray.add(stockTable.getDeleteQ(stock));
					batchQ(queryArray);
					p.setStockId(0);
					p.setStockCap(0);
					p.setQuantity(0);
				}
				else if(!p.isStatus() && p.getCapacity() <= 0){
					p.setStatus(false);
//					ProductDB.update(p); -Old Code
					queryArray.add(productTable.getUpdateQ(p));
					batchQ(queryArray);
				}
			}
			return p;
		}
	//

		// delete product
		public boolean deleteProduct(ProductStockItem productStockItem) throws DBException {
			boolean check = false;
			if(productStockItem != null){
//				Stock stock = StockDB.getByProductId(product.getId());
				Stock stock = new Stock();
				stock.setId(productStockItem.getStockId());
				stock.setProductId(productStockItem.getId());
				stock.setQuantity(productStockItem.getQuantity());
				stock.setCapacity(productStockItem.getCapacity());
				ArrayList<String> queries = new ArrayList<>();
				if(stock != null){
					List<TransactionRecord> transactions = TransactionHandler.getInstance().
																		getByStockId(stock.getId());
					if(!transactions.isEmpty()){
						for(Transaction t: transactions){
//							TransactionDB.delete(t); -Old Code
							queries.add(transactionTable.getDeleteQ(t));
						}
					}
//					StockDB.delete(stock);
					queries.add(stockTable.getDeleteQ(stock));
				}
//				ProductDB.delete(product);
				queries.add(productTable.getDeleteQ(productStockItem));
				batchQ(queries);
				check = true;
			}
			return check;
		}


	// private get complex business object ProductStockItem 
	private List<ProductStockItem> get(String tableColumnName, Object value) 
														throws DBException, SQLException{
		List<ProductStockItem> productStockItems = new ArrayList<>();
		Table table1 = productTable;
		Table table2 = stockTable;
		ArrayList<Integer> table1Columns = table1.getColumnIdArrayList();
		ArrayList<Integer> table2Columns = table2.getColumnIdArrayList();
		String sql = "SELECT ";
		for(Integer i: table1Columns){
			sql += table1.getTableColumnName(i) + ", ";
		}
		sql += " \n";
		for(Integer i: table2Columns){
			sql += table2.getTableColumnName(i) +", ";
		}
		sql = sql.substring(0,sql.length()-2);
		sql += "\nFROM " + table1.getName() + " " +
				"\nLEFT JOIN " + table2.getName() + " ON  " + table1.getTableColumnName(0) +
				" = " + table2.getTableColumnName(1) +" ";
		if(tableColumnName != null && value != null && !tableColumnName.isEmpty()){
			ArrayList<Table> tables = new ArrayList<>();
			tables.add(table1);
			tables.add(table2);
			sql += getCondition( tables, tableColumnName, value);
		}
		ResultSet rs = selectQ(sql);
		while(rs.next()){
			ProductStockItem p = new ProductStockItem();
			p.setId(rs.getInt(table1.getTableColumnName(0)));
			p.setName(rs.getString(table1.getTableColumnName(1)));
			p.setType(ProductType.valueOf(rs.getString(table1.getTableColumnName(2))));
			p.setBrand(rs.getString(table1.getTableColumnName(3)));
			p.setModel(rs.getString(table1.getTableColumnName(4)));
			p.setAveragePrice(rs.getDouble(table1.getTableColumnName(5)));
			p.setStatus(rs.getBoolean(table1.getTableColumnName(6)));
			p.setStockId(rs.getInt(table2.getTableColumnName(0)));
			p.setQuantity(rs.getInt(table2.getTableColumnName(2)));
			p.setStockCap(rs.getInt(table2.getTableColumnName(3)));

			productStockItems.add(p);
		}
		DBUtilNew.closeConnection();
		return productStockItems;
	}


	/*Implemented in super class - QueryTool */
	//	//private method to get condition query string
	//	private static String getCondition(Table table1, Table table2, 
	//								String tableColumnName,	Object value) throws DBException {
	//		String sql = "";
	//		
	//		
	//		
	//		return sql;
	//	}

}