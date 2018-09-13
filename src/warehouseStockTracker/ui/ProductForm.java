

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

import warehouseStockTracker.Business.ProductStockItem;
import warehouseStockTracker.Business.ProductType;
import warehouseStockTracker.db.DBException;
import warehouseStockTracker.db.ProductHandler;


@SuppressWarnings("serial")
public class ProductForm extends JDialog {
	private JTextField nameField;
	private JTextField typeField;
	private JTextField brandField;
	private JTextField modelField;
	private JTextField averagePriceField;
	private JTextField quantityField;
	private JTextField capacityField;
	private JButton confirmButton;
	private JButton cancelButton;

	private ProductStockItem product = new ProductStockItem();
	

	public ProductForm(java.awt.Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public ProductForm(java.awt.Frame parent, String title,
			boolean modal, ProductStockItem product) {
		this(parent, title, modal);        
		this.product = product;
		confirmButton.setText("Save");
		nameField.setText(product.getName());
		typeField.setText(product.getType().toString());
		brandField.setText(product.getBrand());
		modelField.setText(product.getModel());
		averagePriceField.setText(new Double(product.getAveragePrice()).toString());
		quantityField.setText(Integer.toString(product.getQuantity()));
		capacityField.setText(Integer.toString(product.getCapacity()));
	}

	private void initComponents() {
		nameField = new JTextField();
		typeField = new JTextField();
		brandField = new JTextField();
		modelField = new JTextField();
		averagePriceField = new JTextField();
		quantityField = new JTextField();
		capacityField = new JTextField();
		cancelButton = new JButton();
		confirmButton = new JButton();
		quantityField.setEditable(false);


		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);     

		Dimension shortField = new Dimension(100, 20);
		Dimension longField = new Dimension(300, 20);
		nameField.setPreferredSize(shortField);
		nameField.setMinimumSize(shortField);        
		typeField.setPreferredSize(longField);
		brandField.setMinimumSize(shortField);
		brandField.setPreferredSize(longField);
		typeField.setMinimumSize(longField);
		modelField.setPreferredSize(shortField);
		modelField.setMinimumSize(shortField);   
		averagePriceField.setPreferredSize(shortField);
		averagePriceField.setMinimumSize(shortField);  
		quantityField.setPreferredSize(shortField);
		quantityField.setMinimumSize(shortField);        
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

		confirmButton.setText("Add");
		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				confirmButtonActionPerformed();
			}
		});

		// JLabel and JTextField panel
		JPanel productPanel = new JPanel();
		productPanel.setLayout(new GridBagLayout());
		productPanel.add(new JLabel("Product Name:"), 
				getConstraints(0, 0, GridBagConstraints.LINE_END));
		productPanel.add(nameField,
				getConstraints(1, 0, GridBagConstraints.LINE_START));
		productPanel.add(new JLabel("Product Type:"), 
				getConstraints(0, 1, GridBagConstraints.LINE_END));
		productPanel.add(typeField, 
				getConstraints(1, 1, GridBagConstraints.LINE_START));
		productPanel.add(new JLabel("Brand:"), 
				getConstraints(0, 2, GridBagConstraints.LINE_END));
		productPanel.add(brandField, 
				getConstraints(1, 2, GridBagConstraints.LINE_START));
		productPanel.add(new JLabel("Model:"), 
				getConstraints(0, 3, GridBagConstraints.LINE_END));
		productPanel.add(modelField, 
				getConstraints(1, 3, GridBagConstraints.LINE_START));
		productPanel.add(new JLabel("Average Price:"), 
				getConstraints(0, 4, GridBagConstraints.LINE_END));
		productPanel.add(averagePriceField, 
				getConstraints(1, 4, GridBagConstraints.LINE_START));
		productPanel.add(new JLabel("Quantity:"), 
				getConstraints(0, 5, GridBagConstraints.LINE_END));
		productPanel.add(quantityField, 
				getConstraints(1, 5, GridBagConstraints.LINE_START));
		productPanel.add(new JLabel("Stock Capacity:"), 
				getConstraints(0, 6, GridBagConstraints.LINE_END));
		productPanel.add(capacityField, 
				getConstraints(1, 6, GridBagConstraints.LINE_START));

		// JButton panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		// add panels to main panel
		setLayout(new BorderLayout());
		add(productPanel, BorderLayout.CENTER);
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
		if (validateData()) {
			setData();
			if (confirmButton.getText().equals("Add")) {
				doAdd();
			} else {
				doEdit();
			}
		}
	}

	private boolean validateData() {
		String name = nameField.getText();
		String type = typeField.getText();
		String brand = brandField.getText();
		String model = modelField.getText();
		String capacityString = capacityField.getText();
		String averagePriceString = averagePriceField.getText();
		//        String quantityString = quantityField.getText();
		//        String capacityString = capacityField.getText();
		if (name == null || type == null || brand == null || model == null ||
				averagePriceString == null ||
				name.isEmpty() || type.isEmpty() || brand.isEmpty() || model.isEmpty() ||
				averagePriceString.isEmpty()){
			JOptionPane.showMessageDialog(this, "Please fill in all fields.",
					"Missing Fields", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} 
		else {
			try {
				if(Double.parseDouble(averagePriceString) <= 0.00){
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this,
						"The data entered in the Average Price field is invalid",
						"Invalid Average Price",
						JOptionPane.INFORMATION_MESSAGE);
				averagePriceField.requestFocusInWindow();
				return false;
			}

			try {
				if(capacityString != null && (!capacityString.isEmpty())){
					Integer.parseInt(capacityField.getText());
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this,
						"The data entered in the Capacity field is invalid",
						"Invalid Capacity",
						JOptionPane.INFORMATION_MESSAGE);
				capacityField.requestFocusInWindow();
				return false;
			}

		} 

		try{
			product.setType(ProductType.valueOf(type));
		}
		catch(IllegalArgumentException e){
			JOptionPane.showMessageDialog(this,
					"The data entered in the Product Type field is invalid",
					"Invalid Product Type",
					JOptionPane.INFORMATION_MESSAGE);
			typeField.requestFocusInWindow();
			return false;
		}
		return true;
	}

	private void setData() {
		String name = nameField.getText();
		String type = typeField.getText();
		String brand = brandField.getText();
		String model = modelField.getText();

		double averagePrice = Double.parseDouble(averagePriceField.getText());
		int capacity = 0;
		if(capacityField.getText() != null){
			capacity = Integer.parseInt(capacityField.getText());
			if(capacity <= 0){
				capacity = 0;
			}
		}
		product.setName(name);
		product.setType(ProductType.valueOf(type));
		product.setBrand(brand);
		product.setModel(model);
		product.setAveragePrice(averagePrice);
		product.setCapacity(capacity);

	}

	private void doEdit() {
		try {
			ProductHandler.getInstance().updateProduct(product);
			dispose();
			fireDatabaseUpdatedEvent();
		} catch (DBException|CloneNotSupportedException e) {
			System.out.println(e);
		}
	}

	private void doAdd() {
		try {
			ProductHandler.getInstance().createProduct(product);
			dispose();
			fireDatabaseUpdatedEvent();
		} catch(DBException e) {
			System.out.println(e);
		}
	}

	private void fireDatabaseUpdatedEvent() {
		((WSTFrame) this.getParent()).fireDatabaseUpdatedEvent("Product");
	}
}