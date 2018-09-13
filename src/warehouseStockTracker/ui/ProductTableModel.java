package warehouseStockTracker.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import warehouseStockTracker.Business.Product;
import warehouseStockTracker.Business.ProductStockItem;
import warehouseStockTracker.db.ProductHandler;
import warehouseStockTracker.db.DBException;

@SuppressWarnings("serial")
public class ProductTableModel extends AbstractTableModel {
    private List<ProductStockItem> productStockItems;
    private final String[] COLUMN_NAMES = {"Name", "Type", "Model", "Brand", 
    										"Average Price", "Quantity", "StockCap"};
 
    public ProductTableModel() {
        try {
            productStockItems = ProductHandler.getInstance().getAll();
        } catch (DBException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public int getRowCount() {
        return productStockItems.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            
            case 0:
                return productStockItems.get(rowIndex).getName();
            case 1:
                return productStockItems.get(rowIndex).getType();
            case 2:
                return productStockItems.get(rowIndex).getBrand();
            case 3:
                return productStockItems.get(rowIndex).getModel();
            case 4:
                return productStockItems.get(rowIndex).getAveragePrice();
            case 5:
                return productStockItems.get(rowIndex).getQuantity();
            case 6:
                return productStockItems.get(rowIndex).getCapacity();            
            default:
                return null;
        }
    }   
    
    ProductStockItem getProduct(int rowIndex) {
        return productStockItems.get(rowIndex);
    }
    
    void databaseUpdated() {
        try {
            productStockItems = ProductHandler.getInstance().getAll();
            fireTableDataChanged();
        } catch (DBException e) {
            System.out.println(e);
        }        
    }    
}