package soft.edu.view;

import soft.edu.common.SoftConfig;
import soft.edu.dao.IStuDao;
import soft.edu.dao.StuDaoImpl;
import soft.edu.util.FrameUtil;
import soft.edu.util.ImageIconUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StuLoginFrame extends JFrame{
	private JButton okButton = new JButton("登录"),clearButton = new JButton("重置");
	private JTextField stuIdTextField;
	private JPasswordField pwdField;
	//JFrame parentFrame;
	public StuLoginFrame(String title){
		setSize(SoftConfig.DEFAULT_WIDTH, SoftConfig.DEFAULT_HEIGHT);
		/*
		 * 设置窗口在屏幕中的出现位置
		 */
		setLocation(SoftConfig.screenWidth/3,SoftConfig.screenHeight/3);
		setLocationByPlatform(false);
		/*
		 * 设置图标
		 */
		Image img = new ImageIcon(SoftConfig.SRC_PATH + "/images/frametitle.png").getImage();
		setIconImage(img);
		StuLoginPanel sp=new StuLoginPanel();
		setContentPane(sp);
		FrameUtil.baseSetFrame(this, title, null);
	
	}
	/**
	 * 学生登录面板
	 */
	private class StuLoginPanel extends JPanel{
		JLabel picLabel,idLabel,pwdLabel;
		JPanel buttonPanel,infoPanel;
		ButtonAction bta = new ButtonAction();
		public StuLoginPanel(){
			picLabel = new JLabel();
			idLabel = new JLabel();
			pwdLabel = new JLabel();
			stuIdTextField = new JTextField("在此输入学生ID",20);
			pwdField = new JPasswordField(20);
			buttonPanel = new JPanel();
			infoPanel = new JPanel();
			setLayout(new BorderLayout());
			infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			picLabel.setSize(SoftConfig.DEFAULT_WIDTH,200);
			idLabel.setSize(32,32);
			pwdLabel.setSize(32,32);
			/*
			 * 设置控件图标
			 */
			picLabel.setIcon(ImageIconUtil.setImageIcon("/images/bg.jpg", picLabel));
			infoPanel.add(picLabel);
			idLabel.setIcon(ImageIconUtil.setImageIcon("/images/student.png", idLabel));
			infoPanel.add(idLabel);
			pwdLabel.setIcon(ImageIconUtil.setImageIcon("/images/pwd.png", pwdLabel));
			infoPanel.add(pwdLabel);
			okButton.setIcon(ImageIconUtil.setBtImageIcon("/images/pwd.png", okButton,20,20));
			okButton.addActionListener(bta);
			clearButton.setIcon(ImageIconUtil.setBtImageIcon("/images/refresh.png", clearButton,20,20));
			clearButton.addActionListener(bta);
			infoPanel.add(idLabel);
			idLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
			infoPanel.add(stuIdTextField);
			stuIdTextField.setLayout(new FlowLayout(FlowLayout.RIGHT));
			stuIdTextField.addMouseListener(new MouseListener() {
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
					stuIdTextField.setText("");
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
			add(infoPanel,BorderLayout.CENTER);
			buttonPanel.add(okButton);
			buttonPanel.add(clearButton);
			add(buttonPanel,BorderLayout.SOUTH);
		}
		
	}
	private class ButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String stuid = stuIdTextField.getText().trim();
			String pwd = new String(pwdField.getPassword());
			if(e.getSource().equals(okButton)){
				if(stuid.equals("")||stuid.equals("在此输入学生ID")||pwd.equals("")){
					JOptionPane.showMessageDialog(null, "ID和密码均不能为空","错误提示",JOptionPane.ERROR_MESSAGE);
					stuIdTextField.setText("");
				}
				else{
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						IStuDao stuDao = new StuDaoImpl();
						if(stuDao.login(stuid, pwd)){
							StuLoginFrame.this.dispose();
							new StuMainFrame(stuid);
							System.out.println("登录成功");
						}else{
							JOptionPane.showMessageDialog(null, "学生信息输入有误","错误提示",JOptionPane.ERROR_MESSAGE);
							pwdField.setText("");
						}
					}
				});	
				}
			}else{
				stuIdTextField.setText("");
				pwdField.setText("");
			}
		}
		
	}

}
