package soft.edu.view;

import soft.edu.common.SoftConfig;
import soft.edu.dao.*;
import soft.edu.domain.CollegeBean;
import soft.edu.domain.FacultyBean;
import soft.edu.domain.StudentBean;
import soft.edu.domain.TeacherBean;
import soft.edu.util.FileUtil;
import soft.edu.util.FrameUtil;
import soft.edu.util.ImageIconUtil;
import soft.edu.util.RegexUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class UpdateInfoFrame extends JFrame {
	private JButton submitButton = new JButton("确认修改"),
			returnButton = new JButton("返回上级菜单"),
			updatePhotoButton = new JButton("修改个人照片");
	private JLabel label1,label2,label3 = new JLabel("所属学院"),label4 = new JLabel("所属专业"),label5 = new JLabel("电子邮箱");
	private JTextField text1,text2,text3;
	private JComboBox<String> selectCollege,selectFaculty;
	private IStuDao stuDao = new StuDaoImpl();
	private ITeacherDao techDao = new TeacherDaoImpl();
	private ICollegeDao colDao = new CollegeDaoImpl();
	private IFacultyDao facDao = new FacultyDaoImpl();
	private String photo;
	public UpdateInfoFrame(String _id,Class<?> _cla){
		setBounds(SoftConfig.screenWidth/5,SoftConfig.screenHeight/5, 300, 350);
		InfoPanel infopanel = new InfoPanel(_id,_cla);
		FrameUtil.baseSetFrame(this,"修改个人信息", infopanel);	
	}
	private class InfoPanel extends JPanel{
		private JPanel textInfoPanel=new JPanel(),photoPanel=new JPanel(),buttonPanel = new JPanel();
		private JLabel tipLabel = new JLabel("个人照片"),photoLabel = new JLabel();
		public InfoPanel(String _id,Class<?> _cla){
			setLayout(null);
			selectCollege = new JComboBox<>();
			selectFaculty = new JComboBox<>();
			for(CollegeBean col:colDao.queryAllCollege()){
				selectCollege.addItem(col.getCollege_name());
			}
			if(_cla==StuMainFrame.class||_cla==StuInfoFrame.class){
				label1=new JLabel("学生序号");
				label2 = new JLabel("学生姓名");
				StudentBean stu =new StudentBean();
				stu = stuDao.queryStuById(_id);
				text1 = new JTextField(stu.getStu_id(),10);
				text1.setEditable(false);
				text2 = new JTextField(stu.getStu_name(),10);
				text3 = new JTextField(stu.getStu_email(),10);
				for(FacultyBean fac:facDao.queryFacultByCollege(stu.getCollege_name())){
					selectFaculty.addItem(fac.getFaculty_name());
				}
				selectCollege.setSelectedItem(stu.getCollege_name());
				selectFaculty.setSelectedItem(stu.getFaculty_name());
				photo=stu.getStu_photo();
			}
			else if(_cla==TechMainFrame.class||_cla==TechInfoFrame.class){
				label1=new JLabel("教师姓名");
				label2 = new JLabel("教师工号");
				TeacherBean tech =new TeacherBean();
				tech = techDao.queryTeacherById(_id);
				text1 = new JTextField(tech.getTeacher_id(),10);
				text1.setEditable(false);
				text2 = new JTextField(tech.getTeacher_name(),10);
				text3 = new JTextField(tech.getTeacher_email(),10);
				for(FacultyBean fac:facDao.queryFacultByCollege(tech.getCollege_name())){
					selectFaculty.addItem(fac.getFaculty_name());
				}
				selectCollege.setSelectedItem(tech.getCollege_name());
				selectFaculty.setSelectedItem(tech.getFaculty_name());
				photo=tech.getTeacher_photo();
				System.out.println(photo);
			}
			photoLabel.setSize(135,150);
			photoLabel.setIcon(ImageIconUtil.setImageIcon("/photos/"+photo, photoLabel));
			textInfoPanel.add(label1);
			textInfoPanel.add(text1);
			textInfoPanel.add(label2);
			textInfoPanel.add(text2);
			textInfoPanel.add(label3);
			selectCollege.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					selectFaculty.removeAllItems();
					for(FacultyBean fac:facDao.queryFacultByCollege(selectCollege.getSelectedItem().toString().trim())){
						selectFaculty.addItem(fac.getFaculty_name());
					}
				}
			});
			textInfoPanel.add(selectCollege);
			textInfoPanel.add(label4);
			textInfoPanel.add(selectFaculty);
			textInfoPanel.add(label5);
			textInfoPanel.add(text3);
			textInfoPanel.setBounds(0, 0, 130, 250);
			photoPanel.add(tipLabel);
			photoPanel.add(photoLabel);
			updatePhotoButton.setSize(120,20);
			updatePhotoButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JFileChooser jfile = new JFileChooser();
					jfile.setFileFilter(new FileNameExtensionFilter("image files", "jpg","png","gif"));
					jfile.setCurrentDirectory(new File(SoftConfig.SRC_PATH + "/images/"));
					int ischoose = jfile.showOpenDialog(UpdateInfoFrame.this);
					if(ischoose==jfile.APPROVE_OPTION){
						/**
						 * 删除之前上传的头像
						 */
						File f = new File( SoftConfig.SRC_PATH + "/photos/"+photo);
						if(f.exists()){
							f.delete();
						}
						String imgpath = jfile.getSelectedFile().getPath();
						ImageIcon _photo =new ImageIcon(imgpath);
						_photo.setImage(_photo.getImage().getScaledInstance(135,150, Image.SCALE_DEFAULT));
						InfoPanel.this.photoLabel.setIcon(_photo);
						String newfile = SoftConfig.SRC_PATH + "/photos/"+UUID.randomUUID()+"."+RegexUtil.getFileExt(imgpath);
						System.out.println("new"+newfile+"old"+imgpath);
						FileUtil.FileCopy(imgpath, newfile);
						photo=RegexUtil.getFileName(newfile);
						if( _cla==StuMainFrame.class||_cla==StuInfoFrame.class){
							int rVal=stuDao.updateStuPhoto(photo,_id);
							if(rVal!=-1){
								JOptionPane.showMessageDialog(null, "头像已更新完毕！","提示",JOptionPane.INFORMATION_MESSAGE);
							}else{
								JOptionPane.showMessageDialog(null, "头像更新失败","错误提示",JOptionPane.ERROR_MESSAGE);
							}
						}
						else if(_cla==TechMainFrame.class||_cla==TechInfoFrame.class){
							int rVal=techDao.updateTechPhoto(photo,_id);
							if(rVal!=-1){
								JOptionPane.showMessageDialog(null, "头像已更新完毕！","提示",JOptionPane.INFORMATION_MESSAGE);
							}else{
								JOptionPane.showMessageDialog(null, "头像更新失败","错误提示",JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			});
			photoPanel.add(updatePhotoButton);
			photoPanel.setBounds(150,0,140,250);
			submitButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if( _cla==StuMainFrame.class||_cla==StuInfoFrame.class){
						StudentBean stu = new StudentBean();
						stu.setStu_id(text1.getText().trim());
						stu.setStu_name(text2.getText().trim());
						stu.setStu_email(text3.getText().trim());
						stu.setCollege_name(selectCollege.getSelectedItem().toString().trim());
						stu.setFaculty_name(selectFaculty.getSelectedItem().toString().trim());
						stu.setStu_photo(photo);
						int resultVal = stuDao.updateStu(stu);
						if(resultVal!=-1){
							JOptionPane.showMessageDialog(null, "恭喜！修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null, "很遗憾！修改失败了","错误提示",JOptionPane.ERROR_MESSAGE);
						}
					}
					else if(_cla==TechMainFrame.class||_cla==TechInfoFrame.class){
						TeacherBean tech=new TeacherBean();
						tech.setTeacher_id(text1.getText().trim());
						tech.setTeacher_name(text2.getText().trim());
						tech.setTeacher_email(text3.getText().trim());
						tech.setCollege_name(selectCollege.getSelectedItem().toString().trim());
						tech.setFaculty_name(selectFaculty.getSelectedItem().toString().trim());
						tech.setTeacher_photo(photo);
						int resultVal = techDao.updateTech(tech);
						if(resultVal!=-1){
							JOptionPane.showMessageDialog(null, "恭喜！修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null, "很遗憾！修改失败了","错误提示",JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
			buttonPanel.add(submitButton);
			returnButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					UpdateInfoFrame.this.dispose();
					/**
					 * 反射获取上级对象，动态生成调用其构造函数，实例化
					 */
						try {
							Constructor<?> cons=_cla.getConstructor(String.class);
							cons.newInstance(_id);
						} catch (NoSuchMethodException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SecurityException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InstantiationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalArgumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
			});
			buttonPanel.add(returnButton);
			buttonPanel.setBounds(0,270,300,40);
			add(textInfoPanel);
			add(photoPanel);
			add(buttonPanel);
		}
	}
}
