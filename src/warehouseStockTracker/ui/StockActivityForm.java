
package warehouseStockTracker.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import warehouseStockTracker.Business.InvalidStockOpException;
import warehouseStockTracker.Business.ProductStockItem;
import warehouseStockTracker.Business.Stock;
import warehouseStockTracker.Business.StockInterface;
import warehouseStockTracker.Business.TransactionRecord;
import warehouseStockTracker.Business.TransactionType;
import warehouseStockTracker.Business.Vendor;
import warehouseStockTracker.db.DBException;
import warehouseStockTracker.db.ProductHandler;
import warehouseStockTracker.db.TransactionHandler;


@SuppressWarnings("serial")
public class StockActivityForm extends JDialog {
	// declaring variables

	//	private JTextField idField;
	private JTextField productNameField;
	private JTextField vendorNameField;
	private JTextField typeField;
	//    private JTextField averagePriceField;
	private JTextField quantityField;
	private JTextField capacityField;
	private JTextField amountField;
	//    private JTextField vendorNameField;
	private JButton confirmButton;
	private JButton cancelButton;

	private TransactionRecord record = new TransactionRecord();
	private ProductStockItem product = new ProductStockItem();
	private TransactionHandler transactionHandler = TransactionHandler.getInstance();
	private ProductHandler productHandler = ProductHandler.getInstance();

	//    public StockActivityForm(java.awt.Frame parent, String title, boolean modal) {
	//        super(parent, title, modal);
	//        initComponents();
	//    }

	public StockActivityForm(java.awt.Frame parent, String title,
			boolean modal, TransactionRecord record) {
		super(parent, title, modal);   
		initComponents();
		this.record = record;

		try {
			//			Stock stock = StockDBOld.getByStockId(record.getStockId());
			//			Product p =  ProductDB.getByStock(stock);
			product = productHandler.getByStockId(record.getStockId());

			//			product.setId(p.getId());
			//			product.setName(p.getName());
			//			product.setType(p.getType());
			//			product.setBrand(p.getBrand());
			//			product.setModel(p.getModel());
			//			product.setAveragePrice(p.getAveragePrice());
			//			product.setStockId(stock.getId());
			//			product.setQuantity(stock.getQuantity());
			//			product.setStockCap(stock.getCapacity());

		} catch (DBException e) {
			System.out.println(e);
		}
		//        confirmButton.setText("Save");
		//        idField.setText(Integer.toString(record.getId()));
		productNameField.setText(record.getProductName());
		vendorNameField.setText(record.getVendorName());
		typeField.setText(record.getType().toString());
		amountField.setText(Integer.toString(record.getQuantity()));
		quantityField.setText(Integer.toString(product.getQuantity()));
		capacityField.setText(Integer.toString(product.getCapacity()));
		String typeString = record.getType().toString();

		if(!(typeString.substring(0,6).equals("revert"))){
			typeString = typeString.substring(0,5) + " " + typeString.substring(6);
			if(!record.getType().toString().equals("stock_take")){
				vendorNameField.setEditable(true);	
			}
			amountField.setEditable(true);
		}else{
			typeString = typeString.substring(0,6);
		}
		confirmButton.setText(typeString);
	}

	//    public StockActivityForm(java.awt.Frame parent, String title,
	//            boolean modal, ProductStockItem product) {
	//        super(parent, title, modal);   
	//        initComponents();
	//        this.product = product;
	////        confirmButton.setText("Save");
	////        idField.setText(Integer.toString(record.getId()));
	//        productNameField.setText(record.getProductName());
	//        vendorNameField.setText(record.getVendorName());
	//        typeField.setText(record.getType());
	//        quantityField.setText(Integer.toString(product.getQuantity()));
	//        capacityField.setText(Integer.toString(product.getCapacity()));
	//    }


	private void initComponents() {
		productNameField = new JTextField();
		vendorNameField = new JTextField();
		typeField = new JTextField();
		//        idField = new JTextField();
		amountField = new JTextField();
		//        averagePriceField = new JTextField();
		quantityField = new JTextField();
		capacityField = new JTextField();
		cancelButton = new JButton();
		confirmButton = new JButton();
		productNameField.setEditable(false);
		vendorNameField.setEditable(false);
		typeField.setEditable(false);
		//        idField.setEditable(false);
		amountField.setEditable(false);
		quantityField.setEditable(false);
		capacityField.setEditable(false);


		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);     

