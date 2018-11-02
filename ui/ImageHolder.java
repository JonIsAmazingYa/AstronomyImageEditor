package ui;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageHolder extends JLabel {
	private BufferedImage original = null;
	private BufferedImage edited = null;
	private double defaultZoom = 0.4;
	private double zoomLevel = defaultZoom;
	
	public ImageHolder() {
		super("No image loaded", JLabel.CENTER);
	}
	
	public ImageHolder(BufferedImage b) {
		super();
		setOriginal(b);
	}
	
	public void setOriginal(BufferedImage b) {
		if (b != null) {
			zoomLevel = defaultZoom;
			original = b;
			updateImage(original);
		}
	}
	
	public BufferedImage getOriginal() {
		return original;
	}
	
	public void updateImage(BufferedImage b) {
		if (b != null) {
			setText("");
			edited = b;
			resizeImage();
		}
	}
	
	public void zoomIn() {
		zoomLevel += 0.1;
	}
	
	public void zoomOut() {
		zoomLevel -= 0.1;
	}
	
	public BufferedImage getEdited() {
		return edited;
	}
	
	public void resizeImage() {
		if (getOriginal() != null) {
			System.out.println(zoomLevel);
			
			BufferedImage cur = getEdited();
			
			int newImageWidth = new Double(cur.getWidth() * zoomLevel).intValue();
			int newImageHeight = new Double(cur.getWidth() * zoomLevel).intValue();
			BufferedImage resizedImage = new BufferedImage(newImageWidth , newImageHeight, cur.getType());
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(cur, 0, 0, newImageWidth , newImageHeight , null);
			g.dispose();
			
			setIcon(new ImageIcon(resizedImage));
			
			revalidate();
			repaint();
		}
	}
	
	public void clearImage() {
		setIcon(null);
		original = null;
		setText("No image loaded");
	}
	
	
}
