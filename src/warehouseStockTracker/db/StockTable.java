
package warehouseStockTracker.db;

import java.util.ArrayList;

import warehouseStockTracker.Business.Stock;

public class StockTable extends Table {
	
	//constructor
	public StockTable(String name) {
		super(name);
		try {
			addColumn(0, "StockID", "INT", true, true);
			addColumn(1, "ProductID", "INT", "Product");
			addColumn(2, "Quantity", "INT");
			addColumn(3, "Capacity", "INT");
		} catch (DBException e) {
			System.out.println(e);
		}	
	}
	
	// method to get InsertQ from Stock object
	public String getInsertQ(Stock stock) throws DBException {
		ArrayList<Object> values = getValuesList(stock); 
		values.remove(0);
		ArrayList<Integer> columnList = getColumnIdArrayList();
		columnList.remove(0);
		return super.getInsertQ(columnList, values);	
	}
	
	// method to get UpdateQ from Stock object
	public String getUpdateQ(Stock stock) throws DBException {
		ArrayList<Object> values = getValuesList(stock);
		ArrayList<Integer> columnList = getColumnIdArrayList();
		return super.getUpdateQ(columnList, values, 0, stock.getId());
	}
	
	// method to get DeleteQ from Stock object
	public String getDeleteQ(Stock stock) throws DBException {
		return super.getDeleteQ(0, stock.getId());
	}
	
	// private method to cast object fields to list of Objects
		private ArrayList<Object> getValuesList(Stock stock) {
			ArrayList<Object> values = new ArrayList<>();
			values.add(new Integer(stock.getId()));
			values.add(new Integer(stock.getProductId()));
			values.add(new Integer(stock.getQuantity()));
			values.add(new Integer(stock.getCapacity()));
			return values;
		}
}