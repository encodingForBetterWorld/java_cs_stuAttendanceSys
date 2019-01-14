package soft.edu.dao;

import soft.edu.domain.FacultyBean;

import java.util.List;

public interface IFacultyDao {
	public List<FacultyBean> queryFacultByCollege(String college_name);
}
