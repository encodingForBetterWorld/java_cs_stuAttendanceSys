package soft.edu.dao;

import soft.edu.domain.TeacherBean;

import java.util.List;

public interface ITeacherDao {
	public boolean login(String tech_id, String tech_pwd);
	public List<Object> queryAllTeacher();
	public TeacherBean queryTeacherById(String tech_id);
	public int updateTechPwd(String tech_id, String oldpwd, String newpwd);
	public int updateTechPhoto(String tech_photo, String tech_id);
	public int updateTech(TeacherBean teacher);
}
