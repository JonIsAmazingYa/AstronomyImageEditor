package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import filter.Filter;

public class MainScreen extends JFrame {
	private ImageHolder holder = new ImageHolder();
	private JPanel filterHolder = new JPanel();
	
	public MainScreen() {
		setup();
	}

	public void setup() {
		setTitle("Astronomy Image Compositor");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel pnlOptions = new JPanel();
		pnlOptions.setLayout(new BorderLayout());
		pnlOptions.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		pnlOptions.add(new FilterSelector(this, holder), BorderLayout.NORTH);
		add(pnlOptions, BorderLayout.WEST);
		
		Filter filter = new Filter(holder);
		
		filterHolder.add(filter.getSettingsPanel());
		pnlOptions.add(filterHolder, BorderLayout.CENTER);
		
		pnlOptions.add(new ZoomPanel(holder), BorderLayout.SOUTH);
		
		pnlOptions.setPreferredSize(new Dimension(220,220));
		
		add(new JScrollPane(holder), BorderLayout.CENTER);
		
		createMenus();
		
		pack();
		setSize(640,480);
	}
	
	public void updateFilter(Filter f) {
		filterHolder.removeAll();
		
		filterHolder.add(f.getSettingsPanel());
		
		revalidate();
		repaint();
	}
	
	public BufferedImage processImage(BufferedImage r, BufferedImage g, BufferedImage b) {
		for (int y = 0; y < r.getHeight(); y++) {
			for (int x = 0; x < r.getWidth(); x++) {
				int red = new Color(r.getRGB(x, y)).getRed();
				int green = new Color(g.getRGB(x, y)).getRed();
				int blue = new Color(b.getRGB(x, y)).getRed();

				r.setRGB(x, y, ((red << 16) | (green << 8) | blue));
			}
		}
		
		return r;
	}

	public void createMenus() {
		JMenuBar mainBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem itmOpen = new JMenuItem("Open...");
		
		JFileChooser fc = new JFileChooser();

		fc.setDialogTitle("Choose directory to export image...");
		
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileNameExtensionFilter("JPEG File", "jpg", "jpeg"));
		fc.setFileFilter(new FileNameExtensionFilter("PNG File", "png"));
		fc.setFileFilter(new FileNameExtensionFilter("All Supported Files", "jpg", "jpeg", "png"));
		
		itmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				fc.setDialogTitle("Select image to load");
				fc.setApproveButtonText("Open");
				
				if (fc.showOpenDialog(MainScreen.this) == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					try {
						holder.setOriginal(ImageIO.read(file));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainScreen.this, "An IO error occurred while loading the image", 
								"Error Loading", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					System.out.println("Operation aborted");
					return;
				}
			}
			
		});
		
		JMenuItem itmOpenComp = new JMenuItem("Open Composite...");
		itmOpenComp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				BufferedImage red = null, green = null, blue = null;

				fc.setDialogTitle("Select red image component");
				fc.setApproveButtonText("Open");
				
				if (fc.showOpenDialog(MainScreen.this) == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					try {
						red = ImageIO.read(file);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainScreen.this, "An IO error occurred while loading the image", 
								"Error Loading", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					System.out.println("Operation aborted");
					return;
				}
				
				fc.setDialogTitle("Select green image component");
				
				if (fc.showOpenDialog(MainScreen.this) == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					try {
						green = ImageIO.read(file);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainScreen.this, "An IO error occurred while loading the image", 
								"Error Loading", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					System.out.println("Operation aborted");
					return;
				}
				
				fc.setDialogTitle("Select blue image component");
				
				if (fc.showOpenDialog(MainScreen.this) == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					try {
						blue = ImageIO.read(file);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainScreen.this, "An IO error occurred while loading the image", 
								"Error Loading", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					System.out.println("Operation aborted");
					return;
				}
				
				holder.setOriginal(processImage(red, green, blue));
			}
			
		});
		
		JMenuItem itmExport = new JMenuItem("Export...");
		itmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					holder.getEdited();
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(MainScreen.this, "There is no image to save", 
							"Error Saving", JOptionPane.WARNING_MESSAGE);
					
					return;
				}
				
				fc.setDialogTitle("Select export location");
				fc.setApproveButtonText("Export");
				
				if (fc.showOpenDialog(MainScreen.this) == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					try {
						String fileLoc = file.getCanonicalFile().toString();
						fileLoc = fileLoc.substring(0, fileLoc.lastIndexOf("."));
						
						ImageIO.write(holder.getEdited(), "png", new File(fileLoc + ".png"));
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainScreen.this, "An IO error occurred while saving the image", 
								"Error Loading", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					System.out.println("Operation aborted");
					return;
				}
			}
		});
		
		JMenuItem itmExit = new JMenuItem("Exit");
        itmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                System.exit(0);
            }
        });
		
		fileMenu.add(itmOpen);
		fileMenu.add(itmOpenComp);
		fileMenu.add(itmExport);
		fileMenu.add(itmExit);
		mainBar.add(fileMenu);
		
		JMenu helpMenu = new JMenu("Help");
		
		InstructionsFrame instrFrame = new InstructionsFrame();

		JMenuItem itmInstructions = new JMenuItem("Instructions");
		itmInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instrFrame.setVisible(true);
			}
		});
		
		JMenuItem itmCredits = new JMenuItem("Credits");
		itmCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(MainScreen.this, "Program © Jonathan Mounty, 2016\nSpecial thanks to Dr Reichart and the Skynet team\n\n"
						+ "Unauthorized modification or repackaging of this program is prohibited",
						"Credits", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		helpMenu.add(itmInstructions);
		helpMenu.add(itmCredits);
		mainBar.add(helpMenu);
		
		setJMenuBar(mainBar);
	}
}
