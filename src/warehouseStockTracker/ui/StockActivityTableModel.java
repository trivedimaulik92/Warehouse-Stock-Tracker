
package warehouseStockTracker.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import warehouseStockTracker.Business.TransactionRecord;
import warehouseStockTracker.db.TransactionHandler;
import warehouseStockTracker.db.DBException;

@SuppressWarnings("serial")
public class StockActivityTableModel extends AbstractTableModel {
    private List<TransactionRecord> transactionRecords;
    private final String[] COLUMN_NAMES = {"Transaction ID", "Product Name", "Vendor Name", 
    												"Transaction Type", "Volume", "Time Stamp"};
 
    public StockActivityTableModel() {
        try {
            transactionRecords = TransactionHandler.getInstance().getAll();
        } catch (DBException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public int getRowCount() {
        return transactionRecords.size();
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
                return transactionRecords.get(rowIndex).getId();
            case 1:
                return transactionRecords.get(rowIndex).getProductName();
            case 2:
                return transactionRecords.get(rowIndex).getVendorName();
            case 3:
                return transactionRecords.get(rowIndex).getType();
            case 4:
                return transactionRecords.get(rowIndex).getQuantity();
            case 5:
                return transactionRecords.get(rowIndex).getDatetime();
            default:
                return null;
        }
    }   
    
    TransactionRecord getRecord(int rowIndex) {
        return transactionRecords.get(rowIndex);
    }
    
    void databaseUpdated() {
        try {
            transactionRecords = TransactionHandler.getInstance().getAll();
            fireTableDataChanged();
        } catch (DBException e) {
            System.out.println(e);
        }        
    }    
}