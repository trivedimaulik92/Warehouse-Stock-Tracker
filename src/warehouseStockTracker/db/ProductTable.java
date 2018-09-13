
package warehouseStockTracker.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import warehouseStockTracker.Business.Product;
import warehouseStockTracker.Business.ProductType;

public class ProductTable extends Table {

	// constructor
	public ProductTable(String name) {
		super(name);
		try {
			addColumn(0, "ProductID", "INT", true);
			addColumn(1, "Name", "VARCHAR(100)");
			addColumn(2, "Type", "VARCHAR(25)");
			addColumn(3, "Brand", "VARCHAR(25)");
			addColumn(4, "Model", "VARCHAR(25)");
			addColumn(5, "AveragePrice", "DOUBLE");
			addColumn(6, "Status", "BOOLEAN");
		} catch (DBException e) {
			System.out.println(e);
		}
	}	

	// method to generate productId
	public int generateProductId(ProductType type) throws DBException {
		int id = -1;
		switch (type){
		case laptop:
			id = 10000000 + getLastRecordId(type) - 10000000 + 1;
			break;
		case cellphone:
			id = 20000000 + getLastRecordId(type) - 20000000 + 1;
			break;
		case tablet:
			id = 30000000 + getLastRecordId(type) - 30000000 + 1;
			break;
		case printer:
			id = 40000000 + getLastRecordId(type) - 40000000 + 1;
			break;
		case scanner:
			id = 50000000 + getLastRecordId(type) - 50000000 + 1;
			break;
		case television:
			id = 60000000 + getLastRecordId(type) - 60000000 + 1;
			break;
		case camera:
			id = 70000000 + getLastRecordId(type) - 70000000 + 1;
			break;
		case projector:
			id = 80000000 + getLastRecordId(type) - 80000000 + 1;
			break;
		}
		return id;
	}

//	// private method to get product count by type - Faulty logic to generate ProductID
//	private int getCount(ProductType type) throws DBException {
//		int count = -1;
//		String sql = "SELECT COUNT(Type) AS \"count\" " +
//				"\nFROM " + getName() + " " +
//				"\nWHERE Type =  " + "\'"+type.toString()+"\'  ";
//		ResultSet rs = QueryTool.selectQ(sql);
//
//		try {
//			if(rs.next()){
//				count = rs.getInt("count");
//			}
//		} 
//		catch (SQLException e) {
//			System.out.println(e);
//		}
//		return count;
//	}
	
	// method to get LastRecordId by ProductType
		public int getLastRecordId(ProductType type) throws DBException {
			int id = -1;
			String sql = "SELECT "+ getColumn(0).getName() +" as lastRecord " +
					"\nFROM  " + getName() + " " +
					"\nWHERE " + getColumn(2).getName() + " =  \'" + type.toString() + "\' "+
					"\nORDER BY " + getColumn(0).getName() + " DESC " +
					"\nLIMIT 1";
			
			ResultSet rs = QueryTool.selectQ(sql);
			try {
				if(rs.next()){
					id = rs.getInt("lastRecord");
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
			return id;
		}
	// method to get InsertQ from Product object
	public String getInertQ(Product product) throws DBException {
//		int id = generateProductId(product.getType());
//		product.setId(id);
		ArrayList<Object> values = getValuesList(product);
		return super.getInsertQ(values);
	}
	
	// method to get UpdateQ from Product object
	public String getUpdateQ(Product product) throws DBException {
		ArrayList<Object> values = getValuesList(product);
		ArrayList<Integer> columnList = getColumnIdArrayList();
		return super.getUpdateQ(columnList, values, 0, product.getId());
	}
	
	//method to get DeleteQ from Product object
	public String getDeleteQ(Product product) throws DBException {
		return super.getDeleteQ(0, product.getId());
	}
	
	// private method to cast object fields to list of Objects
	private ArrayList<Object> getValuesList(Product product) {
		ArrayList<Object> values = new ArrayList<>();
		values.add(new Integer(product.getId()));
		values.add(product.getName());
		values.add(product.getType().toString());
		values.add(product.getBrand());
		values.add(product.getModel());
		values.add(new Double(product.getAveragePrice()));
		values.add(new Boolean(product.isStatus()));
		return values;
	}
}