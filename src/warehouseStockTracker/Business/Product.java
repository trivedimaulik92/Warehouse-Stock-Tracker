/* 
 * Class : Product
 * Author : Maulik Trivedi, Jemini Malaviya
 * Creation Date : 4/21/2017
 * Last Modified Date : 4/21/2017
 */

package warehouseStockTracker.Business;

import java.text.NumberFormat;
public class Product {

	// declaring private variables
	private int id;
	private String name;
	private ProductType type;
	private String brand;
	private String model;
	private double averagePrice;
	private boolean status;

	// constructors
	public Product(){
		id = 0;
		name = "";
		type = ProductType.laptop;
		brand = "";
		model = "";
		averagePrice = 0.0;
		status = false;
	}

	public Product(int id, String description, String type, String brand, String model,
			double averagePrice, boolean status){
		this.id = id;
		this.name = description;
		this.type = ProductType.valueOf(type);
		this.brand = brand;
		this.model = model;
		this.averagePrice = averagePrice;
		this.status = status;
	}

	// getter and setter methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	// override toString method
	public String toString(){
		return  id + "\t" + name + "\t" + type + 
				"\t" + brand + "\t" + model + "\t" + averagePrice + "\t" + status;
	}

	// override clone method
	public Object clone() throws CloneNotSupportedException {
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setBrand(brand);
		product.setType(type);
		product.setStatus(status);
		product.setAveragePrice(averagePrice);
		product.setModel(model);
		return product;
	}
}