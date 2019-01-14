package soft.edu.view;

import soft.edu.common.SoftConfig;
import soft.edu.dao.IStuDao;
import soft.edu.dao.ITeacherDao;
import soft.edu.dao.StuDaoImpl;
import soft.edu.dao.TeacherDaoImpl;
import soft.edu.util.FrameUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePwdFrame extends JFrame{
	private JButton okButton = new JButton("确认修改"),returnButton = new JButton("返回上级菜单");
	private JLabel oldpwdl = new JLabel("旧密码"),newpwdl = new JLabel("新密码"),repeatpwdl = new JLabel("确认密码");
	private JPasswordField oldpwdpf=new JPasswordField(20),newpwdpf=new JPasswordField(20),repeatpwdpf=new JPasswordField(19);
	private Border border = BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black), "修改密码");
	private IStuDao stuDao = new StuDaoImpl();
	private ITeacherDao techDao = new TeacherDaoImpl();
	JFrame parentFrame;
	public UpdatePwdFrame(String _id){
		setBounds(SoftConfig.screenWidth/5,SoftConfig.screenHeight/5, 320, 200);
		JPanel content = new ContentPanel(_id);
		FrameUtil.baseSetFrame(this, "修改个人密码", content);
	}
	private class ContentPanel extends JPanel{
		private JPanel jp1 = new JPanel(),jp2 = new JPanel();
		public ContentPanel(String _id){
			setLayout(new BorderLayout());
			jp1.add(oldpwdl,new FlowLayout(FlowLayout.LEFT));
			jp1.add(oldpwdpf, new FlowLayout(FlowLayout.LEFT));
			jp1.add(newpwdl,new FlowLayout(FlowLayout.LEFT));
			jp1.add(newpwdpf, new FlowLayout(FlowLayout.LEFT));
			jp1.add(repeatpwdl,new FlowLayout(FlowLayout.LEFT));
			jp1.add(repeatpwdpf, new FlowLayout(FlowLayout.LEFT));
			jp1.setBorder(border);
			okButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(new String(oldpwdpf.getPassword()).equals("")&&new String(newpwdpf.getPassword()).equals(""))
						JOptionPane.showMessageDialog(null, "密码不能为空","错误提示",JOptionPane.ERROR_MESSAGE);
					else if(new String(repeatpwdpf.getPassword()).equals(new String(newpwdpf.getPassword()))){
						/**
						 * 修改学生密码
						 */
						if(parentFrame.getClass()==StuMainFrame.class){
							if(stuDao.updateStuPwd(_id,new String(oldpwdpf.getPassword()), new String(newpwdpf.getPassword()))!=-1){
								JOptionPane.showMessageDialog(null, "恭喜！修改学生密码成功！","提示",JOptionPane.INFORMATION_MESSAGE);
							}
							else{
								JOptionPane.showMessageDialog(null, "修改失败","错误提示",JOptionPane.ERROR_MESSAGE);
							}
						}
						/**
						 * 修改教师密码
						 */
						else if(parentFrame.getClass()==TechMainFrame.class){
							if(techDao.updateTechPwd(_id,new String(oldpwdpf.getPassword()), new String(newpwdpf.getPassword()))!=-1){
								JOptionPane.showMessageDialog(null, "恭喜！修改教师密码成功！","提示",JOptionPane.INFORMATION_MESSAGE);
							}
							else{
								JOptionPane.showMessageDialog(null, "修改失败","错误提示",JOptionPane.ERROR_MESSAGE);
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "修改失败","错误提示",JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "确认密码输入有误","错误提示",JOptionPane.ERROR_MESSAGE);
						repeatpwdpf.setText("");
					}
				}
			});
			jp2.add(okButton);
            returnButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					UpdatePwdFrame.this.setVisible(false);
					parentFrame.setVisible(true);
				}
			});
			jp2.add(returnButton);
			add(jp1,BorderLayout.CENTER);
			add(jp2,BorderLayout.SOUTH);
		}
	}

}
