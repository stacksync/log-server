package com.stacksync.log_server;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;

import com.stacksync.log_server.model.Computer;
import com.stacksync.log_server.model.Log;
import com.stacksync.log_server.model.Report;

public class LogReader implements Iterator<Log> {
	
	private static final Logger logger = Logger.getLogger(LogReader.class.getName());
	
	private String pattern = "\\[[0-9]*-[0-9]*-[0-9]* [0-9]*:[0-9]*:[0-9]*\\] ";
	private Pattern pat;
	
	private Scanner scanner;
	
	private Computer computer;
	private Report report;
	
	public LogReader(String log) throws IOException {
		
		logger.debug("Creating LogReader.");
		
		//this.logFile = new File(log);
		GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(log));
		this.scanner = new Scanner(gzis);
		
		this.pat = Pattern.compile(pattern, Pattern.MULTILINE);
		this.scanner.useDelimiter(pat);
		
		this.initializeDate(log); // Computer and report
		
	}
	
	private void initializeDate(String log) {
		String[] logName = log.split("__");
		
		String computerName = logName[0];
		this.computer = new Computer(null, computerName);
		logger.debug("Computer created: " + this.computer.toString());
		
		//Report
		String dateReaded = logName[1].split("\\.")[0];
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = null;
		try {
			date = (Date)formatter.parse(dateReaded);
		} catch (ParseException e) {
			logger.error("Error parsing the report date:", e);
		}
		
		this.report = new Report(null, this.computer, date);
		logger.debug("Report created: " + this.report.toString());
		
	}

	public Computer getComputer() {
		return computer;
	}
	
	public Report getReport() {
		return report;
	}

	@Override
	public boolean hasNext() {
		return this.scanner.hasNext();
	}

	@Override
	public Log next() {
		
		Log log = new Log();
		String dateReaded = this.scanner.findInLine(pat);
		DateFormat formatter = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ");
		Date date;
		try {
			date = (Date)formatter.parse(dateReaded);
			log.setLogDate(date);				//date
		} catch (ParseException e) {
			logger.error("Error parsing the log date:", e);
		}
		
		String logStr = this.scanner.next();
		String[] parts = logStr.split(" - ");
		
		String message = parts[1];
		log.setMessage(message);				//message
		
		String firstPart = parts[0];
		Scanner logReader = new Scanner(firstPart);
		
		String priority = logReader.next();
		log.setPriority(priority);				//priority
		
		String thread = logReader.next();
		log.setThread(thread);					//thread
		
		if (thread.equals("AMQP")) {
			logReader.close();
			return null;
		}
		
		String[] fileInfo = logReader.next().split("\\.");
		String filePackage = fileInfo[0];
		log.setFilePackage(filePackage);		//package
		
		String fileName = fileInfo[1].split(":")[0];
		log.setFileName(fileName);				//file name
		
		Integer line = Integer.parseInt(fileInfo[1].split(":")[1]);
		log.setLine(line);						//line
		
		logReader.close();
		return log;
	}

	@Override
	public void remove() {
		
		this.scanner.close();
	}

}
