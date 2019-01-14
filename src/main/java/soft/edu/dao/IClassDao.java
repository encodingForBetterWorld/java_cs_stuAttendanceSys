package soft.edu.dao;

import soft.edu.domain.ClassBean;

import java.util.List;

public interface IClassDao {
	public List<ClassBean> queryClassForStu(String stu_id);
	public List<ClassBean> queryClassForTeacher(String tech_id);
	public ClassBean queryClassByID(String cla_id);
}
