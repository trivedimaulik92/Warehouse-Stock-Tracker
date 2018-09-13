

package warehouseStockTracker.Business;

public class InvalidStockOpException extends Exception {
	
	// public constructor
	public InvalidStockOpException(){}
	
	public InvalidStockOpException(String message){
		super(message);
	}
}