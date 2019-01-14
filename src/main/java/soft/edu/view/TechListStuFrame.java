package soft.edu.view;

import soft.edu.common.BackgroundImagePanel;
import soft.edu.common.PageBean;
import soft.edu.common.SoftConfig;
import soft.edu.dao.*;
import soft.edu.domain.ClassBean;
import soft.edu.domain.SelectcourseBean;
import soft.edu.domain.StudentBean;
import soft.edu.ui.DetailInfoPanel;
import soft.edu.util.FrameUtil;
import soft.edu.util.ImageIconUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class TechListStuFrame extends JFrame{
	JFrame parentFrame;
	Font font1=SoftConfig.font;
	private JButton queryButton = new JButton("查询"),
	returnButton = new JButton("返回主菜单");
	private JTextField textfield = new JTextField(10);
	private Border border = SoftConfig.shadowborder;
	private String tech_id,title;
	private IStuDao stuDao=new StuDaoImpl();
	private IRecordDao recordDao=new RecordDaoImpl();
	private SelectCourseDaoImpl scDao=new SelectCourseDaoImpl();
	private JComboBox<String> selectCourse=new JComboBox<>();
	private List<String> claids=new ArrayList<>();
	private List<JPanel> detailPs;
	private PageBean pageBean=new PageBean();
	public TechListStuFrame(String tech_id,String tech_name){
		this.tech_id=tech_id;
		this.title="教师"+tech_id+"("+tech_name+")的学生信息";
		setBounds(SoftConfig.screenWidth/5,SoftConfig.screenHeight/20, 650, 680);
		setLayout(null); //设置空布局  
		OperatePanel op=new OperatePanel();
		op.setBounds(2, 0, 640, 50);
//		op.setBackground(Color.LIGHT_GRAY);
		FrameUtil.baseSetFrame(this, title, op);
	}
	/**
	 * 
	 * ClassName: operatePanel 
	 * @Description: 用于摆放操作控件的面板
	 * @author wangsuqi
	 * @date 2016年9月2日
	 * @see TechListStuFrame
	 */
	private class OperatePanel extends BackgroundImagePanel{
		private IClassDao claDao=new ClassDaoImpl();
		private JLabel jl1=new JLabel(),jl2=new JLabel();
		//检验文本框内容是否改变的标记
		private String pretext="";
		public OperatePanel(){
			super(SoftConfig.panel_bgimg1);
			List<ClassBean> clas=new ArrayList<>();
			clas=claDao.queryClassForTeacher(tech_id);
			/**
			 * 为文本框和下拉框内容之间设置关联
			 */
			if(textfield.getText().trim().equals("")){
				for(ClassBean cla:clas){
					selectCourse.addItem(cla.getCourse_name());
					claids.add(cla.getClass_id());
				}
			}
			textfield.addMouseListener(new MouseListener() {
				
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
					/*
					 *输入内容不变不执行查询 
					 */
					if(!textfield.getText().trim().equals(pretext)){
						//如果输入为空查询该教师所有课程
						List<ClassBean> _clas=claDao.queryClassForTeacher(tech_id);
						List<String> _claids=new ArrayList<>();
						String textValue=textfield.getText().trim();
						if(textValue.equals("")){
							selectCourse.removeAllItems();
							for(ClassBean _cla:_clas){
								selectCourse.addItem(_cla.getCourse_name());
								_claids.add(_cla.getClass_id());
							}
							claids=_claids;
						}
						//如果输入为学生ID
						else{
							List<SelectcourseBean> _scbs=scDao.querySCbyStuId(textValue);
							if(_scbs.size()!=0){
								/*
								 * 验证该课程既是本老师所授又是本学生所选时,记录下该课程
								 */
								selectCourse.removeAllItems();
								for(SelectcourseBean _scb:_scbs){
									for(int i=0;i<_clas.size();i++){
										if(_scb.getClass_id().equals(_clas.get(i).getClass_id())){
											selectCourse.addItem(_clas.get(i).getCourse_name());
											_claids.add(_clas.get(i).getClass_id());
											break;
										}
									}
								}
								claids=_claids;
							}
							//如果输入的不是学生ID
							else{
								JOptionPane.showMessageDialog(null, "输入的学号有误","错误提示",JOptionPane.ERROR_MESSAGE);
							}
						}
					}
					pretext=textfield.getText().trim();
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			jl1.setText("学生学号");
			jl2.setText("课程名称");
			add(jl1);
			add(textfield);
			add(jl2);
			add(selectCourse);
			queryButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					PageBean _pageBean=new PageBean();
					/*
					 * 先清除上次查询产生的面板
					 */
					gotoPage(_pageBean);
					
				}
			});
			add(queryButton);
			returnButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					TechListStuFrame.this.parentFrame.setVisible(true);
					TechListStuFrame.this.dispose();
				}
			});
			add(returnButton);
			setBorder(border);
		}
	}
	private class changPagePanel extends BackgroundImagePanel{
		private JComboBox<String> selectPageBox=new JComboBox<>();
		private JButton prebutton=new JButton(),nextbutton=new JButton(),redirectbutton=new JButton("跳转");
		private JLabel tipl=new JLabel("选择页数");
		public changPagePanel(int pageIndex){
			super(SoftConfig.panel_bgimghead);
//			super(SoftConfig.panel_bgimgbottom);
			setLayout(null);
			/*
			 * 设置按钮图片和布局
			 */
			prebutton.setIcon(ImageIconUtil.setBtImageIcon("/images/pre.png", prebutton,50,50));
			nextbutton.setIcon(ImageIconUtil.setBtImageIcon("/images/next.png", nextbutton,50,50));
			/*
			 * 为下拉框添加属性
			 */
			int _index=1;
			while(_index<=pageIndex){
				selectPageBox.addItem("第"+_index+"页");
				_index++;
			}
			selectPageBox.setSelectedIndex(pageBean.getCurrentPage()-1);
			/*
			 * 设置字体
			 */
			SoftConfig.compenentsSetFont(font1, tipl,selectPageBox,redirectbutton);
			/*
			 * 设置布局
			 */
			prebutton.setBounds(10, 0, 50, 50);
			nextbutton.setBounds(580, 0, 50, 50);
			tipl.setBounds(180, 0, 80, 50);
			selectPageBox.setBounds(260, 10, 100, 30);
			redirectbutton.setBounds(360, 10, 100,30);
			/*
			 *为按钮绑定相应事件 
			 */
			prebutton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int cpage=pageBean.getCurrentPage()-1;
					System.out.println(cpage);
					if(cpage<1){
						JOptionPane.showMessageDialog(null, "已经是最前一页","错误提示",JOptionPane.ERROR_MESSAGE);
					}
					else{
						pageBean.setCurrentPage(cpage);
						gotoPage(pageBean);
					}
				}
			});
			nextbutton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int cpage=pageBean.getCurrentPage()+1;
					if(cpage>pageBean.getTotalPage()){
						JOptionPane.showMessageDialog(null, "已经是最后一页","错误提示",JOptionPane.ERROR_MESSAGE);
					}
					else{
						pageBean.setCurrentPage(cpage);
						gotoPage(pageBean);
					}
				}
			});
			redirectbutton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					pageBean.setCurrentPage(selectPageBox.getSelectedIndex()+1);
					gotoPage(pageBean);
				}
			});
			/*
			 * 添加组件
			 */
			add(prebutton);
			add(tipl);
			add(selectPageBox);
			add(redirectbutton);
			add(nextbutton);
		}
	}
	private void gotoPage(PageBean _pageBean){
		/**
		 * 先清空之前的面板
		 */
		if(detailPs!=null){
			for(JPanel d:detailPs){
				TechListStuFrame.this.remove(d);
			}
		}
		/**
		 * 获取当前课程ID和学生ID
		 */
		String currentClaId=claids.get(selectCourse.getSelectedIndex()),currentStuId=textfield.getText().trim();
		pageBean=stuDao.queryStuForTeacher(_pageBean,currentClaId, currentStuId);
		List<StudentBean>_stus=pageBean.getList();
		detailPs=new ArrayList<>();
		/**
		 * 把一维数组转化为二维
		 */
		int k=0;
		for(int i=0;i<Math.floor(_stus.size()/5);i++){
			for(int j=0;j<5;j++){
			DetailInfoPanel stuInfop=new DetailInfoPanel(recordDao,currentClaId,_stus.get(i*5+j));
			stuInfop.setBounds(j*130+3, i*135+70, 122, 92);
//			detailPs[i*5+j]=stuInfop;
			detailPs.add(stuInfop);
			k++;
			}
		}
		for(int i=0;i<_stus.size()%5;i++){
			DetailInfoPanel stuInfop=new DetailInfoPanel(recordDao,currentClaId,_stus.get(k+i));
			stuInfop.setBounds(i*130+3, (k/5)*135+70, 122, 92);
//			detailPs[k+i]=stuInfop;
			detailPs.add(stuInfop);
		}
		changPagePanel cpagePanel=new changPagePanel(pageBean.getTotalPage());
		cpagePanel.setBounds(2, 600, 650, 50);
		detailPs.add(cpagePanel);
		JPanel[] jpanls=detailPs.toArray(new JPanel[_stus.size()+1]);
		FrameUtil.baseSetFrame(TechListStuFrame.this,title, jpanls);
	}
}
