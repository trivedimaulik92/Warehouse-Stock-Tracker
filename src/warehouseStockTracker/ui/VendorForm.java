

package warehouseStockTracker.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import warehouseStockTracker.Business.Vendor;
import warehouseStockTracker.db.DBException;
import warehouseStockTracker.db.VendorTable;
import warehouseStockTracker.ui.ProductPanel;


@SuppressWarnings("serial")
public class VendorForm extends JDialog {
	private JTextField nameField;
	private JTextField contactNameField;
	private JTextField addressField;
	private JTextField phoneField;
	private JTextField emailField;
	private JButton confirmButton;
	private JButton cancelButton;

	private Vendor vendor = new Vendor();
	private VendorTable vendorTable = new VendorTable("Vendor");

	public VendorForm(java.awt.Frame parent, String title, boolean modal) {
		super(parent, title, modal);
		initComponents();
	}

	public VendorForm(java.awt.Frame parent, String title,
			boolean modal, Vendor vendor) {
		this(parent, title, modal);        
		this.vendor = vendor;
		confirmButton.setText("Save");
		nameField.setText(vendor.getName());
		contactNameField.setText(vendor.getContactName());
		addressField.setText(vendor.getContactAddress());
		phoneField.setText(vendor.getContactPhone());
		emailField.setText(vendor.getContactEmail());
	}

	private void initComponents() {
		nameField = new JTextField();
		contactNameField = new JTextField();
		addressField = new JTextField();
		phoneField = new JTextField();
		emailField = new JTextField();
		cancelButton = new JButton();
		confirmButton = new JButton();


		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);     

		Dimension shortField = new Dimension(100, 20);
		Dimension longField = new Dimension(300, 20);
		nameField.setPreferredSize(shortField);
		nameField.setMinimumSize(shortField);        
		contactNameField.setPreferredSize(shortField);
		contactNameField.setMinimumSize(shortField);
		addressField.setPreferredSize(longField);
		addressField.setMinimumSize(longField);
		phoneField.setPreferredSize(shortField);
		phoneField.setMinimumSize(shortField);   
		emailField.setPreferredSize(shortField);
		emailField.setMinimumSize(shortField);  

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
		JPanel vendorPanel = new JPanel();
		vendorPanel.setLayout(new GridBagLayout());
		vendorPanel.add(new JLabel("Name:"), 
				getConstraints(0, 0, GridBagConstraints.LINE_END));
		vendorPanel.add(nameField,
				getConstraints(1, 0, GridBagConstraints.LINE_START));
		vendorPanel.add(new JLabel("Contact Name:"), 
				getConstraints(0, 1, GridBagConstraints.LINE_END));
		vendorPanel.add(contactNameField, 
				getConstraints(1, 1, GridBagConstraints.LINE_START));
		vendorPanel.add(new JLabel("Address:"), 
				getConstraints(0, 2, GridBagConstraints.LINE_END));
		vendorPanel.add(addressField, 
				getConstraints(1, 2, GridBagConstraints.LINE_START));
		vendorPanel.add(new JLabel("Phone:"), 
				getConstraints(0, 3, GridBagConstraints.LINE_END));
		vendorPanel.add(phoneField, 
				getConstraints(1, 3, GridBagConstraints.LINE_START));
		vendorPanel.add(new JLabel("Email:"), 
				getConstraints(0, 4, GridBagConstraints.LINE_END));
		vendorPanel.add(emailField, 
				getConstraints(1, 4, GridBagConstraints.LINE_START));
		

		// JButton panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);

		// add panels to main panel
		setLayout(new BorderLayout());
		add(vendorPanel, BorderLayout.CENTER);
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
		String address = addressField.getText();
		String contactname = contactNameField.getText();
		String email = emailField.getText();
		String phone = phoneField.getText();
		//        String quantityString = quantityField.getText();
		//        String capacityString = capacityField.getText();
		if (name == null || phone == null || email == null || contactname == null ||address == null ||
						name.isEmpty() || phone.isEmpty() || contactname.isEmpty() || email.isEmpty() ||
				address.isEmpty()){
			JOptionPane.showMessageDialog(this, "Please fill in all fields.",
					"Missing Fields", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} 
		else {
			try {
				if(Long.parseLong(phone) <= 0){
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this,
						"The data entered in the Phone field is invalid",
						"Invalid Average Price",
						JOptionPane.INFORMATION_MESSAGE);
				phoneField.requestFocusInWindow();
				return false;
			}

		} 

		return true;
	}

	private void setData() {
		String name = nameField.getText();
		String phone = phoneField.getText();
		String contactname = contactNameField.getText();
		String email = emailField.getText();
		String address = addressField.getText();

		
		vendor.setName(name);
		vendor.setContactAddress(address);
		vendor.setContactEmail(email);
		vendor.setContactName(contactname);
		vendor.setContactPhone(phone);

	}

	private void doEdit() {
		try {
			vendorTable.updateVendor(vendor);
			dispose();
			fireDatabaseUpdatedEvent();
		} catch (DBException e) {
			System.out.println(e);
		}
	}

	private void doAdd() {
		try {
			vendorTable.addVendor(vendor);
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