		Dimension shortField = new Dimension(100, 20);
		Dimension longField = new Dimension(300, 20);
		productNameField.setPreferredSize(shortField);
		productNameField.setMinimumSize(shortField);        
		vendorNameField.setPreferredSize(shortField);
		vendorNameField.setMinimumSize(shortField);        
		typeField.setPreferredSize(longField);
		typeField.setMinimumSize(longField);
		//        idField.setPreferredSize(shortField);
		//        idField.setMinimumSize(shortField);   
		amountField.setPreferredSize(shortField);
		amountField.setMinimumSize(shortField);   
		capacityField.setPreferredSize(shortField);
		capacityField.setMinimumSize(shortField); 
		quantityField.setPreferredSize(shortField);
		quantityField.setMinimumSize(shortField);

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cancelButtonActionPerformed();
			}
		});

		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				confirmButtonActionPerformed();
			}
		});

		// JLabel and JTextField panel
		JPanel TransactionPanel = new JPanel();
		TransactionPanel.setLayout(new GridBagLayout());
		//        TransactionPanel.add(new JLabel("Transaction Id:"), 
		//                getConstraints(0, 0, GridBagConstraints.LINE_END));
		//        TransactionPanel.add(idField,
		//                getConstraints(1, 0, GridBagConstraints.LINE_START));
		TransactionPanel.add(new JLabel("Product Name:"), 
				getConstraints(0, 0, GridBagConstraints.LINE_END));
		TransactionPanel.add(productNameField, 
				getConstraints(1, 0, GridBagConstraints.LINE_START));
		TransactionPanel.add(new JLabel("Vendor Name:"), 
				getConstraints(0, 1, GridBagConstraints.LINE_END));
		TransactionPanel.add(vendorNameField, 
				getConstraints(1, 1, GridBagConstraints.LINE_START));
		TransactionPanel.add(new JLabel("Transaction Type:"), 
				getConstraints(0, 2, GridBagConstraints.LINE_END));
		TransactionPanel.add(typeField, 
				getConstraints(1, 2, GridBagConstraints.LINE_START));
		TransactionPanel.add(new JLabel("Volume :"), 
				getConstraints(0, 3, GridBagConstraints.LINE_END));
		TransactionPanel.add(amountField, 
				getConstraints(1, 3, GridBagConstraints.LINE_START));
		TransactionPanel.add(new JLabel("Quantity:"), 
				getConstraints(0, 4, GridBagConstraints.LINE_END));
		TransactionPanel.add(quantityField, 
				getConstraints(1, 4, GridBagConstraints.LINE_START));
		TransactionPanel.add(new JLabel("Stock Capacity:"), 
				getConstraints(0, 5, GridBagConstraints.LINE_END));
		TransactionPanel.add(capacityField, 
				getConstraints(1, 5, GridBagConstraints.LINE_START));

		// JButton panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		// add panels to main panel
		setLayout(new BorderLayout());
		add(TransactionPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		pack();        
	}

	private GridBagConstraints getConstraints(int x, int y, int anchor) {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 0, 5);
		c.gridx = x;
		c.gridy = y;
		c.anchor = anchor;
		return c;
	}

	private void cancelButtonActionPerformed() {                                             
		dispose();
	}                                            

	private void confirmButtonActionPerformed() {
		try {
			switch (record.getType()){
			case stock_in:
			{
				Integer amount = validateAmount();
				Vendor vendor = validateVendorName();
				if(amount != null && vendor != null ){
					record.setQuantity(amount);
					doStockIn(vendor);
				}
				break;}
			case stock_out:{
				Integer amount = validateAmount();
				Vendor vendor = validateVendorName();
				if(amount != null && vendor != null ){
					record.setQuantity(amount);
					doStockOut(vendor);
				}
				break;}
			case stock_take:{
				Integer amount = validateAmount();
				if(amount != null ){
					record.setQuantity(amount);
					doStockTake();
				}
				break;}
			case revert_stock_in:
			case revert_stock_out:
			case revert_stock_take:
				doRevert();
				break;
			}	
		}
		catch(InvalidStockOpException e){
			JOptionPane.showMessageDialog(this,
					e.getMessage(),
					"Invalid Stock Operation",
					JOptionPane.INFORMATION_MESSAGE);
			amountField.requestFocusInWindow();
			return;
		}
	}


	// validate amount field
	private Integer validateAmount() {

		String amountString = amountField.getText();

		if (amountString == null ||  amountString.isEmpty() ){
			JOptionPane.showMessageDialog(this, "Please fill in Volume field.",
					"Missing Volume", JOptionPane.INFORMATION_MESSAGE);
			amountField.requestFocusInWindow();
			return null;
		} else {
			try {
				if(Integer.parseInt(amountString) <= 0){
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this,
						"The data entered in the Volume field is invalid",
						"Invalid Volume",
						JOptionPane.INFORMATION_MESSAGE);
				amountField.requestFocusInWindow();
				return null;
			}
		}
		return Integer.parseInt(amountString);
	}


	// validate vendor name field
	private Vendor validateVendorName(){
		String vendorName = vendorNameField.getText();
		if (vendorName == null || vendorName.isEmpty() ){
			JOptionPane.showMessageDialog(this, "Please fill in Vendor Name field.",
					"Missing Vendor Name", JOptionPane.INFORMATION_MESSAGE);
			vendorNameField.requestFocusInWindow();
			return null;
		}
		else {
			Vendor vendor = null;
			try {
				vendor = transactionHandler.getVendorByName(vendorName);
				if(vendor == null){
					throw new DBException();
				}
			} catch (DBException e) {
				JOptionPane.showMessageDialog(this,
						"Vendor not found. Enter valid Vendor Name.",
						"Invalid Vendor Name",
						JOptionPane.INFORMATION_MESSAGE);
				vendorNameField.requestFocusInWindow();
				return null;
			}
			return vendor;
		}
	}

	// set amount value 
	@SuppressWarnings("unused")
	private void setAmount() {
		String vendorName = vendorNameField.getText();
		int amount = Integer.parseInt(amountField.getText());
		record.setQuantity(amount);
		Vendor vendor = null;
		try {
			vendor = transactionHandler.getVendorByName(vendorName);

		} catch (DBException e) {
			System.out.println(e);
		}
		if(vendor != null){
			record.setVendorId(vendor.getId());
		}
	}

	// set vendor name
	@SuppressWarnings("unused")
	private void setVendorName(){
		String vendorName = vendorNameField.getText();
	}

	// private stock In
	private void doStockIn(Vendor vendor) throws InvalidStockOpException {
		try {
			transactionHandler.stockIn( product, vendor, record.getQuantity());
			dispose();
			fireDatabaseUpdatedEvent();
		} catch (DBException e) {
			System.out.println(e);
		}
	}

	// private stock Out
	private void doStockOut(Vendor vendor) throws InvalidStockOpException {
		try {
			transactionHandler.stockOut(product, vendor, record.getQuantity());
			dispose();
			fireDatabaseUpdatedEvent();
		} catch (DBException e) {
			System.out.println(e);
		}
	}

	// private stock Take method
	private void doStockTake() throws InvalidStockOpException {
		try {
			transactionHandler.stockTake(product, record.getQuantity());
			dispose();
			fireDatabaseUpdatedEvent();
		} catch (DBException e) {
			System.out.println(e);
		}
	}

	// private revert method
	private void doRevert() throws InvalidStockOpException{
		try{
			record.setType(TransactionType.valueOf((record.getType().toString().substring(7))));
			transactionHandler.revert(record);
			dispose();
			fireDatabaseUpdatedEvent();
		} catch(DBException e) {
			System.out.println(e);
		}
	}

	// private refresh database update method
	private void fireDatabaseUpdatedEvent() {
		((WSTFrame) getParent()).fireDatabaseUpdatedEvent("StockActivity");
		((WSTFrame) getParent()).fireDatabaseUpdatedEvent("Product");
	}


}