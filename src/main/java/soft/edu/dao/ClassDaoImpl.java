package soft.edu.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import soft.edu.common.BaseDao;
import soft.edu.common.BaseEntity;
import soft.edu.common.DatabaseConnection;
import soft.edu.domain.ClassBean;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassDaoImpl extends BaseDao<ClassBean> implements IClassDao {

	@Override
	public List<ClassBean> queryClassForStu(String stu_id) {
		// TODO Auto-generated method stub
		Connection conn = DatabaseConnection.getConnection();
		List<ClassBean> clas = new ArrayList<>();
		List<String>claids = new ArrayList<>();
		String querySql1 = "select class_id from selectcourse where stu_id = ?";
		String querySql2 = "select * from classinfo where class_id = ?";
		try {
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(querySql1);
			PreparedStatement subpstmt = (PreparedStatement) conn.prepareStatement(querySql2);
			pstmt.setString(1, stu_id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String classid = rs.getString("class_id");
				claids.add(classid);
			}
			for(String claid:claids){
				subpstmt.setString(1, claid);
				ResultSet subrs = subpstmt.executeQuery();
				if(subrs.next()){
					ClassBean cla = new ClassBean();
					cla.setClass_id(subrs.getString("class_id"));
					cla.setCourse_name(subrs.getString("course_name"));
					cla.setTeacher_id(subrs.getString("teacher_id"));
					cla.setClass_time(subrs.getString("class_time"));
					cla.setClass_start_week(subrs.getInt("class_start_week"));
					cla.setClass_end_week(subrs.getInt("class_end_week"));
					clas.add(cla);
				}
				DatabaseConnection.close(subrs);
			}
			DatabaseConnection.close(rs);
			DatabaseConnection.close(pstmt);
			DatabaseConnection.close(subpstmt);
			DatabaseConnection.close(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据异常");
			e.printStackTrace();
		}
		return clas;
	}

	@Override
	public List<ClassBean> queryClassForTeacher(String tech_id) {
		// TODO Auto-generated method stub
		String queryClassSql="select * from classinfo where teacher_id = ? ";
		List<ClassBean> clas=new ArrayList<>();
		try {
			List<BaseEntity> bases=super.queryItemsId(tech_id, queryClassSql, ClassBean.class);
			for(BaseEntity base:bases){
				ClassBean cla=(ClassBean)base;
				clas.add(cla);
			}
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clas;
	}

	@Override
	public ClassBean queryClassByID(String cla_id) {
		// TODO Auto-generated method stub
		String querySql="select * from classinfo where class_id = ? ";
		return super.queryItemId(cla_id, querySql, new ClassBean());
	}

}
