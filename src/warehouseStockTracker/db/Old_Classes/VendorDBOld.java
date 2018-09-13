//
//
package warehouseStockTracker.db.Old_Classes;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import warehouseStockTracker.Business.Vendor;
//
//public class VendorDBOld{
//	
//	public static List<Vendor> getAll() throws DBException {
//		
//		String sql = "Select * FROM Vendor ORDER By VendorID";
//		List<Vendor> vendors = new ArrayList<>();
//		Connection connection = DBUtilOld.getConnection();
//		try(PreparedStatement ps = connection.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery()){
//			while (rs.next()){
//				int vendorId = rs.getInt("VendorID");
//				String name = rs.getString("Name");
//				String contactName = rs.getString("ContactName");
//				String contactAddress = rs.getString("ContactAddress");
//				String contactPhone = rs.getString("ContactPhone");
//				String contactEmail = rs.getString("ContactEmail");
//								
//				Vendor v = new Vendor();
//				v.setId(vendorId);
//				v.setName(name);
//				v.setContactName(contactName);
//				v.setContactAddress(contactAddress);
//				v.setContactPhone(contactPhone);
//				v.setContactEmail(contactEmail);
//				vendors.add(v);
//			}
//			return vendors;
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//	
//	// get by VendorId
//	public static Vendor getByVendorID(int vendorId) throws DBException {
//		String sql = "SELECT * FROM Vendor WHERE VendorID = ?";
//		Connection connection = DBUtilOld.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, vendorId);
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()){
////				int vendorId = rs.getInt("VendorID");
//				String name = rs.getString("Name");
//				String contactName = rs.getString("ContactName");
//				String contactAddress = rs.getString("ContactAddress");
//				String contactPhone = rs.getString("ContactPhone");
//				String contactEmail = rs.getString("ContactEmail");
//				rs.close();
//				Vendor v = new Vendor();
//				v.setId(vendorId);
//				v.setName(name);
//				v.setContactName(contactName);
//				v.setContactAddress(contactAddress);
//				v.setContactPhone(contactPhone);
//				v.setContactEmail(contactEmail);
//				
//				return v;
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
//	// get by Vendor name
//	public static Vendor getByName(String name) throws DBException {
//		String sql = "SELECT * FROM Vendor WHERE Name = ?";
//		Connection connection = DBUtilOld.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setString(1, name);
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()){
//				int vendorId = rs.getInt("VendorID");
//				String contactName = rs.getString("ContactName");
//				String contactAddress = rs.getString("ContactAddress");
//				String contactPhone = rs.getString("ContactPhone");
//				String contactEmail = rs.getString("ContactEmail");
//				rs.close();
//				Vendor v = new Vendor();
//				v.setId(vendorId);
//				v.setName(name);
//				v.setContactName(contactName);
//				v.setContactAddress(contactAddress);
//				v.setContactPhone(contactPhone);
//				v.setContactEmail(contactEmail);
//				
//				return v;
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
//	public static void add(Vendor vendor) throws DBException {
//		String sql = "Insert INTO Vendor(Name, ContactName, ContactAddress, ContactPhone,"
//				+ "ContactEmail)"+
//					"VALUES(?, ?, ?, ?, ?)";
//		Connection connection = DBUtilOld.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setString(1, vendor.getName());
//			ps.setString(2, vendor.getContactName());
//			ps.setString(3, vendor.getContactAddress());
//			ps.setString(4, vendor.getContactPhone());
//			ps.setString(5, vendor.getContactEmail());
//			ps.executeUpdate();
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//	
//	public static void update(Vendor vendor) throws DBException {
//		String sql = "UPDATE Vendor SET " +
//						"Name = ?, " +
//						"ContactName = ?, " +
//						"ContactAddress = ?, " +
//						"ContactPhone = ?, " +
//						"ContactEmail = ? " +
//						"WHERE VendorID = ? ";
//		Connection connection = DBUtilOld.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setString(1, vendor.getName());
//			ps.setString(2, vendor.getContactName());
//			ps.setString(3, vendor.getContactAddress());
//			ps.setString(4, vendor.getContactPhone());
//			ps.setString(5, vendor.getContactEmail());
//			ps.setInt(7, vendor.getId());
//			ps.executeUpdate();
//		}
//		catch (SQLException e){
//			throw new DBException(e);
//		}
//	}
//	
//	public static void delete(Vendor vendor) throws DBException {
//		String sql = "DELETE FROM Vendor " +
//					"WHERE VendorID = ? ";
//		Connection connection = DBUtilOld.getConnection();
//		try (PreparedStatement ps = connection.prepareStatement(sql)){
//			ps.setInt(1, vendor.getId());
//			ps.executeUpdate();
//		}
//		catch(SQLException e){
//			throw new DBException(e);
//		}
//	}
//}