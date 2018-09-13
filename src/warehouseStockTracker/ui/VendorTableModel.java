package warehouseStockTracker.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import warehouseStockTracker.Business.Vendor;
import warehouseStockTracker.db.DBException;
import warehouseStockTracker.db.VendorTable;

@SuppressWarnings("serial")
public class VendorTableModel extends AbstractTableModel {
    private List<Vendor> vendorStockItems;
    private VendorTable vendorTable = new VendorTable("Vendor");
    private final String[] COLUMN_NAMES = {"Name", "Contact Name", "Address", "Phone", 
    										"Email"};
 
    public VendorTableModel() {
        try {
        	vendorStockItems = vendorTable.getVendors();
        } catch (DBException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public int getRowCount() {
        return vendorStockItems.size();
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
                return vendorStockItems.get(rowIndex).getName();
            case 1:
                return vendorStockItems.get(rowIndex).getContactName();
            case 2:
                return vendorStockItems.get(rowIndex).getContactAddress();
            case 3:
                return vendorStockItems.get(rowIndex).getContactPhone();
            case 4:
                return vendorStockItems.get(rowIndex).getContactEmail();
                    
            default:
                return null;
        }
    }   
    
    Vendor getVendor(int rowIndex) {
        return vendorStockItems.get(rowIndex);
    }
    
    void databaseUpdated() {
        try {
        	vendorStockItems = vendorTable.getVendors();
            fireTableDataChanged();
        } catch (DBException e) {
            System.out.println(e);
        }        
    }    
}