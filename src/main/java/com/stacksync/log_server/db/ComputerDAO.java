package com.stacksync.log_server.db;

import com.stacksync.log_server.exceptions.DAOException;
import com.stacksync.log_server.model.Computer;

public interface ComputerDAO {

	public void add(Computer computer) throws DAOException;

	public Computer findByName(String name) throws DAOException;
	
}
