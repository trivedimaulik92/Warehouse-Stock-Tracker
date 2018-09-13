/* 
 * Class : Stock
 * Author : Maulik Trivedi, Jemini Malaviya
 * Creation Date : 4/21/2017
 * Last Modified Date : 4/21/2017
 */

package warehouseStockTracker.Business;

public class Stock implements StockInterface {

	// declaring private variables
	private int id;
	private int productId;
	private int quantity;
	private int capacity;

	// constructor
	public Stock(){
		id = 0;
		productId = 0;
		quantity = 0;
		capacity = 0;
	}

	public Stock(int id,int productId, int quantity, int capacity, boolean status){
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
		this.capacity = capacity;
	}


	// getter and setter methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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


	// override toString method
	public String toString(){
		return id + "\t" + productId + "\t" + 
				quantity + "\t" + capacity;
	}

	// add quantity
	public void addStock(int amount) throws InvalidStockOpException {
			if (amount <= capacity-quantity){
				quantity += amount;
			} 
			else {
				// throw StockException
				throw new InvalidStockOpException("Stock capacity less than Required!");
			}
	}

	// remove quantity
	public void removeStock(int amount) throws InvalidStockOpException {
		if(amount <= quantity){
			quantity -= amount;
		}
		else {
			// throw StockException
			throw new InvalidStockOpException("Required Stock not available!");
		}
	}

	@Override
	public void setStockCap(int capacity) {
		setCapacity(capacity);
	}

	@Override
	public int getCurrentStock() {
		return getQuantity();
	}
}