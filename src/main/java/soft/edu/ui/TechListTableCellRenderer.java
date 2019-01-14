package soft.edu.ui;

import soft.edu.common.SoftConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TechListTableCellRenderer extends DefaultTableCellRenderer {
	private DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
	private UpdateTableCellRenderer updateRender=new UpdateTableCellRenderer();
	private DeleteTableCellRenderer deleteRender=new DeleteTableCellRenderer();
	  @Override
	  public Component getTableCellRendererComponent(JTable table, Object value,  
	    boolean isSelected, boolean hasFocus, int row, int column) {
		boolean isUpdate=table.getValueAt(row, 6).toString().trim().equals("true"),
				isDelete=table.getValueAt(row, 7).toString().trim().equals("true");
	    if(isUpdate&&isDelete){  
	  //调用基类方法  
	        return super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);  
	    }
	    else if(isUpdate&&!isDelete){
	    	return updateRender.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);  
	    }
	    else if(!isUpdate&&isDelete){
	    	return deleteRender.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);  
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
	      Graphics2D g2=(Graphics2D)g;  
	      g2.setColor(Color.RED);  
	      g2.setStroke(SoftConfig.stroke);  
	      g2.drawLine(0,getHeight()/2,getWidth(),getHeight()/2);  
	  }
}
