package com.anthony;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableController implements ListSelectionListener {
	
	private JTable table;
	
	public TableController (JTable inputTable) {
		this.table = inputTable;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
