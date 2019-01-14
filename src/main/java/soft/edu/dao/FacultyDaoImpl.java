package soft.edu.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import soft.edu.common.DatabaseConnection;
import soft.edu.domain.FacultyBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacultyDaoImpl implements IFacultyDao {

	@Override
	public List<FacultyBean> queryFacultByCollege(String college_name) {
		// TODO Auto-generated method stub
		Connection conn = DatabaseConnection.getConnection();
		List<FacultyBean> facs = new ArrayList<>();
		String querySql = "select faculty_name from collegefaculty where college_name = ?";
		try{
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(querySql);
			pstmt.setString(1, college_name);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				FacultyBean fac = new FacultyBean();
				fac.setFaculty_name(rs.getString("faculty_name"));
				facs.add(fac);
			}
			DatabaseConnection.close(rs);
			DatabaseConnection.close(pstmt);
			}catch (SQLException e){
				System.out.println("数据异常");
				e.printStackTrace();
			}
		return facs;
	}

}
