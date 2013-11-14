package com.stacksync.log_server.model;

import java.util.Date;

public class Log {

	private Long id;
	private Report report;
	private Date logDate;
	private String priority;
	private String thread;
	private String filePackage;
	private String fileName;
	private Integer line;
	private String message;
	
	public Log() {
		this(null);
	}
	
	public Log(Long id) {
		this(id, null, null, null, null, null, null, null, null);
	}
	
	public Log(Long id, Report report, Date logDate, String priority, String thread, String filePackage,
			String fileName, Integer line, String message) {
		this.id = id;
		this.report = report;
		this.logDate = logDate;
		this.priority = priority;
		this.thread = thread;
		this.filePackage = filePackage;
		this.fileName = fileName;
		this.line = line;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String getFilePackage() {
		return filePackage;
	}

	public void setFilePackage(String filePackage) {
		this.filePackage = filePackage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
