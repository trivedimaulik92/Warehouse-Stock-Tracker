

package warehouseStockTracker.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import warehouseStockTracker.Business.ProductStockItem;
import warehouseStockTracker.Business.TransactionRecord;
import warehouseStockTracker.Business.TransactionType;
import warehouseStockTracker.db.DBException;
import warehouseStockTracker.db.ProductHandler;

@SuppressWarnings("serial")
public class ProductPanel extends JPanel {

	private JTable productTable;
	private ProductTableModel productTableModel;

	
	public ProductPanel(){
		super();
		this.setLayout(new BorderLayout());
		productTableModel = new ProductTableModel();
		productTable = buildProductTable(productTableModel);
		add(new JScrollPane(productTable), BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(buildProductButtonPanel(), BorderLayout.WEST);
		buttonPanel.add(buildStockButtonPanel(), BorderLayout.EAST);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	// get Product Panel
//	public JPanel getPanel(){
//		ProductPanel productPanel = new ProductPanel();
//		productPanel.add(new JScrollPane(buildProductTable()), BorderLayout.CENTER);
//		JPanel ButtonPanel = new JPanel();
//		ButtonPanel.add(buildProductButtonPanel(), BorderLayout.WEST);
//		ButtonPanel.add(buildStockButtonPanel(), BorderLayout.EAST);
//		productPanel.add(buildProductButtonPanel(), BorderLayout.SOUTH);
//		return productPanel;
//	}

	// build productButtonPanel
	JPanel buildProductButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		// panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JButton addButton = new JButton("New Product");
		addButton.setToolTipText("Add product");
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				doAddButton();
			}
		});
		panel.add(addButton);

		JButton editButton = new JButton("Update Product");
		editButton.setToolTipText("Edit selected product");
		editButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				doEditButton();
			}
		});
		panel.add(editButton);

		JButton deleteButton = new JButton("Delete Product");
		deleteButton.setToolTipText("Delete selected product");
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				doDeleteButton();
			}
		});
		panel.add(deleteButton);


		return panel;
	}

	// build Stock Button Panel
	JPanel buildStockButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());

		JButton stockInButton = new JButton("Stock In");
		stockInButton.setToolTipText("Add to Stock");
//		stockInButton.setEnabled(false);
		stockInButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				doStockInButton();
			}
		});
		panel.add(stockInButton);

		JButton stockOutButton = new JButton("Stock Out");
		stockOutButton.setToolTipText("Remove from Stock");
//		stockOutButton.setEnabled(false);
		stockOutButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				doStockOutButton();
			}
		});
		panel.add(stockOutButton);

		JButton stockTakeButton = new JButton("Stock Take");
		stockTakeButton.setToolTipText("Record Lost/Damaged Stock");
