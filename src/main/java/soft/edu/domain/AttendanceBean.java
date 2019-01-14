package soft.edu.domain;

import soft.edu.common.BaseEntity;

public class AttendanceBean implements BaseEntity{
	private String att_id;
	private String selectcourse_id;
	private String recordtime;
	private Byte recordstatus = 0;
	public String getAtt_id() {
		return att_id;
	}
	public void setAtt_id(String att_id) {
		this.att_id = att_id;
	}
	public String getSelectcourse_id() {
		return selectcourse_id;
	}
	public void setSelectcourse_id(String selectcourse_id) {
		this.selectcourse_id = selectcourse_id;
	}
	public String getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(String recordtime) {
		this.recordtime = recordtime;
	}
	public Byte getRecordstatus() {
		return recordstatus;
	}
	public void setRecordstatus(Byte recordstatus) {
		this.recordstatus = recordstatus;
	}
	
}