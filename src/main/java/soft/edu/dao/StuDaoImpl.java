package soft.edu.dao;

import soft.edu.common.BaseDao;
import soft.edu.common.BaseEntity;
import soft.edu.common.PageBean;
import soft.edu.domain.SelectcourseBean;
import soft.edu.domain.StudentBean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class StuDaoImpl extends BaseDao<StudentBean> implements IStuDao {

	@Override
	public boolean login(String stu_id, String stu_pwd) {
		// TODO Auto-generated method stub
		String qureySql="select stu_name from studentinfo where stu_id = ? and stu_pwd = ? ";
		return super.login(stu_id, stu_pwd, qureySql);
	}

	@Override
	public StudentBean queryStuById(String stu_id) {
		// TODO Auto-generated method stub
		String qureySql="select * from studentinfo where stu_id = ? ";
		return super.queryItemId(stu_id, qureySql, new StudentBean());
	}

	@Override
	public int updateStuPwd(String stu_id, String oldpwd, String newpwd) {
		// TODO Auto-generated method stub
		String querySql = "select * from studentinfo where stu_id="+stu_id+" and stu_pwd="+oldpwd;
		String updateSql = "update studentinfo set stu_pwd='"+newpwd+"' where stu_id='"+stu_id+"'";
		return super.updatePwd(querySql, updateSql);
	}

	@Override
	public int updateStu(StudentBean stu) {
		// TODO Auto-generated method stub
		String updateSql = "update studentinfo set stu_name='"+stu.getStu_name()+
				"',college_name='"+stu.getCollege_name()+
				"',faculty_name='"+stu.getFaculty_name()+"',stu_email='"+stu.getStu_email()+
				"' where stu_id='"+stu.getStu_id()+"'";
		return super.update(updateSql);
	}

	@Override
	public int updateStuPhoto(String stu_photo,String stu_id) {
		// TODO Auto-generated method stub
		String updateSql = "update studentinfo set stu_photo='"+stu_photo+"' where stu_id='"+stu_id+"'";	
		return super.updatePhoto(updateSql);
	}
	/**
	 * 教师选择课程并输入学生学号查询学生信息
	 */
	@Override
	public PageBean queryStuForTeacher(PageBean pageBean,String cla_id,String stu_id) {
		// TODO Auto-generated method stub
		String qureySelectCourseSql="select * from selectcourse where class_id = ? ";
		String qureyStudentSql="select * from studentinfo where stu_id = ? ";
		List<StudentBean> stus=new ArrayList<>();
		List<String> stuids=new ArrayList<>();
		/*
		 * 如果没输入学号,查询该门课程下所有学生的信息
		 */
		if(stu_id.equals("")){
			try {
				List<BaseEntity> _sclas=super.queryItemsId(cla_id, qureySelectCourseSql, SelectcourseBean.class);
				for(BaseEntity scla:_sclas){
					SelectcourseBean _scla=(SelectcourseBean) scla;
					stuids.add(_scla.getStu_id());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			stuids.add(stu_id);
		}
		for(String stuid:stuids){
			StudentBean stu=super.queryItemId(stuid, qureyStudentSql, new StudentBean());
			stus.add(stu);
		}
		pageBean.setAllRows(stus.size());
		pageBean.setTotalPage(pageBean.getTotalPages(stus.size()));
		/*
		 * 如果最后一页,取余
		 */
		int currentPageOffset=pageBean.getCurrentPageOffset(),pageSize=pageBean.getPageSize().intValue();
		List<StudentBean> list=(pageBean.getCurrentPage()!=pageBean.getTotalPage())?
				stus.subList(currentPageOffset,currentPageOffset+pageSize):
					stus.subList(currentPageOffset,currentPageOffset+stus.size()%pageSize);
		pageBean.setList(list);
		
		
		return pageBean;
	}
	@Override
	public int insertStu(StudentBean stu) {
		// TODO Auto-generated method stub
		try {
			super.insert(stu, "studentinfo");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
