
package warehouseStockTracker.db;

import java.util.List;

public interface TableInterface {
	
	// get select query for all records with all columns
	public String getSelectQ() throws DBException;
	
	// get select sql query for all records
	public String getSelectQ(List<Integer> columns) throws DBException ;
	
	//get select sql query for records with specific criteria
	public String getSelectQ(List<Integer> columns, int columnId, Object value) throws DBException;
	
	//get insert sql query 
	public String getInsertQ(List<Object> values) throws DBException;
	
	//get insert sql query for selected columns
	public String getInsertQ(List<Integer> columns, List<Object> values) throws DBException;
	
	//get update sql query with specific condition
	public String getUpdateQ(List<Integer> columns, List<Object> values, int columnId, Object value) throws DBException;
	
	//get delete sql query
	public String getDeleteQ(int columnId, Object value) throws DBException;
	
}