
package warehouseStockTracker.Business;

public interface StockInterface {
	
	// set stock capacity
	public void setStockCap(int capacity);
	
	// add stock 
	public void addStock(int amount) throws InvalidStockOpException;
	
	// remove stock
	public void removeStock(int amount) throws InvalidStockOpException ;
	
	// get current stock quantity
	public int getCurrentStock();
}