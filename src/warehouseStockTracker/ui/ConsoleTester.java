
package warehouseStockTracker.ui;

import java.util.List;

import warehouseStockTracker.Business.*;
import warehouseStockTracker.db.*;

public class ConsoleTester{
	public static void main(String args[]) throws DBException, CloneNotSupportedException {
//		// Old Product Handler test
//		Product p = new Product();
//		p.setName("Asus laptop");
//		p.setType("laptop");
//		p.setBrand("Asus");
//		p.setModel("AS3401T");
//		p.setAveragePrice(150.00);
//		Product p1 = ProductHandler.createProduct(p, 0);
//		System.out.println(p1);
//		Product p2 = ProductHandler.updateProduct(p1,1500);
//		System.out.println(p2);
//		Product p3 = ProductDB.getByModel("AS3401T");
//		ProductHandlerNew.deleteProduct(p3);
		
//		// New Product Handler test
//		ProductStockItem p = new ProductStockItem();
//		p.setName("Asus laptop");
//		p.setType("laptop");
//		p.setBrand("Asus");
//		p.setModel("AS3401T");
//		p.setAveragePrice(150.00);
//		ProductStockItem p1 = ProductHandlerNew.createProduct(p);
//		System.out.println(p1);
//		p1.setStockCap(2000);
//		ProductStockItem p2 = ProductHandlerNew.updateProduct(p1);
//		System.out.println(p2);
//		Product p3 = ProductDB.getByModel("AS3401T");
//		ProductHandlerNew.deleteProduct(p3);
		
		// 2nd Version ProductHandler Test
//		ProductStockItem p = new ProductStockItem();
//		p.setName("Dell Laptop");
//		p.setType(ProductType.valueOf("laptop"));
//		p.setBrand("Dell");
//		p.setModel("DELLINSP15");
//		p.setAveragePrice(180.00);
//		ProductStockItem p1 = ProductHandler.getInstance().createProduct(p);
//		System.out.println(p1);
//		p1.setStockCap(2000);
//		ProductStockItem p2 = ProductHandler.getInstance().updateProduct(p1);
//		System.out.println(p2);
		ProductStockItem p3 = ProductHandler.getInstance().getByProductId(10000004);
		System.out.println(p3);
		p3.setCapacity(0);
		ProductStockItem p4 = ProductHandler.getInstance().updateProduct(p3);
		System.out.println(p4);
//		// Vendor test
//		Vendor v = new Vendor();
//		v.setName("ABC Electronics");
//		v.setContactName("Bob Fishcer");
//		v.setContactAddress("Some St, zip");
//		v.setContactPhone("999-999-9999");
//		v.setContactEmail("bob@abcelec.com");
//		VendorDB.add(v);

		
//		// transaction test
//		Stock s = StockDB.getByProductId(ProductDB.getByModel("AS3401T").getId());
//		StockHandler.stockIn(s, v, 500);
//		StockHandler.stockOut(s, v, 200);
//		Transaction t = TransactionDB.getBYTransactionId(4);
//		TransactionDB.delete(t);
//		if(t != null){
//			StockHandler.revert(t);
//		}
		
//		// List Products, Stocks, ProductStockItems, Vendors, Transactions
//		List<Product> products = ProductDB.getAll();
//		for(Product p1: products){
//			System.out.println(p1);
//		}
//		List<Stock> stocks = StockDB.getAll();
//		for(Stock s: stocks){
//			System.out.println(s);
//		}
//		List<ProductStockItem> productStockItems = ProductHandlerNew.getAll();
//		for(ProductStockItem pstock: productStockItems){
//			System.out.println(pstock);
//		}
//		List<Vendor> vendors = VendorDB.getAll();
//		for(Vendor v: vendors){
//			System.out.println(v);
//		}
//		List<Transaction> transactions = TransactionDB.getAll();
//		for(Transaction t : transactions){
//			System.out.println(t);
//		}
		
		
//		// delete Product, Stock, Vendor
//		List<Vendor> vendors = VendorDB.getAll();
//		VendorDB.delete(vendors.get(1));
//		Vendor v1 = VendorDB.get("ABC Electronics");
//		VendorDB.delete(v1);
//		List<Stock> stocks = StockDB.getAll();
//	    StockDB.delete(stocks.get(0));
//		ProductStockItem p100 = new ProductStockItem();
//		p100.setId(10000002);
//		ProductHandler.getInstance().deleteProduct(p100);
	}
}