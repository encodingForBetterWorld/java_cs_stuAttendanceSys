package soft.edu.dao;

import soft.edu.common.PageBean;
import soft.edu.domain.StudentBean;

public interface IStuDao {
	public boolean login(String stu_id, String pwd);
	public StudentBean queryStuById(String stu_id);
	public int updateStuPwd(String stu_id, String oldpwd, String newpwd);
	public int updateStu(StudentBean stu);
	public int updateStuPhoto(String stu_photo, String stu_id);
	public int insertStu(StudentBean stu);
	public PageBean queryStuForTeacher(PageBean pageBean, String cla_id, String stu_id);
}
