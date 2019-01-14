package soft.edu.view;

import soft.edu.common.SoftConfig;
import soft.edu.dao.ITeacherDao;
import soft.edu.dao.TeacherDaoImpl;
import soft.edu.util.FrameUtil;
import soft.edu.util.ImageIconUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TecLoginFrame extends JFrame {
	public TecLoginFrame(String title){
		setSize(SoftConfig.DEFAULT_WIDTH, 400);
		setLocation(SoftConfig.screenWidth/3,SoftConfig.screenHeight/3);
		setLocationByPlatform(false);
		TecLoginPanel tp = new TecLoginPanel();
		setContentPane(tp);
		FrameUtil.baseSetFrame(this, title, null);
	}
	/**
	 * 教师登录面板
	 */
	private class TecLoginPanel extends JPanel{
		JLabel picLabel,idLabel,pwdLabel;
		JTextField tecIdTextField;
		JPasswordField pwdField;
		JButton okButton,clearButton;
		JPanel buttonPanel,infoPanel;
		JCheckBox isadminbox;
		public TecLoginPanel(){
			picLabel = new JLabel();
			idLabel = new JLabel();
			pwdLabel = new JLabel();
			tecIdTextField = new JTextField("在此输入教师ID",20);
			pwdField = new JPasswordField(20);
			okButton = new JButton("提交");
			clearButton = new JButton("重置");
			buttonPanel = new JPanel();
			isadminbox = new JCheckBox("我是管理员");
			infoPanel = new JPanel();
			setLayout(new BorderLayout());
			infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			picLabel.setSize(SoftConfig.DEFAULT_WIDTH,200);
			idLabel.setSize(32,32);
			pwdLabel.setSize(32,32);
			/*
			 * 设置图标
			 */
			picLabel.setIcon(ImageIconUtil.setImageIcon("/images/bg.jpg", picLabel));
			infoPanel.add(picLabel);
			idLabel.setIcon(ImageIconUtil.setImageIcon("/images/teacher.png", idLabel));
			infoPanel.add(idLabel);
			pwdLabel.setIcon(ImageIconUtil.setImageIcon("/images/pwd.png", pwdLabel));
			infoPanel.add(pwdLabel);
			okButton.setIcon(ImageIconUtil.setBtImageIcon("/images/pwd.png", okButton,20,20));
			clearButton.setIcon(ImageIconUtil.setBtImageIcon("/images/refresh.png", clearButton,20,20));
			infoPanel.add(idLabel);
			idLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
			infoPanel.add(tecIdTextField);
			tecIdTextField.setLayout(new FlowLayout(FlowLayout.RIGHT));
			tecIdTextField.addMouseListener(new MouseListener() {
				boolean flag=true;
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					if(flag){
					tecIdTextField.setText("");
					}
					flag = false;
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
				}
			});
			infoPanel.add(pwdLabel);
			pwdLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
			infoPanel.add(pwdField);
			pwdField.setLayout(new FlowLayout(FlowLayout.RIGHT));
			infoPanel.add(isadminbox);
			add(infoPanel,BorderLayout.CENTER);
			/*
			 * 设置监听事件
			 */
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					final String techid = tecIdTextField.getText().trim();
					final String pwd = new String(pwdField.getPassword());
					System.out.println(techid+":"+pwd);
					if(techid.equals("")||techid.equals("在此输入学生ID")||pwd.equals("")){
						JOptionPane.showMessageDialog(null, "ID和密码均不能为空","错误提示",JOptionPane.ERROR_MESSAGE);
						tecIdTextField.setText("");
					}
					else{
					EventQueue.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							ITeacherDao techDao = new TeacherDaoImpl();
							if(techDao.login(techid, pwd)){
								TecLoginFrame.this.dispose();
								new TechMainFrame(techid);
								System.out.println("登录成功");
							}else{
								JOptionPane.showMessageDialog(null, "教师信息输入有误","错误提示",JOptionPane.ERROR_MESSAGE);
								pwdField.setText("");
							}
						}
					});	
					}
				
				}
			});
			clearButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					tecIdTextField.setText("");
					pwdField.setText("");
				}
			});
			buttonPanel.add(okButton);
			buttonPanel.add(clearButton);
			add(buttonPanel,BorderLayout.SOUTH);
		}
		
	}
}
