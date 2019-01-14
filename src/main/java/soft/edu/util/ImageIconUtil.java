package soft.edu.util;

import soft.edu.common.SoftConfig;

import javax.swing.*;
import java.awt.*;


public class ImageIconUtil {
	public static ImageIcon setImageIcon(String imgpath,JLabel label){
		ImageIcon img=new ImageIcon();
		if(imgpath.split(":/").length>1){
			img.setImage(Toolkit.getDefaultToolkit().getImage(imgpath).getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
		}
		else{
			img.setImage(Toolkit.getDefaultToolkit().getImage(SoftConfig.SRC_PATH + imgpath).getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
		}
		return img;
	}
	public static ImageIcon setBtImageIcon(String imgpath,JButton button,int iconWidth,int iconHeight){
		ImageIcon img = new ImageIcon(SoftConfig.SRC_PATH + imgpath);
		img.setImage(img.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT));
		return img;
	}

}
