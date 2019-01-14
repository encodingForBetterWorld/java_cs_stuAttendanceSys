package soft.edu.dao;

import soft.edu.common.BaseDao;
import soft.edu.common.BaseEntity;
import soft.edu.domain.SelectcourseBean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SelectCourseDaoImpl extends BaseDao<SelectcourseBean> {
	public int insertSelectCourse(SelectcourseBean scb){
		try {
			super.insert(scb, "selectcourse");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public List<SelectcourseBean> querySCbyStuId(String StuId){
		String querySql="select * from selectcourse where stu_id = ? ";
		List<SelectcourseBean> scbs=new ArrayList<>();
		try {
			List<BaseEntity> bases=super.queryItemsId(StuId, querySql, SelectcourseBean.class);
			for(BaseEntity base:bases){
				scbs.add((SelectcourseBean) base);
			}
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scbs;
	}

}
