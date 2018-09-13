
package warehouseStockTracker.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class QueryTool {

	// restrict constructor
	protected QueryTool(){}

	// method to run select query  
	protected static ResultSet selectQ(String sql){
		ResultSet rs = null;
		Connection connection = DBUtilNew.getConnection();
		try {
			PreparedStatement query = connection.prepareStatement(sql);
			rs = query.executeQuery();
			return rs;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	// method to run queries in batch
	protected static void batchQ(List<String> queries){
		try{
			Connection connection = DBUtilNew.getConnection();
			Statement statement = connection.createStatement();
			connection.setAutoCommit(false);
			for(String s: queries){
				statement.addBatch(s);
			}
			statement.executeBatch();
			connection.commit();
			DBUtilNew.closeConnection();
			return;
		}
		catch(SQLException e){
			System.out.println(e);
			return;
		}
		
	}

	// method to get query string for condition
	protected static String getCondition(ArrayList<Table> tables, 
			String tableColumnName,	Object value) throws DBException {
		String sql = "";
		if(tableColumnName == null || value == null || tableColumnName.isEmpty()
				|| tableColumnName.trim().indexOf(".") == -1){
			throw new DBException();
		}
		else{
			tableColumnName = tableColumnName.trim();
			int qualifierIndex = tableColumnName.indexOf(".");
			String tableName = tableColumnName.substring(0, qualifierIndex);
			String columnName = tableColumnName.substring(qualifierIndex+1);
			boolean tableFound = false;
			boolean columnFound = false;
			for(Table t: tables){
				if(!tableFound && tableName.equals(t.getName())){
					tableFound = true;
					ArrayList<Integer> columnList = t.getColumnIdArrayList();
					for(Integer i: columnList){
						Column c = t.getColumn(i);
						if(!columnFound && columnName.equals(c.getName())){
							columnFound = true;
							sql += "\nWHERE " + t.getTableColumnName(i) + " = " + t.parse(c, value) +" ";
						}
					}
				}
			}
		}
		return sql;
	}
}