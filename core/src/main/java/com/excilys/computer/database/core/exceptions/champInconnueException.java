package com.excilys.computer.database.core.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class champInconnueException extends Exception{
	private static final long serialVersionUID = 1L;
	final private static Logger logger = LogManager.getLogger(champInconnueException.class);
	
	final private String message = "Enter a valid attribute please!";
	
	public champInconnueException() {
		logger.info("this attribute is unknown!\n");
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
