package com.excilys.computer.database.core.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntroducedSuperiorException extends Exception{
	private static final long serialVersionUID = 1L;
	final private static Logger logger = LogManager.getLogger(IntroducedSuperiorException.class);
	
	public IntroducedSuperiorException() {
		logger.info("The discontinued date must be greater than the introduced date!\n");
	}
	
	final private String message = "The discontinued date must be greater than the introduced date!";
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
