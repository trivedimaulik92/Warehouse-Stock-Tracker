

package warehouseStockTracker.Business;

import warehouseStockTracker.Business.Product;
import warehouseStockTracker.db.DBException;


public class ProductStockItem extends Product implements StockInterface {
	
	// declaring variables
	private int stockId;
	private int quantity;
	private int capacity;
	
	// constructors
	public ProductStockItem(){
		super();
		stockId = 0;
		quantity = 0;
		capacity = 0;
	}

	// Getter and Setter methods
	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public String toString(){
		return super.toString() + "\t" + stockId + "\t" + quantity + "\t" + capacity; 
	}
	
	public Object clone() throws CloneNotSupportedException {
		ProductStockItem p = new ProductStockItem();
		Product p1 = (Product) super.clone();
		p.setId(p1.getId());
		p.setName(p1.getName());
		p.setType(p1.getType());
		p.setBrand(p1.getBrand());
		p.setModel(p1.getModel());
		p.setAveragePrice(p1.getAveragePrice());
		p.setStatus(p1.isStatus());
		p.setStockId(stockId);
		p.setQuantity(quantity);
		p.setCapacity(capacity);
		return p;
	}
	
	// stock set capacity
	public void setStockCap(int capacity){
		if(capacity > 0){
			setCapacity(capacity);
		}
	}
	
	// get current stock quantity
	public int getCurrentStock(){
		return getQuantity();
	}
	
	// add stock
	public void addStock(int amount) throws InvalidStockOpException {
		if (amount <= capacity-quantity){
			quantity += amount;
		} 
		else {
			// throw StockException
			throw new InvalidStockOpException("Stock capacity less than Required!");
		}
	}
	
	// remove stock
	public void removeStock(int amount) throws InvalidStockOpException {
		if(amount <= quantity){
			quantity -= amount;
		}
		else {
			// throw StockException
			throw new InvalidStockOpException("Required Stock not available!");
		}
	}
	
	
	
	// public method to extract stock object
		public Stock getStock() throws DBException {
			Stock stock = new Stock();
				stock.setId(getStockId());
				stock.setProductId(getId());
				stock.setQuantity(getQuantity());
				stock.setCapacity(getCapacity());
				return stock;	
		}
}