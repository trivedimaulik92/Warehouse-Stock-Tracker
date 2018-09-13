package warehouseStockTracker.db.Old_Classes;
//
//
//package warehouseStockTracker.db;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//
//import java.sql.SQLException;
//
//import warehouseStockTracker.business.Product;
//import warehouseStockTracker.business.ProductStockItem;
//import warehouseStockTracker.business.Stock;
//import warehouseStockTracker.business.Transaction;
//
//public class ProductHandlerNew{
//
//	// get all ProductStockItems
//	public static List<ProductStockItem> getAll() throws DBException {
//		List<ProductStockItem> productStockItems = new ArrayList<>();
//		String sql = "SELECT * "
//				+ "FROM Product "
//				+ "LEFT JOIN Stock ON Product.ProductID = Stock.ProductID ";
//		Connection connection = DBUtil.getConnection();
//		try(PreparedStatement ps = connection.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery()){
//			while(rs.next()){
//				int productId = rs.getInt("Product.ProductID");
//				String name = rs.getString("Product.Name");
//				String type = rs.getString("Product.Type");
//				String brand = rs.getString("Product.Brand");
//				String model = rs.getString("Product.Model");
//				double averagePrice = rs.getDouble("Product.AveragePrice");
//				boolean status = rs.getBoolean("Product.Status");
//				int stockId = 0, quantity = 0, capacity = 0;
//				if(status){
//					stockId = rs.getInt("Stock.StockID");
//					quantity = rs.getInt("Stock.Quantity");
//					capacity = rs.getInt("Stock.Capacity");
//				}
//				ProductStockItem p = new ProductStockItem();
//				p.setId(productId);
//				p.setName(name);
//				p.setType(type);
//				p.setBrand(brand);
//				p.setModel(model);
//				p.setAveragePrice(averagePrice);
//				p.setStatus(status);
//				p.setStockId(stockId);
//				p.setQuantity(quantity);
//				p.setCapacity(capacity);
//
//				productStockItems.add(p);
//			}
//			return productStockItems;
//		}
//		catch(SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// create product method - add product and respective stock entry depending on capacity
//	public static ProductStockItem createProduct(ProductStockItem productStockItem) throws DBException {
//		if(productStockItem != null){
//			productStockItem.setId(ProductDB.generateProductId(productStockItem.getType()));
//			ProductDB.add(productStockItem);
//			if (productStockItem.getId() != -1 && productStockItem.getCapacity() > 0){
//				Stock stock = new Stock();
//				stock.setProductId(productStockItem.getId());
//				stock.setCapacity(productStockItem.getCapacity());
//				stock.setQuantity(0);
//				productStockItem.setStatus(true);
//				ProductDB.update(productStockItem);
//				StockDB.add(stock);	
//				stock = StockDB.getByProductId(productStockItem.getId());
//				productStockItem.setStockId(stock.getId());
//				productStockItem.setQuantity(stock.getQuantity());
//				productStockItem.setCapacity(stock.getCapacity());
//			}
//		}
//		return productStockItem;
//	}
//
//	// update product info
//	public static ProductStockItem updateProduct(ProductStockItem productStockItem) throws DBException, CloneNotSupportedException {	
//		ProductStockItem p = new ProductStockItem();
//		if(productStockItem != null){
//			p = (ProductStockItem)productStockItem.clone();
//			if(!p.isStatus() && p.getCapacity() > 0){
//				p.setStatus(true);
//				ProductDB.update(p);
//				Stock stock = new Stock();
//				stock.setProductId(p.getId());
//				stock.setQuantity(0);
//				stock.setCapacity(p.getCapacity());
//				StockDB.add(stock);
//				stock = StockDB.getByProductId(p.getId());
//				p.setStockId(stock.getId());
//			}
//			else if(!p.isStatus() && p.getCapacity() <= 0){
//				p.setStatus(false);
//				ProductDB.update(p);
//			}
//			else if(p.isStatus() && p.getCapacity() > 0){
//				p.setStatus(true);
//				ProductDB.update(p);
//				Stock stock = StockDB.getByProductId(p.getId());
//				stock.setCapacity(p.getCapacity());
//				StockDB.update(stock);
//			}
//			else if(p.isStatus() &&  p.getCapacity() <= 0){
//				p.setStatus(false);
//				ProductDB.update(p);
//				Stock stock = StockDB.getByProductId(p.getId());
//				StockDB.delete(stock);
//				p.setStockId(0);
//				p.setStockCap(0);
//				p.setQuantity(0);
//			}
//		}
//		return p;
//	}
//
//	//	// deactivate product - deactivate product
//	//	public static void deactivateProduct(int productId) throws DBException {
//	//		Product product = ProductDB.getByProductId(productId);
//	//		if(product != null){
//	//			ProductDB.setStatus(product, false);
//	//		}
//	//		
//	//	}
//	//	
//	//	// edit product info
//	//	public static void editProduct(Product product) throws DBException {
//	//		if(product!= null){
//	//			ProductDB.update(product);			
//	//		}
//	//	}
//	//	
//	// delete product
//	public static boolean deleteProduct(Product product) throws DBException {
//		boolean check = false;
//		if(product != null){
//			Stock stock = StockDB.getByProductId(product.getId());
//			if(stock != null){
//				List<Transaction> transactions = TransactionDB.getByStockId(stock.getId());
//				if(!transactions.isEmpty()){
//					for(Transaction t: transactions){
//						TransactionDB.delete(t);
//					}
//				}
//				StockDB.delete(stock);
//			}
//			ProductDB.delete(product);
//			check = true;
//		}
//		return check;
//	}
//
//
//	// Archive Products
//
//}