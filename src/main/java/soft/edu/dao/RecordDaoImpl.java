package soft.edu.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import soft.edu.common.BaseDao;
import soft.edu.common.DatabaseConnection;
import soft.edu.domain.AttendanceBean;
import soft.edu.domain.ClassBean;
import soft.edu.domain.StudentBean;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecordDaoImpl extends BaseDao<AttendanceBean> implements IRecordDao {
	/*
	 * 需要先验证用户输入的数据是否正确
	 */
	@Override
	public String checkClassById(String class_info) {
		// TODO Auto-generated method stub
		Connection conn = DatabaseConnection.getConnection();
		String id=null,qureySql="select class_id from classinfo where class_id = ? ";
		if(class_info.trim().equals("")){
			return "ok";
		}
		try{
		PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(qureySql);
		pstmt.setString(1, class_info);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			id = rs.getString("class_id");
		}
		DatabaseConnection.close(rs);
		DatabaseConnection.close(pstmt);
		}catch (SQLException e){
			System.out.println("数据异常");
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public List<Object> queryAttForStu(String stu_id, String class_info) {
		// TODO Auto-generated method stub
		Connection conn = DatabaseConnection.getConnection();
		String querySql1 = "select att_id,recordtime,recordstatus from attendanceinfo where selectcourse_id=";
		String querySql2 = "select selectcourse_id,class_id from selectcourse where stu_id="+stu_id;
		String querySql3 = "select * from classinfo where ";
		List<Object> objs = new ArrayList<>();
		/**
		 * _infos的偶数项存放选课id,奇数项存放课程id
		 */
		List<String> _infos = new ArrayList<>();
			try{
				Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				Statement stmt1 = (Statement) conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				Statement stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				Statement stmt3 = (Statement) conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				/**
				 * 如果没有输入信息则查询所有考勤记录
				 */
				if(class_info.trim().equals("")){
					System.out.println("查询"+stu_id+"所有考勤记录");
					 ResultSet rs2 = stmt.executeQuery(querySql2.toString());
					 while(rs2.next()){
						 _infos.add(rs2.getString("selectcourse_id"));
						 _infos.add(rs2.getString("class_id"));
					 }
					 DatabaseConnection.close(rs2);	
					 int n=0;
					 for(int k=0;k<_infos.size()/2;k++){  
						    /*
						     * 通过选课表唯一标示查询考勤表得到考勤信息
						     */
							ResultSet rs1 = stmt1.executeQuery(querySql1+"'"+_infos.get(n)+"'");
							/*
							 * 查询课程表得到课程信息
							 */
							ResultSet rs3 = stmt2.executeQuery(querySql3+"class_id="+_infos.get(n+1));
							while(rs1.next()){
								AttendanceBean att = new AttendanceBean();
								att.setAtt_id(rs1.getString("att_id"));
								att.setRecordtime(rs1.getString("recordtime"));
								att.setRecordstatus(rs1.getByte("recordstatus"));
								ClassBean cla = new ClassBean();
								if(rs3.next()){
								cla.setCourse_name(rs3.getString("course_name"));
								cla.setClass_id(rs3.getString("class_id"));
								}
								objs.add(att);
								objs.add(cla);
							}
							DatabaseConnection.close(rs1);
							DatabaseConnection.close(rs3);
							n+=2;
						  }
					 }
				/**
				 * 如果输入了信息,支持输入课程编号查询和输入课程名称查询考勤记录
				 */
				else{
					 /*
					  *首先判断输入的信息是课程名称还是课程id 
					  */
					ClassBean cla = new ClassBean();
					ResultSet rs3=stmt.executeQuery(querySql3+"class_id='"+class_info+"'");
					ResultSet rs4=stmt3.executeQuery(querySql3+"course_name='"+class_info+"'");
					/*
					 * 输入的是课程id
					 */
					if(rs3.next()){
						System.out.println("输入课程id进行查询");
						cla.setCourse_name(rs3.getString("course_name"));
						cla.setClass_id(rs3.getString("class_id"));
						ResultSet rs2=stmt1.executeQuery(querySql2+" and class_id="+class_info);
						if(rs2.next()){
							String csid=rs2.getString("selectcourse_id");
							ResultSet rs1=stmt2.executeQuery(querySql1+"'"+csid+"'");
							while(rs1.next()){
								AttendanceBean att = new AttendanceBean();
								att.setAtt_id(rs1.getString("att_id"));
								att.setRecordtime(rs1.getString("recordtime"));
								att.setRecordstatus(rs1.getByte("recordstatus"));
								objs.add(att);
								objs.add(cla);
							}
						}
						else{
							System.out.println("数据有误");
						}
					}
					/*
					 * 如果查询输入的是课程名称
					 */
					else if(rs4.next()){
						System.out.println("输入课程名称进行查询");
						cla.setCourse_name(rs4.getString("course_name"));
						cla.setClass_id(rs4.getString("class_id"));
						ResultSet rs2=stmt1.executeQuery(querySql2+" and class_id="+cla.getClass_id());
						if(rs2.next()){
							String csid=rs2.getString("selectcourse_id");
							ResultSet rs1=stmt2.executeQuery(querySql1+"'"+csid+"'");
							while(rs1.next()){
								AttendanceBean att = new AttendanceBean();
								att.setAtt_id(rs1.getString("att_id"));
								att.setRecordtime(rs1.getString("recordtime"));
								att.setRecordstatus(rs1.getByte("recordstatus"));
								objs.add(att);
								objs.add(cla);
							}
						}
						else{
							System.out.println("数据有误");
						}
					}
					else{
						System.out.println("输入有误");
					}
				}
				DatabaseConnection.close(stmt);
				DatabaseConnection.close(stmt1);
				DatabaseConnection.close(stmt2);
				DatabaseConnection.close(stmt3);
			}catch(SQLException e){
				System.err.println("数据异常");
				e.printStackTrace();
			}
		return objs;
	}

	@Override
	public List<Object> queryAttForTech(String stu_id, String tech_id) {
		// TODO Auto-generated method stub
		Connection conn = DatabaseConnection.getConnection();
		String querySql1 = "select class_id,course_name from classinfo where teacher_id="+tech_id;
		String querySql2 = "select * from selectcourse where class_id=";
		String querySql3 = "select * from attendanceinfo where selectcourse_id=";
		String querySql4 = "select * from studentinfo where stu_id=";
		List<Object> objs = new ArrayList<>();
		List<ClassBean> clas = new ArrayList<>();
			try{
				Statement stmt1 = (Statement) conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				Statement stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				Statement stmt3 = (Statement) conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				Statement stmt4 = (Statement) conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs1 = stmt1.executeQuery(querySql1);
				while(rs1.next()){
					ClassBean cla = new ClassBean();
					cla.setClass_id(rs1.getString("class_id"));
					cla.setCourse_name(rs1.getString("course_name"));
					clas.add(cla);
				}
				DatabaseConnection.close(stmt1);
				DatabaseConnection.close(rs1);
				ResultSet rs2;
				ResultSet rs3;
				ResultSet rs4;
				for(ClassBean cla:clas){
					/*
					 * 如果不填则查询出本教师所教学生的所有考勤记录
					 */
					if(stu_id.trim().equals("")){
						rs2 = stmt2.executeQuery(querySql2+"'"+cla.getClass_id()+"'");
					}
					/*
					 * 否则查询出输入学号对应的学生的考勤记录
					 */
					else{
						rs2 = stmt2.executeQuery(querySql2+"'"+cla.getClass_id()+"' and stu_id='"+stu_id+"'");
					}
					while(rs2.next()){
						/*
						 * 得到考勤字段加入objs
						 */
						rs3=stmt3.executeQuery(querySql3+"'"+rs2.getString("selectcourse_id")+"'");
						rs4=stmt4.executeQuery(querySql4+"'"+rs2.getString("stu_id")+"'");
						if(rs4.next()){
							/*
							 * 得到学生字段加入objs
							 */	
							StudentBean stu=new StudentBean();
							stu.setStu_id(rs4.getString("stu_id"));
							stu.setStu_name(rs4.getString("stu_name"));
							while(rs3.next()){
								AttendanceBean att=new AttendanceBean();
								att.setAtt_id(rs3.getString("att_id"));
								att.setRecordtime(rs3.getString("recordtime"));
								att.setRecordstatus(rs3.getByte("recordstatus"));
								objs.add(stu);
								objs.add(cla);
								objs.add(att);
							}
						}
						DatabaseConnection.close(rs3);
						DatabaseConnection.close(rs4);
					}
					DatabaseConnection.close(rs2);
				}
				DatabaseConnection.close(stmt2);
				DatabaseConnection.close(stmt3);
				DatabaseConnection.close(stmt4);
				DatabaseConnection.close(conn);
			}catch(SQLException e){
				System.err.println("数据异常");
				e.printStackTrace();
			}
		return objs;
	}
	@Override
	public int updateAtts(List<AttendanceBean> atts){
		int returnVal=0;
		for(AttendanceBean att:atts){
			String updateSql = "update attendanceinfo set recordtime='"+att.getRecordtime()+
					"',recordstatus="+att.getRecordstatus()+
					" where att_id='"+att.getAtt_id()+"'";
			returnVal=super.update(updateSql);
			}
		return returnVal;
	}

	@Override
	public int delAtts(List<String> delids) {
		// TODO Auto-generated method stub
		int returnVal=0;
		for(String delid:delids){
			String delSql="delete from attendanceinfo where att_id='"+delid+"'";
			super.del(delSql);
			returnVal=1;
			}
		return returnVal;
	}

	@Override
	public int insertAtt(AttendanceBean att) {
		// TODO Auto-generated method stub
		int returnVal=0;
		String schemaName="attendanceinfo";
		try {
			super.insert(att, schemaName);
			returnVal=1;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnVal;
	}
	@Override
	public String qureySid(String claid,String stuid){
		Connection conn = DatabaseConnection.getConnection();
		String selectcourseid="",qureySql="select selectcourse_id from selectcourse where class_id = ? and stu_id = ? ";
		/*
		 * 先查询选课表得到选课唯一标示
		 */
		try{
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(qureySql);
			pstmt.setString(1, claid);
			pstmt.setString(2, stuid);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				selectcourseid = rs.getString("selectcourse_id");
			}
			DatabaseConnection.close(rs);
			DatabaseConnection.close(pstmt);
			DatabaseConnection.close(conn);
		}catch (SQLException e){
			System.out.println("数据异常");
			e.printStackTrace();
		}
		return selectcourseid;
	}
}
