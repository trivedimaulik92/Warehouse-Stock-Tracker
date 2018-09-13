
package warehouseStockTracker.db;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBSQLQueryGen {
	public static void main(String args[]) throws DBException, SQLException{
		@SuppressWarnings("unused")
		ProductTable product = new ProductTable("Product");
		int[] columnsArray = {0,1,2,3,4,5,6};
		ArrayList<Integer> columns = new ArrayList<>();
		for(int i: columnsArray){
			columns.add(i);
		}
		
//		// Test DBUtil getAll()
//		ArrayList<Object> products = (ArrayList<Object>) DBUtilNew.getAll(product);
//		for(Object p: products){
//			System.out.println(p);
//		}
		
		
//		// Test TableInterface Methods for ProductTable
//		String sql = "";
//		sql = product.getSelectQ(columns);
//		ResultSet rs = DBUtilNew.SelectQ(sql);
//		sql = product.getSelectQ(columns, 0, "14");
//		String[] values = {"Dell Inspiron", "laptop", "Dell", "DELLINSP154", "180.00", "false"};
//		sql = product.getInsertQ( columns, values);
//		List<String> queries = new ArrayList<>();
//		queries.add(sql);
//		DBUtilNew.BatchQ(queries);
//		sql = product.getUpdateQ(columns, values, 0, "1");
//		sql = product.getDeleteQ(0, "1");
//		System.out.println(sql);
	}
}