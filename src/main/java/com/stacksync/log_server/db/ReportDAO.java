package com.stacksync.log_server.db;

import com.stacksync.log_server.exceptions.DAOException;
import com.stacksync.log_server.model.Report;

public interface ReportDAO {

	public void add(Report report) throws DAOException;

	public void update(Report report) throws DAOException;
	
	public void remove(Long id) throws DAOException;
	
}
