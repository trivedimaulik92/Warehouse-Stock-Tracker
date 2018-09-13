package warehouseStockTracker.db.Old_Classes;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import warehouseStockTracker.Business.Stock;
//
//public class StockDBOld{
//	// get all stocks
//	public static List<Stock> getAll() throws DBException {
//		
//		String sql = "Select * FROM Stock ORDER By StockID";
//		List<Stock> stocks = new ArrayList<>();
//		Connection connection = DBUtilOld.getConnection();
//		try(PreparedStatement ps = connection.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery()){
//			while (rs.next()){
//				int stockId = rs.getInt("StockID");
//				int productId = rs.getInt("ProductID");
//				int quantity = rs.getInt("Quantity");
//				int capacity = rs.getInt("Capacity");
//								
//				Stock s = new Stock();
//				s.setId(stockId);
//				s.setProductId(productId);
//				s.setQuantity(quantity);
//				s.setCapacity(capacity);
//				stocks.add(s);
//				
//			}
//			return stocks;
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//	
//	// get stock by productId
//	public static Stock getByProductId(int productId) throws DBException {
//		String sql = "SELECT * FROM Stock WHERE ProductID = ?";
//		Connection connection = DBUtilOld.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, productId);
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()){
//				int id = rs.getInt("StockID");
//				int quantity = rs.getInt("Quantity");
//				int capacity = rs.getInt("Capacity");
//				rs.close();
//				Stock s = new Stock();
//				s.setId(id);
//				s.setProductId(productId);
//				s.setQuantity(quantity);
//				s.setCapacity(capacity);
//				
//				return s;
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
//	// get stock by stockId
//		public static Stock getByStockId(int stockId) throws DBException {
//			String sql = "SELECT * FROM Stock WHERE StockID = ?";
//			Connection connection = DBUtilOld.getConnection();
//			try (PreparedStatement ps = connection.prepareStatement(sql)){
//				ps.setInt(1, stockId);
//				ResultSet rs = ps.executeQuery();
//				if(rs.next()){
//					int productId = rs.getInt("ProductID");
//					int quantity = rs.getInt("Quantity");
//					int capacity = rs.getInt("Capacity");
//					rs.close();
//					Stock s = new Stock();
//					s.setId(stockId);
//					s.setProductId(productId);
//					s.setQuantity(quantity);
//					s.setCapacity(capacity);
//					
//					return s;
//					
//				} else {
//					return null;
//				}
//			} catch (SQLException e){
//				throw new DBException(e);
//			}
//		}
//		
//	// add stock record
//	static boolean add(Stock stock) throws DBException {
//		String sql = "Insert INTO Stock(ProductID, Quantity, Capacity)"+
//					"VALUES(?, ?, ?)";
//		Connection connection = DBUtilOld.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, stock.getProductId());
//			ps.setInt(2, stock.getQuantity());
//			ps.setInt(3, stock.getCapacity());
//			ps.executeUpdate();
//			return true;
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//	
//	
//	// update stock record
//	public static void update(Stock stock) throws DBException {
//		String sql = "UPDATE Stock SET " +
//						"ProductID = ?, " +
//						"Quantity = ?, " +
//						"Capacity = ? " +
//						"WHERE StockID = ? ";
//		Connection connection = DBUtilOld.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, stock.getProductId());
//			ps.setInt(2, stock.getQuantity());
//			ps.setInt(3, stock.getCapacity());
//			ps.setInt(4, stock.getId());
//			ps.executeUpdate();
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//	
//	//delete stock record
//	public static void delete(Stock stock) throws DBException {
//		String sql = "DELETE FROM Stock " +
//					"WHERE StockID = ? ";
//		Connection connection = DBUtilOld.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, stock.getId());
//			ps.executeUpdate();
//		}
//		catch(SQLException e){
//			throw new DBException(e);
//		}
//	}
//}