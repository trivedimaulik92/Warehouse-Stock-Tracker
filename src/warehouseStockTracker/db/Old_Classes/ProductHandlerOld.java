package warehouseStockTracker.db.Old_Classes;
//
//
//package warehouseStockTracker.db;
//
//import java.util.List;
//
//import warehouseStockTracker.business.Product;
//import warehouseStockTracker.business.Stock;
//import warehouseStockTracker.business.Transaction;
//
//public class ProductHandler{
//	
//	
//	// create product method - add product and respective stock entry depending on capacity
//	public static Product createProduct(Product product, int capacity) throws DBException {
//		if(product != null){
//			product.setId(ProductDB.generateProductId(product.getType()));
//			ProductDB.add(product);
//			if (product.getId() != -1 && capacity > 0){
//				Stock stock = new Stock();
//				stock.setProductId(product.getId());
//				stock.setCapacity(capacity);
//				stock.setQuantity(0);
//				product.setStatus(true);
//				ProductDB.update(product);
//				StockDB.add(stock);	
//			}
//		}
//		return product;
//	}
//	
//	// update product info
//	public static Product updateProduct(Product product, int capacity) throws DBException, CloneNotSupportedException {	
//		Product p = new Product();
//		if(product != null){
//			p = (Product)product.clone();
//			if(!p.isStatus() && capacity > 0){
//				p.setStatus(true);
//				ProductDB.update(p);
//				Stock stock = new Stock();
//				stock.setProductId(p.getId());
//				stock.setQuantity(0);
//				stock.setCapacity(capacity);
//				StockDB.add(stock);
//			}
//			else if(!p.isStatus() && capacity <= 0){
//				product.setStatus(false);
//				ProductDB.update(p);
//			}
//			else if(p.isStatus() && capacity > 0){
//				p.setStatus(true);
//				ProductDB.update(p);
//				Stock stock = StockDB.getByProductId(p.getId());
//				stock.setCapacity(capacity);
//				StockDB.update(stock);
//			}
//			else if(p.isStatus() &&  capacity <= 0){
//				p.setStatus(false);
//				ProductDB.update(p);
//				Stock stock = StockDB.getByProductId(p.getId());
//				StockDB.delete(stock);
//			}
//		}
//		return p;
//	}
//	
////	// deactivate product - deactivate product
////	public static void deactivateProduct(int productId) throws DBException {
////		Product product = ProductDB.getByProductId(productId);
////		if(product != null){
////			ProductDB.setStatus(product, false);
////		}
////		
////	}
////	
////	// edit product info
////	public static void editProduct(Product product) throws DBException {
////		if(product!= null){
////			ProductDB.update(product);			
////		}
////	}
////	
//	// delete product
//	public static boolean deleteProduct(Product product) throws DBException {
//		boolean check = false;
//		if(product != null){
//		Stock stock = StockDB.getByProductId(product.getId());
//		if(stock != null){
//			List<Transaction> transactions = TransactionDB.getByStockId(stock.getId());
//			if(!transactions.isEmpty()){
//			for(Transaction t: transactions){
//				TransactionDB.delete(t);
//			}
//			}
//			StockDB.delete(stock);
//		}
//		ProductDB.delete(product);
//		check = true;
//		}
//		return check;
//	}
//	
//	// Archive Products
//	
//	
//}