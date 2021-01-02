package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

public class PredmetJTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 199113098313326605L;

	private static PredmetJTable instance = null;

	public static PredmetJTable getInstance() {
		if (instance == null) {
			instance = new PredmetJTable();
		}
		return instance;
	}

	private PredmetJTable() {
		this.setRowSelectionAllowed(true);
		this.setColumnSelectionAllowed(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setModel(new AbstractTableModelPredmeti());

	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component c = super.prepareRenderer(renderer, row, column);
		// selektovani red ce imati drugaciju boju od ostalih
		if (isRowSelected(row)) {
			c.setBackground(Color.LIGHT_GRAY);
		} else {
			c.setBackground(Color.WHITE);
		}
		return c;
	}

	public void refresTabelu() {
		AbstractTableModelPredmeti model = (AbstractTableModelPredmeti) this.getModel();
		model.fireTableDataChanged();
		validate();
	}

}
