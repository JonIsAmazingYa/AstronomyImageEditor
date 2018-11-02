package filter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ui.ImageHolder;

public class MeanFilterLarge extends Filter {
	private int noPasses = 1;
	
	public MeanFilterLarge(ImageHolder h) {
		super(h);
	}
	
	public void applyFilter() {
		if (holder.getOriginal() != null) {
			BufferedImage image = holder.getOriginal();
			BufferedImage edited = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
			
			for (int i = 0; i < noPasses; i++) {
				for (int y = 0; y < image.getHeight(); y++) {
					for (int x = 0; x < image.getWidth(); x++) {
						if (y > 0 && y < image.getHeight()-1 && x > 0 && x < image.getWidth()-1) {
							//top left, top, top right, bottom left, bottom, bottom right, left, right
							Color [] surrounds = {new Color(image.getRGB(x-1, y+1)), new Color(image.getRGB(x, y+1)), new Color(image.getRGB(x+1, y+1)), new Color(image.getRGB(x-1, y-1)), new Color(image.getRGB(x, y-1)), new Color(image.getRGB(x+1, y-1)), new Color(image.getRGB(x-1, y)), new Color(image.getRGB(x+1, y))};
							
							int count = 0;
							int totR = 0, totG = 0, totB = 0;
							for (Color v : surrounds) {
								totR += v.getRed();
								totG += v.getGreen();
								totB += v.getBlue();
								
								count++;
							}
							
							int rVal = totR/count;
							int gVal = totG/count;
							int bVal = totB/count;
							
							edited.setRGB(x, y, (rVal << 16) | (gVal << 8) | bVal);
						} else {
							edited.setRGB(x, y, image.getRGB(x, y));
						}
					}
				}
				
				image = edited;
			}
			
			holder.updateImage(edited);
		}
	}
	
	public JPanel getSettingsPanel() {
		JPanel rtnPanel = new JPanel();
		rtnPanel.setLayout(new BorderLayout());
		
		JPanel settings = new JPanel();
		settings.setLayout(new GridLayout(2,1));
		
		settings.add(new JLabel("Passes"));
		JSlider passes = new JSlider();
		passes.setMinimum(1);
		passes.setMaximum(5);
		passes.setPaintTicks(true);
		passes.setPaintLabels(true);
		passes.setMajorTickSpacing(1);
		passes.setValue(noPasses);
		
		passes.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				noPasses = passes.getValue();
			}
			
		});
		
		settings.add(passes);
		
		JPanel pnlApplyHolder = new JPanel();
		pnlApplyHolder.setLayout(new GridLayout(1,1));
		pnlApplyHolder.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		JButton applyFilter = new JButton("Apply filter");
		applyFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyFilter();
			}
		});
		
		pnlApplyHolder.add(applyFilter);
		
		rtnPanel.add(pnlApplyHolder, BorderLayout.SOUTH);
		
		rtnPanel.add(settings, BorderLayout.NORTH);
		
		return rtnPanel;
	}
	
	public String toString() {
		return "Mean (average) filter, 8 samples";
	}
}
