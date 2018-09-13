
package warehouseStockTracker.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public abstract class Table implements TableInterface {

	// declaring variables
	private String name;
	private ArrayList<Column> columns;
	private Column primaryKey;
	private ArrayList<Column> foreignKey;

	// constructors
	private Table()	{}

	protected Table(String name){
		this();
		this.name = name;
		this.primaryKey = null;
		this.columns = new ArrayList<>();
		this.foreignKey = new ArrayList<>();
	}

	protected Table(String name, ArrayList<Column> columns) {
		this();
		this.name = name;
		this.columns = columns;
	}


	// getter and setter methods
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<Column> columns) {
		this.columns = columns;
	}

	public Column getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Column primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getTableColumnName(int id){
		return getName()+"."+getColumn(id).getName();
	}

	public ArrayList<Integer> getColumnIdArrayList() {
		List<Integer> columnIdArray = new ArrayList<>();
		for(Column c: columns){
			columnIdArray.add(columns.indexOf(c));
		}
		return (ArrayList<Integer>) columnIdArray;
	}

	protected void addColumn(int id, String name, String type) throws DBException {
		if(name == null || type == null || name.isEmpty()  || type.isEmpty() || id < 0){
			throw new DBException();
		}
		else {
			Column c = new Column(id, name, type);
			columns.add(c);
		}
	}

	protected void addColumn(int id, String name, String type, 
			Boolean primarykey) throws DBException {
		if(name == null || type == null || name.isEmpty()  || type.isEmpty() || id < 0){
			throw new DBException();
		}
		else {
			Column c = new Column(id, name, type);
			c.setPrimaryKey(true);
			this.primaryKey = c;
			columns.add(c);
		}
	}

	protected void addColumn(int id, String name, String type, 
			boolean primaryKey, boolean autoIncrement) throws DBException {
		if(name == null || type == null || name.isEmpty()  || type.isEmpty() || id < 0){
			throw new DBException();
		} 
		else{
			Column c = new Column(id, name, type);
			c.setAutoIncrement(true);
			c.setPrimaryKey(true);
			this.primaryKey = c;
			columns.add(c);
		}
	}

	protected void addColumn(int id, String name, String type, 
			String foreignTable) throws DBException {
		if(name == null || type == null || name.isEmpty()  || type.isEmpty() || id < 0 ||
				foreignTable == null || foreignTable.isEmpty()){
			throw new DBException();
		}
		else {
			Column c = new Column(id, name, type);
			c.setForeignKeyTable(foreignTable);
			foreignKey.add(c);
			columns.add(c);
		}
	}

	protected void addColumn(int id, String name, String type, 
			String foreignTable, boolean auto) throws DBException {
		if(name == null || type == null || name.isEmpty()  || type.isEmpty() || id < 0 ||
				foreignTable == null || foreignTable.isEmpty()){
			throw new DBException();
		}
		else {
			Column c = new Column(id, name, type);
			c.setForeignKeyTable(foreignTable);
			foreignKey.add(c);
			columns.add(c);
		}
	}



	public Column getColumn(int id){
		for(Column c: columns){
			if(c.getId() == id){
				return c;
			}
		}
		return null;
	}



	// basic implementation of Table Interface method
	public String getSelectQ(List<Integer> columns) throws DBException {
		String sql;
		sql = "SELECT ";
		sql += getCSColumns(columns);
		sql += "\nFROM " +" " + getName();
		if(primaryKey != null){
			sql +=	"\nORDER BY " + primaryKey.getName() ;
		}
		return sql;
	}

	public String getSelectQ() throws DBException {
		ArrayList<Integer> columns = getColumnIdArrayList();
		return getSelectQ(columns); 
	}

	public String getSelectQ(List<Integer> columns, int columnId, Object value) throws DBException {
		String sql = null;
		sql = "SELECT ";
		sql += getCSColumns(columns);
		sql += "\nFROM " +" " + getName();
		sql += "\nWHERE ";
		Column c= getColumn(columnId);
		sql += " " + c.getName() + " = ";
		sql += parse(c,value);
		if(primaryKey != null){
			sql +=	"\nORDER BY " + primaryKey.getName() ;
		}
		return sql;
	}

	public String getSelectQ(int columnId, Object value) throws DBException {
		ArrayList<Integer> columnList = getColumnIdArrayList();
		return getSelectQ(columnList, columnId, value);
	}
	public String getInsertQ(List<Integer> columns, List<Object> values) throws DBException {
		String sql = "";
		sql += "INSERT INTO " + getName() + "(";
		if(values.size() == columns.size()){
			sql += getCSColumns(columns) +" )";
			sql += " \nVALUES(";
			for(int i=0; i<values.size(); i++){
				if(values.get(i) == null ){
					throw new DBException();
				}
				else{
					Column c = getColumn(columns.get(i));
					sql += parse(c,values.get(i));
					if(i<values.size() -1){
						sql += ", ";
					}
				}
			}
			sql += " )";
		}
		else{
			throw new DBException();
		}

		return sql;
	}

	public String getInsertQ(List<Object> values) throws DBException {
		String sql = "";
		List<Integer> columnIdArray = getColumnIdArrayList();
		sql = getInsertQ(columnIdArray,values);
		return sql;
	}


	public String getUpdateQ(List<Integer> columns, List<Object> values, int columnId, Object value) 
			throws DBException {
		String sql = "";
		sql += "UPDATE " + getName() + " SET ";
		if(columns.size() == values.size()){
			for(int i=0; i<columns.size(); i++){
				Column c = getColumn(columns.get(i));
				if(c == null || values.get(i) == null ){
					throw new DBException();
				}
				else{
					if( i != columnId){
						sql += "\n"+ c.getName() +" = ";
						sql += parse(c, values.get(i));
						if(i < columns.size() - 1){
							sql += ", ";
						}
					}
				}
			}
			sql += "\nWHERE ";
			Column c = getColumn(columnId);
			if(c == null || value == null ){
				throw new DBException();
			}
			else{
				sql += c.getName() + " = " + parse(c,value);				
			}
		}
		return sql;
	}

	public String getDeleteQ(int columnId, Object value) throws DBException {
		String sql = "";
		Column c = getColumn(columnId);
		sql += "DELETE FROM " + getName() +" \n";
		sql += "WHERE " + c.getName() + " = "+ parse(c,value)+ " ";
		return sql;
	}

	// protected method comma separated string with column names
	private String getCSColumns(List<Integer> columns) throws DBException {
		String result = "";
		for (int i: columns){
			Column c = getColumn(i);
			if( c == null ){
				throw new DBException();
			}
			else{
				result += c.getName() +", ";
			}
		}
		return result.substring(0,result.length()-2);
	}

	// protected method to parse value
	protected String parse(Column column, Object value) throws DBException {
		String result = "";
		if(column != null && value != null){
			//			result = " " + column.getName();
			try{
				if(column.getType().equals("INT")){
					if(value instanceof Integer){
						result += " " + value;
					}
					else{
						throw new IllegalArgumentException("Illegal Argument provided for Column" 
								+ column.getName());
					}

				} else if(column.getType().equals("DOUBLE")){
					if(value instanceof Double){
						result += " " + value;
					}
					else{
						throw new IllegalArgumentException("Illegal Argument provided for Column" 
								+ column.getName());
					}

				} else if(column.getType().equals("TIMESTAMP")){
					if(value instanceof Timestamp){
						result += " " + "\'"+value+"\'";
					}
					else{
						throw new IllegalArgumentException("Illegal Argument provided for Column" 
								+ column.getName());
					}
				} else if(column.getType().equals("BOOLEAN")){
					if(value instanceof Boolean){
						result += " " +value;
					}
					else{
						throw new IllegalArgumentException("Illegal Argument provided for Column" 
								+ column.getName());
					}
				} else if(column.getType().substring(0,7).equals("VARCHAR")){
					if(value instanceof String){
						result += " " + "\'"+value+"\'";
					}
					else{
						throw new IllegalArgumentException("Illegal Argument provided for Column" 
								+ column.getName());
					}
				}
			}
			catch(IllegalArgumentException e){
				System.out.println(e);
			}
		}
		else {
			throw new DBException();
		}
		return result;
	}


	//	private String checkAndAppend(Object value, Types type){
	//		String result = "";
	//		if(value == null|| type == null ){
	//			throw new DBException();
	//		}
	//		else{
	//			if()
	//		}
	//	}


	// private method -comma separated string with column names and values
}