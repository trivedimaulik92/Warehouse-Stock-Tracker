

package warehouseStockTracker.ui;

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class WSTFrame extends JFrame {
    private JTabbedPane tabbedPane = new JTabbedPane();
    private ProductPanel productPanel = new ProductPanel();
    private StockActivityPanel stockActivityPanel = new StockActivityPanel();
    private VendorPanel vendorPanel = new VendorPanel();
    
    public WSTFrame() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }        
        setTitle("WareHouse Stock Tracker");
        setSize(1200, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        tabbedPane.add(productPanel, "Product");
        tabbedPane.add(stockActivityPanel, "Stock Activity");
        tabbedPane.add(vendorPanel, "Vendor");
  
        getContentPane().add(tabbedPane);
        setVisible(true);        
    }
    

    void fireDatabaseUpdatedEvent(String tableName) {
    	if(tableName!= null && !(tableName.isEmpty())){
    		switch(tableName){
    		case "Product":
    			productPanel.fireDatabaseUpdatedEvent();
    			break;
    		case "StockActivity":
    			stockActivityPanel.fireDatabaseUpdatedEvent();
    			break;
    		case "Vendor":
    			vendorPanel.fireDatabaseUpdatedEvent();
    		}
    	}
        
    }   
}