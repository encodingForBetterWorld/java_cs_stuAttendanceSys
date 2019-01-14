package soft.edu.view;

import soft.edu.common.SoftConfig;
import soft.edu.dao.ITeacherDao;
import soft.edu.dao.TeacherDaoImpl;
import soft.edu.domain.TeacherBean;
import soft.edu.util.FrameUtil;
import soft.edu.util.ImageIconUtil;
import soft.edu.util.PanelUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TechInfoFrame extends JFrame{
	private JButton updateInfoButton = new JButton("修改基本信息"),
			returnButton = new JButton("返回主菜单");
	private ITeacherDao techDao= new TeacherDaoImpl(); 
	public TechInfoFrame(String tech_id) {
		// TODO Auto-generated constructor stub
		setBounds(SoftConfig.screenWidth/5,SoftConfig.screenHeight/5, 350, 350);
		InfoPanel infopanel = new InfoPanel(tech_id);
		FrameUtil.baseSetFrame(this,"教师个人信息", infopanel);	
	}
	private class InfoPanel extends JPanel{
		private JPanel textInfoPanel=new JPanel(),photoPanel=new JPanel(),buttonPanel = new JPanel();
		private JLabel tipLabel = new JLabel("个人照片"),photoLabel = new JLabel();
		public InfoPanel(String tech_id){
			setLayout(null);
			TeacherBean tech = new TeacherBean();
			tech = techDao.queryTeacherById(tech_id);
			String[] str1= {"教师姓名",tech.getTeacher_name()};
			String[] str2= {"教师编号",tech.getTeacher_id()};
			String[] str3= {"所属学校",tech.getCollege_name()};
			String[] str4= {"所属学院",tech.getFaculty_name()};
			String[] str5= {"电子邮箱",tech.getTeacher_email()};
			PanelUtil.addTextFieldsToPanel(textInfoPanel, false,13,str1,str2,str3,str4,str5);
			textInfoPanel.setBounds(0, 0, 160, 250);
			photoLabel.setSize(165,200);
			photoLabel.setIcon(ImageIconUtil.setImageIcon("/photos/"+tech.getTeacher_photo(), photoLabel));
			photoPanel.add(tipLabel);
			photoPanel.add(photoLabel);
			photoPanel.setBounds(170,0,165,250);
			updateInfoButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					TechInfoFrame.this.dispose();
					new UpdateInfoFrame(tech_id,TechInfoFrame.class);
					TechInfoFrame.this.setVisible(false);
				}
			});
			buttonPanel.add(updateInfoButton);
			returnButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					TechInfoFrame.this.dispose();
					new TechMainFrame(tech_id);
				}
			});
			buttonPanel.add(returnButton);
			buttonPanel.setBounds(0,270,350,40);
			add(textInfoPanel);
			add(photoPanel);
			add(buttonPanel);
		}
	}
}
