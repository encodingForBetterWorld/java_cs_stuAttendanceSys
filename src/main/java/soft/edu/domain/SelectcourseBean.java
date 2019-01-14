package soft.edu.domain;

import soft.edu.common.BaseEntity;

public class SelectcourseBean implements BaseEntity{
	private String selectcourse_id;
	private String class_id;
	private String stu_id;
	public String getSelectcourse_id() {
		return selectcourse_id;
	}
	public void setSelectcourse_id(String selectcourse_id) {
		this.selectcourse_id = selectcourse_id;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
}
