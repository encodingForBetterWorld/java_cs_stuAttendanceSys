package soft.edu.ui;

import soft.edu.util.ImageIconUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListInfoPanel<T extends JFrame> extends JPanel {
	private JButton returnButton = new JButton("返回上级菜单");
	private JPanel listPanel = new JPanel(),
			buttonPanel = new JPanel();
	public ListInfoPanel(JTable listInfo,JFrame parentFrame,T t){
		JScrollPane scrollPane = new JScrollPane(listInfo, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listPanel.add(scrollPane,new FlowLayout(FlowLayout.CENTER));
		add(listPanel,new FlowLayout(FlowLayout.CENTER));
		returnButton.setIcon(ImageIconUtil.setBtImageIcon("/images/Logout.png", returnButton,20,20));
		returnButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				t.dispose();
				parentFrame.setVisible(true);
			}
		});
		buttonPanel.add(returnButton,new FlowLayout(FlowLayout.CENTER));
		add(buttonPanel,new FlowLayout(FlowLayout.CENTER));
	}
}
