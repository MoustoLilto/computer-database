package com.excilys.computer.database.core.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PageLimitException extends Exception{
	private static final long serialVersionUID = 1L;
	final private static Logger logger = LogManager.getLogger(PageLimitException.class);

	public PageLimitException() {
		logger.info("Enter a valid number of page please!\n");
	}
	
	final private String message = "Enter a valid number of page please!";
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
