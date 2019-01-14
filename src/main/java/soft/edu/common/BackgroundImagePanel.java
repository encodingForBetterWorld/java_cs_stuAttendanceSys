package soft.edu.common;

import javax.swing.*;
import java.awt.*;

public class BackgroundImagePanel extends JPanel {
	private Image image;
	public BackgroundImagePanel(Image image){
		this.image = image;
	}
	@Override
	public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      if (image != null) {
	         int height = image.getHeight(this);
	         int width = image.getWidth(this);
	 
	         if (height != -1 && height > getHeight())
	            height = getHeight();
	 
	         if (width != -1 && width > getWidth())
	            width = getWidth();
	         if	(width < getWidth())
	        	 width = getWidth();
	         if (height != -1 && height > getHeight())
	        	 height = getHeight();
	 
	         int x = (int) (((double) (getWidth() - width)) / 2.0);
	         int y = (int) (((double) (getHeight() - height)) / 2.0);
	         g.drawImage(image, x, y, width, height, this);
	      }
	   }
	
}
