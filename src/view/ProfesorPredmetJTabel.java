package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

public class ProfesorPredmetJTabel extends JTable{
	private static ProfesorPredmetJTabel instance = null;

	public static ProfesorPredmetJTabel getInstance() {
		 if(instance==null)
			instance = new ProfesorPredmetJTabel();
		
		return instance;
	}

	private ProfesorPredmetJTabel() {
		this.setRowSelectionAllowed(true);
		this.setColumnSelectionAllowed(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setModel(new AbstractTableModelProfPredmet());

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
	public void azurirajPrikaz() {
		AbstractTableModelProfPredmet model = (AbstractTableModelProfPredmet) this.getModel(); 
		model.fireTableDataChanged(); //obavestavamo da je doslo do promene podatak i da model treba da se azurira
		validate(); //ugradjena metoda
	}

}
