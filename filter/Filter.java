package filter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.ImageHolder;

public class Filter {
	protected ImageHolder holder;
	
	public Filter(ImageHolder h) {
		holder = h;
	}
	
	public void applyFilter() {
		holder.updateImage(holder.getOriginal());
	}
	
	public JPanel getSettingsPanel() {
		JPanel rtnPanel = new JPanel();
		rtnPanel.setLayout(new BorderLayout());
		
		rtnPanel.add(new JLabel("This filter has no settings", JLabel.CENTER));
		
		return rtnPanel;
	}
	
	public String toString() {
		return ("No filter");
	}
}
