package soft.edu.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * 
 * ClassName: UpdateTableCellRenderer 
 * @Description: 点击确认按钮该行背景色变为金色
 * @author wangsuqi
 * @date 2016年9月4日
 * @see
 */
public class UpdateTableCellRenderer extends DefaultTableCellRenderer {  
      private DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
	  @Override
	  public Component getTableCellRendererComponent(JTable table, Object value,  
	    boolean isSelected, boolean hasFocus, int row, int column) {  
	    if(table.getValueAt(row, 6).toString().trim().equals("true")){  
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
		  Color c=new Color(255,215,0);
	      super.setBackground(c); 
	      super.paintComponent(g);
	  }
}
