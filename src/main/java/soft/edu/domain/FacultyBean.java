package soft.edu.domain;

import soft.edu.common.BaseEntity;

public class FacultyBean implements BaseEntity{
	private String faculty_name,faculty_addr,faculty_contact,faculty_tel;

	public String getFaculty_name() {
		return faculty_name;
	}

	public void setFaculty_name(String faculty_name) {
		this.faculty_name = faculty_name;
	}

	public String getFaculty_addr() {
		return faculty_addr;
	}

	public void setFaculty_addr(String faculty_addr) {
		this.faculty_addr = faculty_addr;
	}

	public String getFaculty_contact() {
		return faculty_contact;
	}

	public void setFaculty_contact(String faculty_contact) {
		this.faculty_contact = faculty_contact;
	}

	public String getFaculty_tel() {
		return faculty_tel;
	}

	public void setFaculty_tel(String faculty_tel) {
		this.faculty_tel = faculty_tel;
	}
	
}
