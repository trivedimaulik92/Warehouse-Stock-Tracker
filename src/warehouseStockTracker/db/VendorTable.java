
package warehouseStockTracker.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import warehouseStockTracker.Business.Vendor;

public class VendorTable extends Table{

	//constructor
	public VendorTable(String name){
		super(name);
		try {
			addColumn(0, "VendorID", "INT", true, true);
			addColumn(1, "Name", "VARCHAR(50)");
			addColumn(2, "ContactName", "VARCHAR(50)");
			addColumn(3, "ContactAddress", "VARCHAR(150)");
			addColumn(4, "ContactPhone", "VARCHAR(20)");
			addColumn(5, "ContactEmail", "VARCHAR(25)");
		} catch (DBException e) {
			System.out.println(e);
		}
	}

	// method to get vendor object from table by vendorName
	public Vendor getByVendorName(String vendorName) throws DBException {
		Vendor vendor = new Vendor();
		ResultSet rs = QueryTool.selectQ(getSelectQ(1, vendorName));
		try {
			if(rs.next()){
				vendor.setId(rs.getInt(1));
				vendor.setName(rs.getString(2));
				vendor.setContactName(rs.getString(3));
				vendor.setContactAddress(rs.getString(4));
				vendor.setContactPhone(rs.getString(5));
				vendor.setContactEmail(rs.getString(6));
				return vendor;
			} 
		}catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		return null;
	}


    // method to get vendor objects
	public ArrayList<Vendor> getVendors() throws DBException {
		ArrayList<Vendor> vendors = new ArrayList<>();
		ResultSet rs = QueryTool.selectQ(getSelectQ());
		try {
			while(rs.next()){
				Vendor v = new Vendor();
				v.setId(rs.getInt(1));
				v.setName(rs.getString(2));
				v.setContactName(rs.getString(3));
				v.setContactAddress(rs.getString(4));
				v.setContactPhone(rs.getString(5));
				v.setContactEmail(rs.getString(6));
				
				vendors.add(v);
				
			}
			return vendors;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// method to add vendor object
	public void addVendor(Vendor vendor) throws DBException {
		ArrayList<String> queries = new ArrayList<>();
		queries.add(getInsertQ(vendor));
		QueryTool.batchQ(queries);
	}
	
	// method to update vendor object
	public void updateVendor(Vendor vendor) throws DBException {
		ArrayList<String> queries = new ArrayList<>();
		queries.add(getUpdateQ(vendor));
		QueryTool.batchQ(queries);
	}
	
	// method to delete vendor object
	public void deleteVendor(Vendor vendor) throws DBException {
		ArrayList<String> queries = new ArrayList<>();
		queries.add(getDeleteQ(vendor));
		QueryTool.batchQ(queries);
	}
	
	// method to get InsertQ from Vendor object
	 String getInsertQ(Vendor vendor) throws DBException {
		ArrayList<Object> values = getValuesList(vendor); 
		values.remove(0);
		ArrayList<Integer> columnList = getColumnIdArrayList();
		columnList.remove(0);
		return super.getInsertQ(columnList, values);	
	}

	// method to get UpdateQ from Vendor object
	String getUpdateQ(Vendor vendor) throws DBException {
		ArrayList<Object> values = getValuesList(vendor);
		ArrayList<Integer> columnList = getColumnIdArrayList();
		return super.getUpdateQ(columnList, values, 0, vendor.getId());
	}

	// method to get DeleteQ from Vendor object
	String getDeleteQ(Vendor vendor) throws DBException {
		return super.getDeleteQ(0, vendor.getId());
	}

	// private method to cast object fields to list of Objects
	private ArrayList<Object> getValuesList(Vendor vendor) {
		ArrayList<Object> values = new ArrayList<>();
		values.add(new Integer(vendor.getId()));
		values.add(new String(vendor.getName()));
		values.add(new String(vendor.getContactName()));
		values.add(new String(vendor.getContactAddress()));
		values.add(new String(vendor.getContactPhone()));
		values.add(new String(vendor.getContactEmail()));
		return values;
	}
}