package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ZoomPanel extends JPanel {
	public ZoomPanel(ImageHolder holder) {
		setLayout(new GridLayout(1,2,5,5));
		
		JButton btnIn = new JButton("+");
		btnIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				holder.zoomIn();
				holder.resizeImage();
			}
		});
		
		JButton btnOut = new JButton("-");
		btnOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				holder.zoomOut();
				holder.resizeImage();
			}
		});
		
		add(btnIn);
		add(btnOut);
	}
}
