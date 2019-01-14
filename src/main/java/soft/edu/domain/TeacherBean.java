package soft.edu.domain;

import soft.edu.common.BaseEntity;

public class TeacherBean implements BaseEntity{
	private String teacher_id;
	private String faculty_name;
	private String college_name;
	private String teacher_name;
	private String teacher_pwd;
	private String teacher_email;
	private String teacher_photo;

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getFaculty_name() {
		return faculty_name;
	}

	public void setFaculty_name(String faculty_name) {
		this.faculty_name = faculty_name;
	}

	public String getCollege_name() {
		return college_name;
	}

	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getTeacher_pwd() {
		return teacher_pwd;
	}

	public void setTeacher_pwd(String teacher_pwd) {
		this.teacher_pwd = teacher_pwd;
	}

	public String getTeacher_email() {
		return teacher_email;
	}

	public void setTeacher_email(String teacher_email) {
		this.teacher_email = teacher_email;
	}

	public String getTeacher_photo() {
		return teacher_photo;
	}

	public void setTeacher_photo(String teacher_photo) {
		this.teacher_photo = teacher_photo;
	}
	
}
