package soft.edu.domain;

import soft.edu.common.BaseEntity;

public class StudentBean implements BaseEntity{
	private String stu_id;
	private String college_name;
	private String stu_name;
	private String stu_pwd;
	private String faculty_name;
	private String stu_email;
	private String stu_photo;
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getCollege_name() {
		return college_name;
	}
	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public String getStu_pwd() {
		return stu_pwd;
	}
	public void setStu_pwd(String stu_pwd) {
		this.stu_pwd = stu_pwd;
	}
	public String getFaculty_name() {
		return faculty_name;
	}
	public void setFaculty_name(String faculty_name) {
		this.faculty_name = faculty_name;
	}
	public String getStu_email() {
		return stu_email;
	}
	public void setStu_email(String stu_email) {
		this.stu_email = stu_email;
	}
	public String getStu_photo() {
		return stu_photo;
	}
	public void setStu_photo(String stu_photo) {
		this.stu_photo = stu_photo;
	}
}
