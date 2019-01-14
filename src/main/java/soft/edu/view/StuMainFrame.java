package soft.edu.view;

import soft.edu.common.BackgroundImagePanel;
import soft.edu.common.SoftConfig;
import soft.edu.dao.IRecordDao;
import soft.edu.dao.IStuDao;
import soft.edu.dao.RecordDaoImpl;
import soft.edu.dao.StuDaoImpl;
import soft.edu.domain.AttendanceBean;
import soft.edu.domain.ClassBean;
import soft.edu.domain.StudentBean;
import soft.edu.ui.RoundRectButton;
import soft.edu.util.FrameUtil;
import soft.edu.util.ImageIconUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StuMainFrame extends JFrame{
	private JButton liststuButton = new RoundRectButton("查看个人资料",120,50),
	updateInfoButton = new RoundRectButton("修改个人资料",120,50),
	updatepwdButton = new RoundRectButton("修改个人密码",120,50),
	listtecButton = new RoundRectButton("查看教师资料",120,50),
	listcourseButton = new RoundRectButton("查看已选课程",120,50),
	queryButton = new JButton("查询"),
	logoutButton = new JButton("注销");
	ButtonAction bta = new ButtonAction();
	private String stu_id,stu_name,stu_photo;
	private JPanel tempjp=new JPanel();
	private JTextField textfield = new JTextField(20);
	private IRecordDao recordDao = new RecordDaoImpl();
	private IStuDao stuDao = new StuDaoImpl();
	private Border border=SoftConfig.shadowborder;
	public StuMainFrame(String stu_id){
		this.stu_id = stu_id;
		StudentBean stu = new StudentBean();
		stu = stuDao.queryStuById(stu_id);
		this.stu_name = stu.getStu_name();
		this.stu_photo = stu.getStu_photo();
		setBounds(SoftConfig.screenWidth/5,SoftConfig.screenHeight/5, 680, 550);
		setLayout(null); //设置空布局     
		JPanel01 jp1=new JPanel01(stu_name);
		jp1.setBounds(10, 0, 150, 185);
		JPanel02 jp2=new JPanel02();
		jp2.setBounds(10, 190, 150, 340);
		JPanel03 jp3=new JPanel03();
		jp3.setBounds(250, 0, 335, 70);
		FrameUtil.baseSetFrame(this,"欢迎您，亲爱的"+stu_name+"同学！", jp1,jp2,jp3,tempjp);
	}
	private class JPanel01 extends BackgroundImagePanel{
		private JLabel iconlabel,idlabel;
		public JPanel01(String stu_name) {
			super(SoftConfig.panel_bgimg);
			// TODO Auto-generated constructor stub
			iconlabel = new JLabel();
			iconlabel.setSize(145,135);
			idlabel = new JLabel();
			iconlabel.setIcon(ImageIconUtil.setImageIcon("/photos/"+stu_photo, iconlabel));
			idlabel.setFont(SoftConfig.mfont);
			idlabel.setText(stu_name);
			idlabel.setForeground(Color.white);
			add(iconlabel,new FlowLayout(FlowLayout.CENTER));
			add(idlabel,new FlowLayout(FlowLayout.CENTER));
			setBorder(border); //设置边框    
		}
	}
	private class JPanel02 extends BackgroundImagePanel{
		public JPanel02() {
			super(SoftConfig.panel_bgimg);
			// TODO Auto-generated constructor stub
			setSize(150, 340);
			FrameUtil.baseSetBtn(this, bta, 5, 120, 50, SoftConfig.font, liststuButton,updateInfoButton,updatepwdButton,listtecButton,listcourseButton);
			logoutButton.setIcon(ImageIconUtil.setBtImageIcon("/images/Logout.png", logoutButton,20,20));
			logoutButton.addActionListener(bta);
			logoutButton.setBounds(25, 290, 100, 30);
			add(logoutButton);
			setBorder(border); //设置边框    
		}
	}
	private class JPanel03 extends BackgroundImagePanel{
		private JLabel tiplabel;
		public JPanel03() {
			super(SoftConfig.panel_bgimg);
			// TODO Auto-generated constructor stub
			tiplabel=new JLabel("请输入要查询考勤记录的课程编号(名称)");
			tiplabel.setForeground(Color.white);
			queryButton.addActionListener(bta);
			add(tiplabel,new FlowLayout(FlowLayout.CENTER));
			add(textfield,new FlowLayout(FlowLayout.LEFT));
			add(queryButton,new FlowLayout(FlowLayout.RIGHT));
			setBorder(border);
		}
	}
	private class ButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int buttontype=0;
			if(e.getSource().equals(liststuButton))buttontype=1;
			else if(e.getSource().equals(updatepwdButton))buttontype=2;
			else if(e.getSource().equals(updateInfoButton))buttontype=3;
			else if(e.getSource().equals(listtecButton))buttontype=4;
			else if(e.getSource().equals(listcourseButton))buttontype=5;
			else if(e.getSource().equals(logoutButton))buttontype=6;
			switch(buttontype){
			case 0:
				List<Object> objs = recordDao.queryAttForStu(stu_id, textfield.getText().trim());
					if(objs.size()!=0){
						Object[][]datas = new Object[objs.size()/2][4];
						int j=0;
						for(int i = 0;i<objs.size();i+=2){
							AttendanceBean att = (AttendanceBean)objs.get(i);
							ClassBean cla = (ClassBean)objs.get(i+1);
							datas[j][0]=cla.getClass_id();
							datas[j][1]=cla.getCourse_name();
							datas[j][2]=att.getRecordtime();
							switch(att.getRecordstatus()){
							case 0:datas[j][3]="正常";break;
							case 1:datas[j][3]="迟到";break;
							case 2:datas[j][3]="旷课";break;
							case 3:datas[j][3]="早退";break;
							case 4:datas[j][3]="请假";break;
							}
							j++;
						}
						/*
						 * 局部刷新显示表单
						 */
						StuMainFrame.this.remove(tempjp);
						String[] columns={"课程编号","课程名称","考勤时间","考勤结果"};
						TableModel tableModel=new DefaultTableModel(datas,columns);
						JTable listInfo = new JTable(tableModel);
						listInfo.setRowHeight(20);
						listInfo.setEnabled(false);
						JScrollPane scrollPane = new JScrollPane(listInfo, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						scrollPane.setBorder(border);
						JPanel listjp = new JPanel();
						listjp.setBounds(160, 70, 540, 445);
						listjp.add(scrollPane);
						tempjp=listjp;
						FrameUtil.baseSetFrame(StuMainFrame.this,"欢迎您，亲爱的"+stu_name+"同学！", tempjp);
					}else{
						JOptionPane.showMessageDialog(null, "您没有课程编号(名称)为"+textfield.getText().trim()+"的课程","错误提示",JOptionPane.ERROR_MESSAGE);
						textfield.setText("");
					}
				break;
			case 1:
				StuMainFrame.this.dispose();
				new StuInfoFrame(stu_id);
				break;
			case 2:
				UpdatePwdFrame updatepwdf = new UpdatePwdFrame(stu_id);
				updatepwdf.parentFrame = StuMainFrame.this;
				StuMainFrame.this.setVisible(false);
				break;
			case 3:
				StuMainFrame.this.dispose();
				new UpdateInfoFrame(stu_id,StuMainFrame.class);
				break;
			case 4:
				new ListTecInfoFrame(StuMainFrame.this); 
				StuMainFrame.this.setVisible(false);
				break;
			case 5:
				new ListStuClassFrame(stu_id,StuMainFrame.this);
				StuMainFrame.this.setVisible(false);
				break;
			case 6:
				StuMainFrame.this.dispose();
				new MainLoginFrame("您已注销完毕，欢迎再次使用");
				break;
		}		
	  }
	}
}
