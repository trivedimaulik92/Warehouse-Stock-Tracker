

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

import warehouseStockTracker.Business.TransactionRecord;
import warehouseStockTracker.Business.TransactionType;
import warehouseStockTracker.db.DBException;
import warehouseStockTracker.db.TransactionHandler;

@SuppressWarnings("serial")
public class StockActivityPanel extends JPanel {

	private JTable stockActivityTable;
	private StockActivityTableModel stockActivityTableModel;

	//private constructor
	StockActivityPanel(){
		super();
		stockActivityTableModel = new StockActivityTableModel();
		stockActivityTable = buildStockActivityTable(stockActivityTableModel);
		this.setLayout(new BorderLayout());
		add(new JScrollPane(stockActivityTable), BorderLayout.CENTER);
		add(buildRevertButtonPanel(), BorderLayout.SOUTH);
	}

//	// get Product Panel
//	public JPanel getPanel(){
//		StockActivityPanel stockActivityPanel = new StockActivityPanel();
//		
//	}

	// build productButtonPanel
	private JPanel buildRevertButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		// panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JButton addButton = new JButton("Revert");
		addButton.setToolTipText("Revert transaction");
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				doRevertButton();
			}
		});
		panel.add(addButton, FlowLayout.LEFT);


		return panel;
	}

	

	// Add Button method
	private void doRevertButton() {
		int selectedRow = stockActivityTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this,
					"No transaction is currently selected.", 
					"No transaction selected", JOptionPane.ERROR_MESSAGE);
		}
		else {
			TransactionRecord record = stockActivityTableModel.getRecord(selectedRow);
			if ((record.getType().toString().startsWith("revert"))) {
				JOptionPane.showMessageDialog(this, "Please select record with Tranasction type other than 'revert'.",
						"Invalid Transaction Selected", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				try{
					if(record.getId() != TransactionHandler.getInstance().getLastRecord(record.getStockId())){
						JOptionPane.showMessageDialog(this, "Please select latest transaction of the product.",
								"Invalid Transaction Selected", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						record.setType(TransactionType.valueOf(("revert_" + record.getType().toString())));
						StockActivityForm stockActivityForm = 
								new StockActivityForm((Frame)this.getParent().getParent().getParent().getParent().getParent(),
										"Revert Transaction", true, record);
						stockActivityForm.setLocationRelativeTo(this);
						stockActivityForm.setVisible(true);
						fireDatabaseUpdatedEvent();
					}
				}catch (DBException e){
					System.out.println(e);
				}
			}
		}
	}

	

	// build Transaction Record Table method
	private JTable buildStockActivityTable(StockActivityTableModel stockActivityTableModel) {
		JTable table = new JTable(stockActivityTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(null);
		return table;
	}

	// refresh table on database update method 
	void fireDatabaseUpdatedEvent() {
		stockActivityTableModel.databaseUpdated();
	}
}