//		stockTakeButton.setEnabled(false);
		stockTakeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				doStockTakeButton();
			}
		});
		panel.add(stockTakeButton);

		return panel;
	}

	// Add Button method
	private  void doAddButton() {
		ProductForm productForm = 
		new ProductForm((Frame)this.getParent().getParent().getParent().getParent().getParent(),
						"New Product", true);
		productForm.setLocationRelativeTo(this);
		productForm.setVisible(true);
	}

	// Edit Button method
	private  void doEditButton() {
		int selectedRow = productTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this,
					"No product is currently selected.", 
					"No product selected", JOptionPane.ERROR_MESSAGE);
		} else {
			ProductStockItem product = productTableModel.getProduct(selectedRow);
			ProductForm productForm = 
			new ProductForm((Frame)this.getParent().getParent().getParent().getParent().getParent(),
							"Edit Product", true, product);
			productForm.setLocationRelativeTo(this);
			productForm.setVisible(true);
			fireDatabaseUpdatedEvent();
		}

	}

	// Delete Button method
	private  void doDeleteButton() {
		int selectedRow = productTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this,
					"No product is currently selected.", 
					"No product selected", JOptionPane.ERROR_MESSAGE);
		} else {
			ProductStockItem product = productTableModel.getProduct(selectedRow);
			int capacity = product.getCapacity();
			int quantity = product.getCurrentStock();
			if(capacity != 0){
				if(quantity != 0){
					int option = JOptionPane.showConfirmDialog(this,
							"Product has " + quantity + " amount of stock currently avalialbe. \n" +
									"Are you sure you want to discard stock and delete product " + 
									product.getName() + " from the database?",
									"Confirm delete", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						try {                    
							ProductHandler.getInstance().deleteProduct(product);
							productTableModel.databaseUpdated();
							return;
						} catch (DBException e) {
							System.out.println(e);
						}
					}
				}
			}
			int option = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete " + 
							product.getName() + " from the database?",
							"Confirm delete", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				try {                    
					ProductHandler.getInstance().deleteProduct(product);
					productTableModel.databaseUpdated();
					return;
				} catch (DBException e) {
					System.out.println(e);
				}
			}
		}
	}

	// stock In method
	private void doStockInButton(){
		int selectedRow = productTable.getSelectedRow();
		if(validateRow(selectedRow)){
			ProductStockItem product = productTableModel.getProduct(selectedRow);
			TransactionRecord record = new TransactionRecord();
			record.setProductName(product.getName());
			record.setStockId(product.getStockId());
			record.setType(TransactionType.valueOf("stock_in"));
			StockActivityForm stockActivityForm = 
					new StockActivityForm((Frame)this.getParent().getParent().getParent().getParent().getParent(),
					"Stock In", true, record);
			stockActivityForm.setLocationRelativeTo(this);
			stockActivityForm.setVisible(true);
			fireDatabaseUpdatedEvent();
		}
	}
	
	// stock Out method
	private void doStockOutButton(){
		int selectedRow = productTable.getSelectedRow();
		if(validateRow(selectedRow)){
			ProductStockItem product = productTableModel.getProduct(selectedRow);
			TransactionRecord record = new TransactionRecord();
			record.setProductName(product.getName());
			record.setStockId(product.getStockId());
			record.setType(TransactionType.valueOf("stock_out"));
			StockActivityForm stockActivityForm = 
					new StockActivityForm((Frame)this.getParent().getParent().getParent().getParent().getParent(),
							"Stock Out", true, record);
			stockActivityForm.setLocationRelativeTo(this);
			stockActivityForm.setVisible(true);
			fireDatabaseUpdatedEvent();
		}
	}
	
	// stock take method
	private void doStockTakeButton(){
		int selectedRow = productTable.getSelectedRow();
		if(validateRow(selectedRow)){
			ProductStockItem product = productTableModel.getProduct(selectedRow);
			TransactionRecord record = new TransactionRecord();
			record.setProductName(product.getName());
			record.setStockId(product.getStockId());
			record.setType(TransactionType.valueOf("stock_take"));
			StockActivityForm stockActivityForm = 
				new StockActivityForm((Frame)this.getParent().getParent().getParent().getParent().getParent(),
						"Stock Take", true, record);
			stockActivityForm.setLocationRelativeTo(this);
			stockActivityForm.setVisible(true);
			fireDatabaseUpdatedEvent();
		}
	}
	// validate selected row
	private boolean validateRow(int selectedRow){
		boolean check = false;
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this,
					"No product is currently selected.", 
					"No product selected", JOptionPane.ERROR_MESSAGE);
		} 
		else {
			ProductStockItem product = productTableModel.getProduct(selectedRow);
			if(product.getCapacity() <= 0){
				JOptionPane.showMessageDialog(this,
						"Invalid Product is currently selected. Select Product with stock capacity.", 
						"Invalid Product selected", JOptionPane.ERROR_MESSAGE);
			}
			else{
				check = true;
			}
		}
		return check;
	}
	
	// build Product Table method
	JTable buildProductTable(ProductTableModel productTableModel) {
		JTable table = new JTable(productTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(null);
		return table;
	}

	// refresh table on database update method 
	void fireDatabaseUpdatedEvent() {
		productTableModel.databaseUpdated();
	}
}