package com.stacksync.log_server.model;

public class Computer {

	private Long id;
	private String name;
	
	public Computer() {
		this(null);
	}
	
	public Computer(Long id) {
		this(id, null);
	}
	
	public Computer(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		
		String str;
		str = "{ID:"+this.id+", name:"+this.name+"}";
		
		return str;
	}
	
}
