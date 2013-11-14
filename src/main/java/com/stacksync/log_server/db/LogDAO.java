package com.stacksync.log_server.db;

import com.stacksync.log_server.exceptions.DAOException;
import com.stacksync.log_server.model.Log;

public interface LogDAO {

	public void add(Log log) throws DAOException;

	public void update(Log log) throws DAOException;
	
	public void remove(Long id) throws DAOException;
	
}
