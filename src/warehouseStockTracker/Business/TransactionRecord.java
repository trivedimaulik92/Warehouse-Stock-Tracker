

package warehouseStockTracker.Business;

import java.util.Comparator;

public class TransactionRecord extends Transaction {
	// declaring product and vendor variables
	private String productName;
	private String vendorName;
	
	// public constructor
	public TransactionRecord(){
		super();
		productName = "";
		vendorName = "";
	}

	// getter and setter methods
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	// override toString() method
	public String toString(){
		return productName + "\t" + vendorName + "\t" + getType() + "\t" + 
					getQuantity()+ "\t" + getDatetime();
	}	
	
	public static Comparator<TransactionRecord> compareById(){
		Comparator<TransactionRecord> compare = new Comparator<TransactionRecord>(){
			public int compare(TransactionRecord r1, TransactionRecord r2){
				if(r1.getId()>r2.getId()){
					return 1;
				}
				else {
					return -1;
				}
			}
		};
		return compare;
	}
}