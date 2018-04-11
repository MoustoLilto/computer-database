package com.excilys.computer.database.core.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class YearLimitException extends Exception{
	private static final long serialVersionUID = 1L;
	final private static Logger logger = LogManager.getLogger(YearLimitException.class);

	public YearLimitException() {
		logger.info("The year must be superior to 1970!\n");
	}
	
	final private String message = "The year must be superior to 1970!";
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
