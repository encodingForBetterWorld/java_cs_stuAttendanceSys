package soft.edu.ui;

import soft.edu.common.SoftConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * 
 * ClassName: DeleteTableCellRenderer 
 * @Description: 点击确认删除按钮，改行会被划红线
 * @author wangsuqi
 * @date 2016年9月4日
 * @see
 */
public class DeleteTableCellRenderer extends DefaultTableCellRenderer {  
	  private DefaultTableCellRenderer renderer=new DefaultTableCellRenderer(); 
	  @Override
	  public Component getTableCellRendererComponent(JTable table, Object value,  
	    boolean isSelected, boolean hasFocus, int row, int column) {  
	    if(table.getValueAt(row, 7).toString().trim().equals("true")){  
	  //调用基类方法  
	        return super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);  
	    }  
	    else{  
	        return renderer.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);  
	    }  
	  } 
	  //该类继承与JLabel，Graphics用于绘制单元格,绘制红线  
	  @Override
	  public void paintComponent(Graphics g){  
	      super.paintComponent(g);  
	      Graphics2D g2=(Graphics2D)g;  
	      g2.setColor(Color.RED);  
	      g2.setStroke(SoftConfig.stroke);  
	      g2.drawLine(0,getHeight()/2,getWidth(),getHeight()/2);  
	  }
}
