package soft.edu.domain;

import soft.edu.common.BaseEntity;

public class ClassBean implements BaseEntity{
	private String class_id,teacher_id,course_name,class_time;
	private int class_start_week = 0,class_end_week = 0;
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getClass_time() {
		return class_time;
	}
	public void setClass_time(String class_time) {
		this.class_time = class_time;
	}
	public int getClass_start_week() {
		return class_start_week;
	}
	public void setClass_start_week(int class_start_week) {
		this.class_start_week = class_start_week;
	}
	public int getClass_end_week() {
		return class_end_week;
	}
	public void setClass_end_week(int class_end_week) {
		this.class_end_week = class_end_week;
	}

}
