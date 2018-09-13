

package warehouseStockTracker.db;

@SuppressWarnings("serial")
public class DBException extends Exception {
	
	public DBException(){}
	
	public DBException(Exception e){
		super(e);
		
	}
}