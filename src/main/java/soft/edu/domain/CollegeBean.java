package soft.edu.domain;

import soft.edu.common.BaseEntity;

public class CollegeBean implements BaseEntity{
	private String college_name,college_addr,college_contact,college_tel;

	public String getCollege_name() {
		return college_name;
	}

	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}

	public String getCollege_addr() {
		return college_addr;
	}

	public void setCollege_addr(String college_addr) {
		this.college_addr = college_addr;
	}

	public String getCollege_contact() {
		return college_contact;
	}

	public void setCollege_contact(String college_contact) {
		this.college_contact = college_contact;
	}

	public String getCollege_tel() {
		return college_tel;
	}

	public void setCollege_tel(String college_tel) {
		this.college_tel = college_tel;
	}
	
}
