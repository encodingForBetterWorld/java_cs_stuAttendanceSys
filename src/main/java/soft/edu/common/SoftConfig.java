package soft.edu.common;

import soft.edu.ui.ShadowBorder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SoftConfig {
	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 360;
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Image panel_bgimg = Toolkit.getDefaultToolkit().getImage(SoftConfig.class.getResource("/images/panel_background.jpg"));
	public static final Image panel_bgimghead = Toolkit.getDefaultToolkit().getImage(SoftConfig.class.getResource("/images/panel_img1.jpg"));
	public static final Image panel_bgimg1 = Toolkit.getDefaultToolkit().getImage(SoftConfig.class.getResource("/images/panel_bg1.jpg"));
	public static final int screenWidth = screenSize.width;
	public static final int screenHeight = screenSize.height;
	public static final Font font = new Font("", Font.BOLD,14);
	public static final Font mfont = new Font("", Font.BOLD,20);
	public static final Font bgfont = new Font("", Font.BOLD,25);
	public static final BasicStroke stroke=new BasicStroke(3.0f);
	public static final BasicStroke btnstroke=new BasicStroke(0.1f);
	public static final Border shadowborder = BorderFactory.createCompoundBorder(ShadowBorder.newInstance(), BorderFactory.createLineBorder(Color.white));
	public static final String SRC_PATH = System.getProperty("user.dir") + "/src/main/resources";

	public static byte StrToByte(String str){
		byte b=0;
		switch(str){
			case "正常":b=0;break;
			case "迟到":b=1;break;
			case "旷课":b=2;break;
			case "早退":b=3;break;
			case "请假":b=4;break;
			default:b=0;
		}
		return b;
	}
	public static String ByteToStr(byte b){
		String str="";
		switch(b){
			case 0:str="正常";break;
			case 1:str="迟到";break;
			case 2:str="旷课";break;
			case 3:str="早退";break;
			default:str="请假";break;
		}
		return str;
	}
	public static void compenentsSetFont(Font compenentFont,Component...compenents){
		for(Component compenent:compenents){
			compenent.setFont(compenentFont);
		}
	}
}
