package soft.edu.view;

import soft.edu.common.SoftConfig;
import soft.edu.util.ImageIconUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLoginFrame extends JFrame {
	private JButton stuLoginButton = new JButton("学生登录");
	private JButton tecLoginButton = new JButton("教师登录");
	
	public MainLoginFrame(String title){
		setSize(450,SoftConfig.DEFAULT_HEIGHT);
		setTitle(title);
		/*
		 * 设置窗口在屏幕中出现的位置
		 */
		setLocation(SoftConfig.screenWidth/3,SoftConfig.screenHeight/3);
		setLocationByPlatform(false);
		/*
		 * 获取图片资源
		 */
		Image img = new ImageIcon(SoftConfig.SRC_PATH + "/images/frametitle.png").getImage();
		setIconImage(img);
		MainLoginPanel mp = new MainLoginPanel();
		setContentPane(mp);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
	/**
	 * 显示登录面板
	 */
	private class MainLoginPanel extends JPanel{
		JLabel picLabel;
		JPanel buttonPanel,infoPanel;
		public MainLoginPanel(){
			buttonPanel = new JPanel();
			infoPanel = new JPanel();
			picLabel = new JLabel();
			ButtonAction bta= new ButtonAction();
			setLayout(new BorderLayout());
			infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			picLabel.setSize(450,200);
			stuLoginButton.setSize(95,120);
			stuLoginButton.setIcon(ImageIconUtil.setBtImageIcon("/images/StudentLogin.png", stuLoginButton,105,120));
			stuLoginButton.addActionListener(bta);
			stuLoginButton.setFont(SoftConfig.font);
			tecLoginButton.setSize(95,120);
			tecLoginButton.setFont(SoftConfig.font);
			tecLoginButton.setIcon(ImageIconUtil.setBtImageIcon("/images/TeacherLogin.png", tecLoginButton,105,120));
			tecLoginButton.addActionListener(bta);
			/*
			 * 设置图标
			 */
			picLabel.setIcon(ImageIconUtil.setImageIcon("/images/bg.jpg", picLabel));
			infoPanel.add(picLabel);
			add(infoPanel,BorderLayout.CENTER);
			buttonPanel.add(stuLoginButton);
			stuLoginButton.setLayout(new FlowLayout(FlowLayout.LEFT));
			buttonPanel.add(tecLoginButton);
			tecLoginButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
			add(buttonPanel,BorderLayout.SOUTH);
		}
		
	}
	private class ButtonAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(stuLoginButton)){
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						new StuLoginFrame("学生登录");
					}
				});
			}
			else{
				EventQueue.invokeLater(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						new TecLoginFrame("教师登录");
					}
				});
			}
			MainLoginFrame.this.dispose();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainLoginFrame("欢迎使用考勤管理系统");

	}

	

}
