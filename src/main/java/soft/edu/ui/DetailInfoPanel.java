package soft.edu.ui;

import soft.edu.common.BackgroundImagePanel;
import soft.edu.common.SoftConfig;
import soft.edu.dao.IRecordDao;
import soft.edu.domain.StudentBean;
import soft.edu.util.ImageIconUtil;
import soft.edu.view.AddAttRecordFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailInfoPanel extends BackgroundImagePanel {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -7751746919597494909L;
	private JLabel photoLabel = new JLabel(),nameLabel,idLabel,collegeLabel;
	private JButton addAttBtn = new RoundRectButton("考勤",55,28);
	public DetailInfoPanel(IRecordDao recordDao,String claId,StudentBean stu){
		super(SoftConfig.panel_bgimg1);
		setLayout(null);
		photoLabel.setSize(50,50);
		
		if(stu.getStu_photo()==null||stu.getStu_photo().trim().equals("")){
			photoLabel.setIcon(ImageIconUtil.setImageIcon("/images/Portrait.png", photoLabel));
		}else{
			photoLabel.setIcon(ImageIconUtil.setImageIcon("/photos/"+stu.getStu_photo(), photoLabel));
		}
		photoLabel.setBounds(3,3,50,72);
		add(photoLabel);
		nameLabel=new JLabel(stu.getStu_name());
		nameLabel.setBounds(53,13,60,20);
		nameLabel.setFont(SoftConfig.font);
		idLabel=new JLabel("学号:"+stu.getStu_id());
		idLabel.setBounds(8,65,92,20);
		add(nameLabel);
		add(idLabel);
		addAttBtn.setIcon(ImageIconUtil.setBtImageIcon("/images/Fingerprint.png", addAttBtn,22,22));
		addAttBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String sid=recordDao.qureySid(claId, stu.getStu_id());
				new AddAttRecordFrame(sid, claId, stu);
			}
		});
		addAttBtn.setBounds(58,40, 55,28);
		add(addAttBtn);
		this.setBorder(SoftConfig.shadowborder);
	}
}
