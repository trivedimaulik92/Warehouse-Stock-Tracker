
package warehouseStockTracker.db;

import java.util.ArrayList;

public abstract class DataBase {
	
	// declaring variables
	private String databaseName;
	private ArrayList<Table> tables;
	
	// constructor 
	private DataBase(){}
	
	public DataBase(String databasename, ArrayList<Table> tables){
		this();
		this.databaseName = databasename;
		this.tables = tables;
	}

	// getter and setter methods
	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public ArrayList<Table> getTables() {
		return tables;
	}

	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}
	
}