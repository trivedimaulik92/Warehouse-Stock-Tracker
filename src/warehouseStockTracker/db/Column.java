
package warehouseStockTracker.db;

public class Column {
	
	// declaring variables
	private int id;
	private String name;
	private String type;
	private boolean isUnique;
	private boolean isPrimaryKey;
	private boolean isAutoIncrement;
	private boolean isForeignKey;
	private boolean isNotNull;
	private String foreignKeyTable;
	
	// constructors
	Column() {}
	
	public Column(int id, String name, String type){
		this();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	// getter and setter methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public boolean isForeignKey() {
		return isForeignKey;
	}

	private void setForeignKey(boolean isForeignKey) {
		this.isForeignKey = isForeignKey;
	}

	public String getForeignKeyTable() {
		return foreignKeyTable;
	}

	public void setForeignKeyTable(String foreignKeyTable) {
		this.foreignKeyTable = foreignKeyTable;
		if(foreignKeyTable != null){
			setForeignKey(true);
		}
	}

	public boolean isNotNull() {
		return isNotNull;
	}

	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	
}