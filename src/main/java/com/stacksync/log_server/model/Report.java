package com.stacksync.log_server.model;

import java.util.Date;

public class Report {
	
	private Long id;
	private Computer computer;
	private Date uploadDate;
	
	public Report() {
		this(null);
	}
	
	public Report(Long id) {
		this(id, null, null);
	}
	
	public Report(Long id, Computer computer, Date uploadDate) {
		this.id = id;
		this.computer = computer;
		this.uploadDate = uploadDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Computer getComputer() {
		return computer;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	@Override
	public String toString() {
		
		String str;
		str = "{ID:"+this.id+
				", Computer:"+this.computer.toString()+
				", uploadDate:"+this.uploadDate.toString()+"}";
		
		return str;
	}

}
