package soft.edu.view;

import soft.edu.common.BackgroundImagePanel;
import soft.edu.common.SoftConfig;
import soft.edu.dao.IRecordDao;
import soft.edu.dao.ITeacherDao;
import soft.edu.dao.RecordDaoImpl;
import soft.edu.dao.TeacherDaoImpl;
import soft.edu.domain.AttendanceBean;
import soft.edu.domain.ClassBean;
import soft.edu.domain.StudentBean;
import soft.edu.domain.TeacherBean;
import soft.edu.ui.DateCellEditor;
import soft.edu.ui.DateChooser;
import soft.edu.ui.RoundRectButton;
import soft.edu.ui.TechListTableCellRenderer;
import soft.edu.util.FrameUtil;
import soft.edu.util.ImageIconUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class TechMainFrame extends JFrame {
			/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
			JFrame parentFrame;
			private ButtonAction bta = new ButtonAction();
			private String tech_id,tech_name,tech_photo;
			private JPanel tempjp=new JPanel();
			private JTextField textfield = new JTextField(20);
			private Border border = SoftConfig.shadowborder;
			private IRecordDao recordDao = new RecordDaoImpl();
			private ITeacherDao techDao = new TeacherDaoImpl();
			private JButton queryButton = new JButton("查询"),
					listTechButton = new RoundRectButton("查看个人资料",120,50),
					updateInfoButton = new RoundRectButton("修改个人资料",120,50),
					updatepwdButton = new RoundRectButton("修改个人密码",120,50),
					listStuButton = new RoundRectButton("查看学生信息",120,50),
					logoutButton = new JButton("注销"),
					delBtn=new JButton("删除选中项"),
					updateBtn=new JButton("修改选中项");
			/*
			 * 桌面渲染器
			 */
			private TechListTableCellRenderer tableRender=new TechListTableCellRenderer();
			public TechMainFrame(String tech_id){
				this.tech_id = tech_id;
				TeacherBean teacher = new TeacherBean();
				teacher = techDao.queryTeacherById(tech_id);
				this.tech_photo = teacher.getTeacher_photo();
				this.tech_name = teacher.getTeacher_name();
				setBounds(SoftConfig.screenWidth/5,SoftConfig.screenHeight/5, 680, 550);
				setLayout(null); //设置空布局     
				JPanel01 jp1=new JPanel01(tech_name);
				jp1.setBounds(10, 0, 150, 185);
				JPanel02 jp2=new JPanel02();
				jp2.setBounds(10, 185, 150, 327);
				JPanel03 jp3=new JPanel03();
				jp3.setBounds(180, 0, 335, 90);
				JPanel04 jp4=new JPanel04();
				jp4.setBounds(520, 0, 150, 90);
				FrameUtil.baseSetFrame(this,"欢迎您，尊敬的"+tech_name+"老师！", jp1,jp2,jp3,jp4,tempjp);
			}
			private class JPanel01 extends BackgroundImagePanel{
				/**
				 * @Fields serialVersionUID : TODO
				 */
				private static final long serialVersionUID = 1L;
				private JLabel iconlabel,idlabel;
				public JPanel01(String stu_name) {
					super(SoftConfig.panel_bgimg);
					// TODO Auto-generated constructor stub
					iconlabel = new JLabel();
					iconlabel.setSize(145,135);
					idlabel = new JLabel();
					iconlabel.setIcon(ImageIconUtil.setImageIcon("/photos/"+tech_photo, iconlabel));
					idlabel.setFont(SoftConfig.mfont);
					idlabel.setForeground(Color.white);
					idlabel.setText(stu_name);
					add(iconlabel,new FlowLayout(FlowLayout.CENTER));
					add(idlabel,new FlowLayout(FlowLayout.CENTER));
					setBorder(border); //设置边框    
				}
			}
			private class JPanel02 extends BackgroundImagePanel{
				/**
				 * @Fields serialVersionUID : TODO
				 */
				private static final long serialVersionUID = 1L;

				public JPanel02() {
					super(SoftConfig.panel_bgimg);
					// TODO Auto-generated constructor stub
					setSize(150, 327);
					setIconImage(new ImageIcon(SoftConfig.SRC_PATH + "/images/frametitle.png").getImage());
					FrameUtil.baseSetBtn(this, bta, 15, 120, 50, SoftConfig.font, listTechButton,updateInfoButton,updatepwdButton,listStuButton);
					logoutButton.setIcon(ImageIconUtil.setBtImageIcon("/images/Logout.png", logoutButton,20,20));
					logoutButton.addActionListener(bta);
					logoutButton.setBounds(25, 270, 100, 30);
					add(logoutButton); 
					setBorder(border);
				}
			}
			private class JPanel03 extends BackgroundImagePanel{
				/**
				 * @Fields serialVersionUID : TODO
				 */
				private static final long serialVersionUID = 1L;
				private JLabel tiplabel;
				public JPanel03() {
					super(SoftConfig.panel_bgimg);
					// TODO Auto-generated constructor stub
					tiplabel=new JLabel("请输入学生学号查询其考勤记录");
					tiplabel.setFont(SoftConfig.mfont);
					tiplabel.setForeground(Color.white);
					queryButton.addActionListener(bta);
					add(tiplabel,new FlowLayout(FlowLayout.CENTER));
					add(textfield,new FlowLayout(FlowLayout.LEFT));
					add(queryButton,new FlowLayout(FlowLayout.RIGHT));
					setBorder(border);
				}
			}
			private class JPanel04 extends BackgroundImagePanel{
				/**
				 * @Fields serialVersionUID : TODO
				 */
				private static final long serialVersionUID = 1L;

				public JPanel04(){
					super(SoftConfig.panel_bgimg);
					updateBtn.setIcon(ImageIconUtil.setBtImageIcon("/images/update.png", updateBtn,20,20));
					delBtn.setIcon(ImageIconUtil.setBtImageIcon("/images/delete.png", delBtn,20,20));
					add(updateBtn);
					add(delBtn);
					setBorder(border);
				}
			}
			private class ButtonAction implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					//考勤记录的唯一识别
					List<String> attids = new ArrayList<>();
					int buttontype=0;
					if(e.getSource().equals(listTechButton))buttontype=1;
					else if(e.getSource().equals(updatepwdButton))buttontype=2;
					else if(e.getSource().equals(updateInfoButton))buttontype=3;
					else if(e.getSource().equals(listStuButton))buttontype=4;
					else if(e.getSource().equals(logoutButton))buttontype=5;
					switch(buttontype){
					case 0:
						List<Object> objs = recordDao.queryAttForTech(textfield.getText().trim(),tech_id);
						if(objs.size()!=0){
							Object[][]datas = new Object[objs.size()/3][8];
							int j=0;
							for(int i = 0;i<objs.size();i+=3){
								StudentBean stu = (StudentBean) objs.get(i);
								ClassBean cla = (ClassBean) objs.get(i+1);
								AttendanceBean att = (AttendanceBean)objs.get(i+2);
								datas[j][0]=stu.getStu_id();
								datas[j][1]=stu.getStu_name();
								datas[j][2]=cla.getClass_id();
								datas[j][3]=cla.getCourse_name();
								datas[j][4]=att.getRecordtime();
								attids.add(att.getAtt_id());
								datas[j][5]=SoftConfig.ByteToStr(att.getRecordstatus());
								datas[j][6]="确认修改";
								datas[j][7]="确认删除";
								j++;
							}
							/*
							 * 局部刷新显示表单
							 */
							TechMainFrame.this.remove(tempjp);
							String[] columns={"学生学号","学生姓名","课程编号","课程名称","考勤日期","考勤结果","修改","删除"};
							TableModel tableModel=new DefaultTableModel(datas,columns);
							JTable listInfo = new JTable(tableModel);
							TableColumnModel tableColModel=listInfo.getColumnModel();
							/*
							 * 在特定列设置下拉框
							 */
							JComboBox<String> attStatusCombo=new JComboBox<>();
							String[] items={"正常","迟到","旷课","早退","请假"};
							for(String item:items){
								attStatusCombo.addItem(item);
							}
							/*
							 * 在特定列设置复选框
							 */
							JCheckBox updateCheck=new JCheckBox();
							JCheckBox delCheck=new JCheckBox();
							DateChooser dateChooser=new DateChooser();
							TableCellEditor slectece=new DefaultCellEditor(attStatusCombo),
									updatece=new DefaultCellEditor(updateCheck),
									delce=new DefaultCellEditor(delCheck);
							TableCellEditor datece=new DateCellEditor(dateChooser);
							/**
							 *编辑下拉框结束后,得到一个修改字段
							 */
//							slectece.addCellEditorListener(new CellEditorListener() {
//								
//								@Override
//								public void editingStopped(ChangeEvent e) {
//									// TODO Auto-generated method stub
//									try{
//									AttendanceBean att=new AttendanceBean();
//									int selectedRow=listInfo.getSelectedRow();
//									String attid=attids.get(selectedRow);
//									byte recordstatus=SoftConfig.StrToByte(attStatusCombo.getSelectedItem().toString());
//									att.setAtt_id(attid);
//									att.setRecordstatus(recordstatus);
//									System.out.println(attid+":"+recordstatus);							
//									}catch(IndexOutOfBoundsException ex){
//										System.out.println("第一次点进去会下标越界,郁闷");
//									}
//								}
//								
//								@Override
//								public void editingCanceled(ChangeEvent e) {
//									// TODO Auto-generated method stub
//									
//								}
//							});
							/**
							 * 每次点击改变背景
							 */
							updateCheck.addItemListener(new ItemListener() {
								
								@Override
								public void itemStateChanged(ItemEvent e) {
									// TODO Auto-generated method stub
									TechMainFrame.this.remove(tempjp);
									listInfo.setDefaultRenderer(Object.class, tableRender);
									JScrollPane scrollPane = new JScrollPane(listInfo, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
									scrollPane.setBorder(border);
									JPanel listjp = new JPanel();
									listjp.setBounds(160, 90, 540, 445);
									listjp.add(scrollPane);
									tempjp=listjp;
									FrameUtil.baseSetFrame(TechMainFrame.this,"欢迎您，尊敬的"+tech_name+"老师！", tempjp);
								}
							});
							/**
							 * 每次点击画红线
							 */
							delCheck.addItemListener(new ItemListener() {
								
								@Override
								public void itemStateChanged(ItemEvent e) {
									// TODO Auto-generated method stub
									TechMainFrame.this.remove(tempjp);
									listInfo.setDefaultRenderer(Object.class, tableRender);
									JScrollPane scrollPane = new JScrollPane(listInfo, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
									scrollPane.setBorder(border);
									JPanel listjp = new JPanel();
									listjp.setBounds(160, 90, 540, 445);
									listjp.add(scrollPane);
									tempjp=listjp;
									FrameUtil.baseSetFrame(TechMainFrame.this,"欢迎您，尊敬的"+tech_name+"老师！", tempjp);
								}
							});
							/**
							 * 批量修改表单按钮的绑定事件
							 */
							updateBtn.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									List<AttendanceBean> atts = new ArrayList<>();
									for(int i=0;i<listInfo.getRowCount();i++){
										/**
										 * 如果勾选了确认修改,加入批量修改队列
										 */
										if(listInfo.getValueAt(i, 6).toString().equals("true")){
											AttendanceBean att=new AttendanceBean();
											att.setAtt_id(attids.get(i));
											att.setRecordtime(listInfo.getValueAt(i, 4).toString());
											att.setRecordstatus(SoftConfig.StrToByte(listInfo.getValueAt(i, 5).toString()));
											System.out.println("更新日期为："+listInfo.getValueAt(i, 4).toString());
											atts.add(att);
											listInfo.setValueAt(false, i, 6);
										}
									}
//								for(AttendanceBean at:atts){
//									System.out.println("id:"+at.getAtt_id()+",time:"+at.getRecordtime()+",type:"+at.getRecordstatus());
//									}
									if(atts.size()!=0){
										if(recordDao.updateAtts(atts)!=0){
											JOptionPane.showMessageDialog(null, "修改完毕","提示",JOptionPane.INFORMATION_MESSAGE);
											queryButton.doClick();
										}
										else{
											JOptionPane.showMessageDialog(null, "修改失败","错误",JOptionPane.ERROR_MESSAGE);
										}
									}
								}
							});
							/**
							 * 批量删除表单按钮的绑定事件
							 */
							delBtn.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									List<String> delids = new ArrayList<>();
									for(int i=0;i<listInfo.getRowCount();i++){
										/**
										 * 如果勾选了确认删除,加入批量删除队列
										 */
										if(listInfo.getValueAt(i, 7).toString().equals("true")){
											delids.add(attids.get(i));
										}
									}
//								for(AttendanceBean at:atts){
//									System.out.println("id:"+at.getAtt_id()+",time:"+at.getRecordtime()+",type:"+at.getRecordstatus());
//									}
									if(delids.size()!=0){
										if(recordDao.delAtts(delids)!=0){
											JOptionPane.showMessageDialog(null, "删除完毕","提示",JOptionPane.INFORMATION_MESSAGE);
											queryButton.doClick();
										}
										else{
											JOptionPane.showMessageDialog(null, "删除失败","错误",JOptionPane.ERROR_MESSAGE);
										}
									}
								}
							});
							tableColModel.getColumn(4).setCellEditor(datece);
							tableColModel.getColumn(5).setCellEditor(slectece);
							tableColModel.getColumn(6).setCellEditor(updatece);
							tableColModel.getColumn(7).setCellEditor(delce);
							listInfo.setRowHeight(20);
							listInfo.setEnabled(true);
							JScrollPane scrollPane = new JScrollPane(listInfo, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							scrollPane.setBorder(border);
							JPanel listjp = new JPanel();
							listjp.setBounds(160, 90, 540, 445);
							listjp.add(scrollPane);
							tempjp=listjp;
							FrameUtil.baseSetFrame(TechMainFrame.this,"欢迎您，尊敬的"+tech_name+"老师！", tempjp);
						}else{
							JOptionPane.showMessageDialog(null, "您只能查看自己所授课程的学生考勤记录","提示",JOptionPane.INFORMATION_MESSAGE);
						}
						break;
					case 1:
						TechMainFrame.this.dispose();
						new TechInfoFrame(tech_id);
						break;
					case 2:
						UpdatePwdFrame updatepwdf = new UpdatePwdFrame(tech_id);
						updatepwdf.parentFrame = TechMainFrame.this;
						TechMainFrame.this.setVisible(false);
						break;
					case 3:
						TechMainFrame.this.dispose();
						new UpdateInfoFrame(tech_id,TechMainFrame.class);
						break;
					case 4:
						TechListStuFrame liststuf=new TechListStuFrame(tech_id, tech_name);
						liststuf.parentFrame=TechMainFrame.this;
						TechMainFrame.this.setVisible(false);
						break;
					case 5:
						TechMainFrame.this.dispose();
						new MainLoginFrame("您已注销完毕，欢迎再次使用");
						break;
				}		
			  }
			}
}
