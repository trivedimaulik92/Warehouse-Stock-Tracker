

package warehouseStockTracker.Business;

import java.sql.Timestamp;

import warehouseStockTracker.Business.TransactionType;
public class Transaction {

	// declaring private variables
	private int id;
	private int stockId;
	private int vendorId;
	private TransactionType type;
	private int quantity;
	private Timestamp datetime;
	
	// constructor
	public Transaction(){
		id = 0;
		stockId = 0;
		vendorId = 0;
		type = null;
		quantity = 0;
		datetime = null;
	}
	public Transaction(int id, int stockId, int userId, String type, int quantity, Timestamp datetime){
		this.id = id;
		this.stockId = stockId;
		this.vendorId = userId;
		this.type = TransactionType.valueOf(type);
		this.quantity = quantity;
		this.datetime = datetime;
		
	}

	// getter and setter methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int userId) {
		this.vendorId = userId;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Timestamp getDatetime() {
		return datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}
	
	// overriding toString() method
	public String toString(){
		return id + "\t" + stockId + "\t" + vendorId + "\t" + 
				type + "\t" + quantity + "\t" + datetime;
	}
}