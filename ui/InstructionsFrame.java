package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InstructionsFrame extends JFrame {
	
	private String content = "To load a single (grayscale or color) image\n"
			+ "Select 'File' > 'Open...', then navigate to the directory the file is stored in, and double-click the file. The image will "
			+ "then be loaded into the program\n\n"
			+ "To load a composite (RGB) image, by channel\n"
			+ "Select 'File' > 'Open Composite...', then navigate to the directory the red image is stored, then double click the file. "
			+ "After this, select the green component, followed by the blue component. The image will then be combined into a single "
			+ "image and loaded into the program.\n\n"
			+ "To apply a filter to an image\n"
			+ "Once an image is loaded, select the filter to apply from the list. Adjust the settings held in the pane to the left until "
			+ "the image is to the standard expected from the image. Mean filters denoise by blurring the contents of each pixel with the "
			+ "average of the surrounding pixels. Anomaly filters denoise by applying this principle only when a pixel's color value is "
			+ "outside the threshold set in the pane to the left.\n\n"
			+ "Saving an image\n"
			+ "Select 'File' > 'Export' and navigate to the directory you wish to save the image. Enter a file name in the box at the "
			+ "bottom of the window labelled 'File Name', then click the button labelled 'Export'. Note that exported images will be saved "
			+ "in the PNG format.\n\n"
			+ "Disclaimer\n"
			+ "Images produced using the algorithms contained in this application are not designed for scientific research purposes, and "
			+ "data produced may be inaccurate.";
	
	public InstructionsFrame() {
		super("Instructions");
		
		setSize(new Dimension(420,360));
		setMinimumSize(getSize());
		setMaximumSize(getSize());
		
		getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		setLayout(new BorderLayout(5,5));
		
		add(new JLabel("Instructions for using Astronomical Image Compositor"), BorderLayout.NORTH);
		
		JTextArea txtInstr = new JTextArea();
		txtInstr.setLineWrap(true);
		txtInstr.setWrapStyleWord(true);
		txtInstr.setEditable(false);
		
		txtInstr.setText(content);
		
		add(new JScrollPane(txtInstr), BorderLayout.CENTER);
	}
}
