package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import filter.*;

public class FilterSelector extends JComboBox<Filter> {
	private Filter [] filters;
	
	public FilterSelector(MainScreen parent, ImageHolder holder) {
		super();
		
		filters = new Filter [] {new Filter(holder), new MeanFilter(holder), new MeanFilterLarge(holder), new AnomalyFilter(holder)};
		
		for (Filter s : filters) {
			addItem(s);
		}
		
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.updateFilter((Filter) getSelectedItem());
				
				((Filter) getSelectedItem()).applyFilter();
			}
		});
	}
}
