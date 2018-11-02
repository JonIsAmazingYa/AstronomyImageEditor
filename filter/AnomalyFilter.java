package filter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ui.ImageHolder;

public class AnomalyFilter extends Filter {
	private int noPasses = 1;
	private int thresholdVal = 40;
	
	public AnomalyFilter(ImageHolder h) {
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
							Color center = new Color(image.getRGB(x, y));
							
							//r, g, b
							int [] max = {0, 0, 0};
							int [] min = {255, 255, 255};
							for (Color v : surrounds) {
								int [] cur = {v.getRed(), v.getGreen(), v.getBlue()};
								
								for (int j=0; j < cur.length; j++) {
									if (cur[j] > max[j]) max[j] = cur[j];
									
									if (cur[j] < min[j]) min[j] = cur[j];
								}
							}
							
							if (center.getRed() > max[0] + thresholdVal) {
								int totR = 0, count = 0;
								
								for (Color v : surrounds) {
									totR += v.getRed();
									
									count++;
								}
								
								center = new Color((totR/count), center.getGreen(), center.getBlue());
							}
							
							if (center.getGreen() > max[1] + thresholdVal) {
								int totG = 0, count = 0;
								
								for (Color v : surrounds) {
									totG += v.getGreen();
									
									count++;
								}
								
								center = new Color(center.getRed(), (totG/count), center.getBlue());
							}
							
							if (center.getBlue() > max[2] + thresholdVal) {
								int totB = 0, count = 0;
								
								for (Color v : surrounds) {
									totB += v.getBlue();
									
									count++;
								}
								
								center = new Color(center.getRed(), center.getGreen(), (totB/count));
							}
							
							
							//minimums
							if (center.getRed() < min[0] - thresholdVal) {
								int totR = 0, count = 0;
								
								for (Color v : surrounds) {
									totR += v.getRed();
									
									count++;
								}
								
								center = new Color((totR/count), center.getGreen(), center.getBlue());
							}
							
							if (center.getGreen() < min[1] - thresholdVal) {
								int totG = 0, count = 0;
								
								for (Color v : surrounds) {
									totG += v.getGreen();
									
									count++;
								}
								
								center = new Color(center.getRed(), (totG/count), center.getBlue());
							}
							
							if (center.getBlue() < min[2] - thresholdVal) {
								int totB = 0, count = 0;
								
								for (Color v : surrounds) {
									totB += v.getBlue();
									
									count++;
								}
								
								center = new Color(center.getRed(), center.getGreen(), (totB/count));
							}
							
							edited.setRGB(x, y, center.getRGB());
							
							
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
		settings.setLayout(new GridLayout(4,1));
		
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
		
		settings.add(new JLabel("Threshold"));
		JSlider threshold = new JSlider();
		threshold.setMinimum(0);
		threshold.setMaximum(255);
		threshold.setPaintTicks(true);
		threshold.setPaintLabels(true);
		threshold.setMajorTickSpacing(50);
		threshold.setValue(thresholdVal);
		
		threshold.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				thresholdVal = threshold.getValue();
			}
			
		});
		
		settings.add(threshold);
		
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
		return "Anomaly filter";
	}
}
