package soft.edu.dao;

import soft.edu.domain.AttendanceBean;

import java.util.List;

public interface IRecordDao {
	public List<Object> queryAttForStu(String stu_id, String class_id);
	public List<Object> queryAttForTech(String stu_id, String tech_id);
	public String checkClassById(String class_id);
	public int updateAtts(List<AttendanceBean> atts);
	public int delAtts(List<String> delids);
	public int insertAtt(AttendanceBean att);
	public String qureySid(String claid, String stuid);
}
