
package warehouseStockTracker.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import warehouseStockTracker.Business.Transaction;

public class TransactionTable extends Table {

	// constructor
	public TransactionTable (String name){
		super(name);
		try {
			addColumn(0, "TransactionID", "INT", true, true);
			addColumn(1, "StockID", "INT", "Stock");
			addColumn(2, "VendorID", "INT", "Vendor");
			addColumn(3, "Type", "VARCHAR(25)");
			addColumn(4, "Quantity", "INT");
			addColumn(5, "Datetime", "TIMESTAMP");
		} catch (DBException e) {
			System.out.println(e);
		}	
	}

	// method to get LastRecordId by stockId
	public int getLastRecordId(int stockId) throws DBException {
		int id = -1;
		String sql = "SELECT "+ getColumn(0).getName() +" as lastRecord " +
				"\nFROM  " + getName() + " " +
				"\nWHERE " + getColumn(1).getName() + " = " + stockId + " "+
				"\nORDER BY " + getColumn(0).getName() + " DESC " +
				"\nLIMIT 1";
		
		ResultSet rs = QueryTool.selectQ(sql);
		try {
			if(rs.next()){
				id = rs.getInt("lastRecord");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return id;
	}


	// method to get InsertQ from Transaction object
	public String getInsertQ(Transaction transaction) throws DBException {
		transaction.setDatetime(Timestamp.valueOf(LocalDateTime.now()));
		ArrayList<Object> values = getValuesList(transaction); 
		values.remove(0);
		ArrayList<Integer> columnList = getColumnIdArrayList();
		columnList.remove(0);
		return super.getInsertQ(columnList, values);	
	}

	// method to get UpdateQ from Transaction object
	public String getUpdateQ(Transaction transaction) throws DBException {
		ArrayList<Object> values = getValuesList(transaction);
		ArrayList<Integer> columnList = getColumnIdArrayList();
		return super.getUpdateQ(columnList, values, 0, transaction.getId());
	}

	// method to get DeleteQ from Transaction object
	public String getDeleteQ(Transaction transaction) throws DBException {
		return super.getDeleteQ(0, transaction.getId());
	}


	// private method to cast object fields to list of Objects
	private ArrayList<Object> getValuesList(Transaction transaction) {
		ArrayList<Object> values = new ArrayList<>();
		values.add(new Integer(transaction.getId()));
		values.add(new Integer(transaction.getStockId()));
		values.add(new Integer(transaction.getVendorId()));
		values.add(new String(transaction.getType().toString()));
		values.add(new Integer(transaction.getQuantity()));
		values.add(new Timestamp(transaction.getDatetime().getTime()));
		return values;
	}
}