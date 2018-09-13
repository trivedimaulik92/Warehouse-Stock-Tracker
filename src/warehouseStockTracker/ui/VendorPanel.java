
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

import warehouseStockTracker.Business.Vendor;
import warehouseStockTracker.db.DBException;
import warehouseStockTracker.db.VendorTable;

@SuppressWarnings("serial")
public class VendorPanel extends JPanel {

	private JTable vendorTable;
	private VendorTableModel vendorTableModel;
	private VendorTable vendorTableDB = new VendorTable("Vendor");

	public VendorPanel() {
		super();
		this.setLayout(new BorderLayout());
		vendorTableModel = new VendorTableModel();
		vendorTable = buildVendorTable(vendorTableModel);
		add(new JScrollPane(vendorTable), BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(buildVendorButtonPanel(), BorderLayout.WEST);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	JPanel buildVendorButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		JButton addButton = new JButton("New Vendor");
		addButton.setToolTipText("Add vendor");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAddButton();
			}
		});
		panel.add(addButton);

		JButton editButton = new JButton("Update Vendor");
		editButton.setToolTipText("Edit selected vendor");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEditButton();
			}
		});
		panel.add(editButton);

		JButton deleteButton = new JButton("Delete Vendor");
		deleteButton.setToolTipText("Delete selected vendor");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDeleteButton();
			}
		});
		panel.add(deleteButton);

		return panel;
	}

	// Add Button method
	private void doAddButton() {
		VendorForm vendorForm = new VendorForm((Frame) this.getParent().getParent().getParent().getParent().getParent(),
				"New Vendor", true);
		vendorForm.setLocationRelativeTo(this);
		vendorForm.setVisible(true);
		fireDatabaseUpdatedEvent();
	}

	// Edit Button method
	private void doEditButton() {
		int selectedRow = vendorTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "No vendor is currently selected.", "No vendor selected",
					JOptionPane.ERROR_MESSAGE);
		} else {
			Vendor vendor = vendorTableModel.getVendor(selectedRow);
			VendorForm vendorForm = new VendorForm(
					(Frame) this.getParent().getParent().getParent().getParent().getParent(), "Edit Vendor", true,
					vendor);
			vendorForm.setLocationRelativeTo(this);
			vendorForm.setVisible(true);
			fireDatabaseUpdatedEvent();
		}

	}

	// Delete Button method
	private void doDeleteButton() {
		int selectedRow = vendorTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "No vendor is currently selected.", "No vendor selected",
					JOptionPane.ERROR_MESSAGE);
		} else {
			Vendor vendor = vendorTableModel.getVendor(selectedRow);
			int option = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete vendor " + vendor.getName() + " from the database?",
					"Confirm delete", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				try {
					vendorTableDB.deleteVendor(vendor);
					vendorTableModel.databaseUpdated();
					fireDatabaseUpdatedEvent();
				} catch (DBException e) {
					System.out.println(e);
				}
			}
		}
	}

	// validate selected row
	private boolean validateRow(int selectedRow) {
		boolean check = false;
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "No vendor is currently selected.", "No vendor selected",
					JOptionPane.ERROR_MESSAGE);
		}
		return check;
	}

	// build Vendor Table method
	JTable buildVendorTable(VendorTableModel vendorTableModel) {
		JTable table = new JTable(vendorTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(null);
		return table;
	}

	// refresh table on database update method
	void fireDatabaseUpdatedEvent() {
		vendorTableModel.databaseUpdated();
	}
}