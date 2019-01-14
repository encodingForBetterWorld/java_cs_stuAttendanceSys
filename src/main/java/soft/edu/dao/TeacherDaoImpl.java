package soft.edu.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import soft.edu.common.BaseDao;
import soft.edu.common.DatabaseConnection;
import soft.edu.domain.TeacherBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoImpl extends BaseDao<TeacherBean> implements ITeacherDao {

	@Override
	public List<Object> queryAllTeacher() {
		// TODO Auto-generated method stub
		Connection conn = DatabaseConnection.getConnection();
		List<Object> objs = new ArrayList<>();
		String querySql1 = "select teacher_id,teacher_name,faculty_name from teacherinfo where 1=1";
		String querySql2 = "select course_name from classinfo where teacher_id=";
		try{
			Statement stmt1 = (Statement) conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			Statement stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs1 = stmt1.executeQuery(querySql1);
			while(rs1.next()){
				List<String> courses= new ArrayList<>();
				TeacherBean teacher = new TeacherBean();
				teacher.setTeacher_name(rs1.getString("teacher_name"));
				teacher.setFaculty_name(rs1.getString("faculty_name"));
				String teacher_id = rs1.getString("teacher_id");
				ResultSet rs2 = stmt2.executeQuery(querySql2+teacher_id);
				while(rs2.next()){
					courses.add(rs2.getString("course_name"));
				}
				DatabaseConnection.close(rs2);
				objs.add(teacher);
				objs.add(courses);
			}
			DatabaseConnection.close(rs1);
			DatabaseConnection.close(stmt1);
			DatabaseConnection.close(stmt2);
			DatabaseConnection.close(conn);
			}catch(SQLException e){
				System.err.println("数据异常");
				e.printStackTrace();
			}
		
		return objs;
	}

	@Override
	public TeacherBean queryTeacherById(String tech_id) {
		// TODO Auto-generated method stub
		TeacherBean teacher=new TeacherBean();
		String qureySql="select * from teacherinfo where teacher_id = ? ";
		return super.queryItemId(tech_id, qureySql, teacher);
	}

	@Override
	public int updateTechPwd(String tech_id, String oldpwd, String newpwd) {
				String querySql = "select * from teacherinfo where teacher_id="+tech_id+" and teacher_pwd="+oldpwd;
				String updateSql = "update teacherinfo set teacher_pwd='"+newpwd+"' where teacher_id='"+tech_id+"'";
				return super.updatePwd(querySql, updateSql);
	}

	@Override
	public int updateTechPhoto(String tech_photo, String tech_id) {
		// TODO Auto-generated method stub
		String updateSql = "update teacherinfo set teacher_photo='"+tech_photo+"' where teacher_id='"+tech_id+"'";	
		return super.updatePhoto(updateSql);
	}

	@Override
	public int updateTech(TeacherBean teacher) {
		// TODO Auto-generated method stub
		String updateSql = "update teacherinfo set teacher_name='"+teacher.getTeacher_name()+
				"',college_name='"+teacher.getCollege_name()+
				"',faculty_name='"+teacher.getFaculty_name()+"',teacher_email='"+teacher.getTeacher_email()+
				"' where teacher_id='"+teacher.getTeacher_id()+"'";
		return super.update(updateSql);
	}

	@Override
	public boolean login(String tech_id, String tech_pwd) {
		// TODO Auto-generated method stub
		String qureySql="select teacher_name from teacherinfo where teacher_id = ? and teacher_pwd = ? ";
		return super.login(tech_id, tech_pwd, qureySql);
	}
	

}
