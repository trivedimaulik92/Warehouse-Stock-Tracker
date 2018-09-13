package warehouseStockTracker.db.Old_Classes;
//
//
//package warehouseStockTracker.db;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import warehouseStockTracker.business.Product;
//import warehouseStockTracker.business.Stock;
//
//public class ProductDB{
//
//	// get all products from database
//	public static List<Product> getAll() throws DBException {
//
//		String sql = "Select * FROM Product ORDER BY ProductID";
//		List<Product> products = new ArrayList<>();
//		Connection connection = DBUtil.getConnection();
//		try(PreparedStatement ps = connection.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery()){
//			while (rs.next()){
//				int productId = rs.getInt("ProductID");
//				String name = rs.getString("Name");
//				String type = rs.getString("Type");
//				String brand = rs.getString("Brand");
//				String model = rs.getString("Model");
//				double averagePrice = rs.getDouble("AveragePrice");
//				boolean status = rs.getBoolean("Status");
//
//				Product p = new Product();
//				p.setId(productId);
//				p.setName(name);
//				p.setBrand(brand);
//				p.setType(type);
//				p.setModel(model);
//				p.setAveragePrice(averagePrice);
//				p.setStatus(status);
//
//				products.add(p);
//			}
//			return products;
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// get product based on stock
//	public static Product getByStock(Stock stock) throws DBException {
//		String sql = "SELECT * FROM Product WHERE ProductID = ?";
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, stock.getProductId());
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()){
//				//int productId = rs.getInt("ProductID");
//				String name = rs.getString("Name");
//				String type = rs.getString("Type");
//				String brand = rs.getString("Brand");
//				String model = rs.getString("Model");
//				double averagePrice = rs.getDouble("AveragePrice");
//				boolean status = rs.getBoolean("Status");
//				rs.close();
//				Product p = new Product();
//				p.setId(stock.getProductId());
//				p.setName(name);
//				p.setBrand(brand);
//				p.setModel(model);
//				p.setType(type);
//				p.setAveragePrice(averagePrice);
//				p.setStatus(status);
//
//				return p;
//
//			} else {
//				rs.close();
//				return null;
//			}
//		} catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// get product by productId
//	public static Product getByProductId(int ProductId) throws DBException {
//		String sql = "SELECT * FROM Product WHERE ProductID = ?";
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, ProductId);
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()){
//				//int productId = rs.getInt("ProductID");
//				String name = rs.getString("Name");
//				String type = rs.getString("Type");
//				String brand = rs.getString("Brand");
//				String model = rs.getString("Model");
//				double averagePrice = rs.getDouble("AveragePrice");
//				boolean status = rs.getBoolean("Status");
//				rs.close();
//				Product p = new Product();
//				p.setId(ProductId);
//				p.setName(name);
//				p.setBrand(brand);
//				p.setModel(model);
//				p.setType(type);
//				p.setAveragePrice(averagePrice);
//				p.setStatus(status);
//
//				return p;
//
//			} else {
//				rs.close();
//				return null;
//			}
//		} catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//	
//	// get product based on model
//	public static Product getByModel(String model) throws DBException {
//		String sql = "SELECT * FROM Product WHERE Model = ?";
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setString(1, model);
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()){
//				int productId = rs.getInt("ProductID");
//				String name = rs.getString("Name");
//				String type = rs.getString("Type");
//				String brand = rs.getString("Brand");
//				double averagePrice = rs.getDouble("AveragePrice");
//				//				String model = rs.getString("Model");
//				boolean status = rs.getBoolean("Status");
//				rs.close();
//				Product p = new Product();
//				p.setId(productId);
//				p.setName(name);
//				p.setBrand(brand);
//				p.setModel(model);
//				p.setType(type);
//				p.setAveragePrice(averagePrice);
//				p.setStatus(status);
//
//				return p;
//
//			} else {
//				rs.close();
//				return null;
//			}
//		} catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// get product list based on type
//	public static List<Product> getByType(String type) throws DBException {
//		String sql = "SELECT * FROM Product WHERE Type = ?";
//		List<Product> products = new ArrayList<>();
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setString(1, type);
//			ResultSet rs = ps.executeQuery();
//			while(rs.next())
//			{
//				int productId = rs.getInt("ProductID");
//				String name = rs.getString("Name");
//				//				String type = rs.getString("Type");
//				String brand = rs.getString("Brand");
//				String model = rs.getString("Model");
//				double averagePrice = rs.getDouble("AveragePrice");
//				boolean status = rs.getBoolean("Status");
//				rs.close();
//				Product p = new Product();
//				p.setId(productId);
//				p.setName(name);
//				p.setBrand(brand);
//				p.setModel(model);
//				p.setType(type);
//				p.setAveragePrice(averagePrice);
//				p.setStatus(status);
//
//				products.add(p);
//
//			} 
//			return products;
//
//		} catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// get product list based on company
//	public static List<Product> getByBrand(String brand) throws DBException {
//		String sql = "SELECT * FROM Product WHERE Brand = ?";
//		List<Product> products = new ArrayList<>();
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setString(1, brand);
//			ResultSet rs = ps.executeQuery();
//			while(rs.next())
//			{
//				int productId = rs.getInt("ProductID");
//				String name = rs.getString("Name");
//				String type = rs.getString("Type");
//				//				String brand = rs.getString("Brand");
//				String model = rs.getString("Model");
//				double averagePrice = rs.getDouble("AveragePrice");
//				boolean status = rs.getBoolean("Status");
//				rs.close();
//				Product p = new Product();
//				p.setId(productId);
//				p.setName(name);
//				p.setBrand(brand);
//				p.setModel(model);
//				p.setType(type);
//				p.setAveragePrice(averagePrice);
//				p.setStatus(status);
//
//				products.add(p);
//
//			} 
//			return products;
//
//		} catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// get count of products by type
//	private static int getCountByType(String type) throws DBException {
//		String sql = "SELECT COUNT(Type) AS \"count\" " +
//				"FROM Product " +
//				"WHERE Type = ? ";
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setString(1, type);
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()){
//				return rs.getInt("count");
//			}
//			else {
//				return 0;
//			}
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// generate product Id
//	static int generateProductId(String type) throws DBException{
//		int id;
//		switch (type) {
//		case "laptop" :
//			id = 10000000 + getCountByType(type) + 1;
//			break;
//		case "cellphone" :
//			id = 20000000 + getCountByType(type) + 1;
//			break;
//		case "tablet" :
//			id = 30000000 + getCountByType(type) + 1;
//			break;
//		case "printer" :
//			id = 40000000 + getCountByType(type) + 1;
//			break;
//		case "scanner" :
//			id = 50000000 + getCountByType(type) + 1;
//			break;
//		case "television" :
//			id = 60000000 + getCountByType(type) + 1;
//			break;
//		case "camera" :
//			id = 70000000 + getCountByType(type) + 1;
//			break;
//		case "projector" :
//			id = 80000000 + getCountByType(type) + 1;
//			break;
//		default :
//			id = -1;
//			break;
//		}
//		return id;
//	}
//
//	// create product entry returns productId
//	static void add(Product product) throws DBException {
//		String sql1 = "Insert INTO Product(ProductID, Name, Type, Brand, Model, AveragePrice, Status)"+
//				"VALUES(?, ?, ?, ?, ?, ?, ? )";
//		Connection connection = DBUtil.getConnection();
////		int productId = generateProductId(product.getType());
//		if (product.getId() != -1){
//			try (PreparedStatement ps = connection.prepareStatement(sql1)){
//				ps.setInt(1, product.getId());
//				ps.setString(2, product.getName());
//				ps.setString(3, product.getType());
//				ps.setString(4,  product.getBrand());
//				ps.setString(5, product.getModel());
//				ps.setDouble(6, product.getAveragePrice());
//				ps.setBoolean(7, false);
//				ps.executeUpdate();
//			}
//			catch (SQLException e){
//				throw new DBException(e);
//			}
//		}
//	}
//
////	// update status of a product
////	@SuppressWarnings("finally")
////	static boolean setStatus(Product product, boolean status) throws DBException {
////		boolean check = false;
////		String sql = "UPDATE Product SET " +
////				"Status = ? " +
////				"WHERE ProductID = ? ";
////		Connection connection = DBUtil.getConnection();
////		try (PreparedStatement ps = connection.prepareStatement(sql)){
////			ps.setBoolean(1, status);
////			ps.setInt(2, product.getId());
////			ps.executeUpdate();
////			check = true;
////		}
////		catch (SQLException e){
////			throw new DBException(e);
////		}
////		finally{
////			return check;
////		}
////	}
//
//	// update product record
//	public static int update(Product product) throws DBException {
//		String sql = "UPDATE Product SET " +
//				"Name = ?, " +
//				"Type = ?, " +
//				"Brand = ?, " +
//				"Model = ?,  " +
//				"AveragePrice = ?, " +
//				"Status = ? " +
//				" WHERE ProductID = ? ";
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setString(1, product.getName());
//			ps.setString(2, product.getType());
//			ps.setString(3, product.getBrand());
//			ps.setString(4, product.getModel());
//			ps.setDouble(5, product.getAveragePrice());
//			ps.setBoolean(6, product.isStatus());
//			ps.setInt(7, product.getId());
//			return ps.executeUpdate();
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//
//	// delete product record
//	public static void delete(Product product) throws DBException {
//		String sql = "DELETE FROM Product " +
//				"WHERE ProductID = ?";
//		Connection connection = DBUtil.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, product.getId());
//			ps.executeUpdate();
//		}
//		catch(SQLException e){
//			throw new DBException(e);
//		}
//	}
//}
