package com.excilys.computer.database.core.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TuplesLimitException extends Exception{
	private static final long serialVersionUID = 1L;
	final private static Logger logger = LogManager.getLogger(TuplesLimitException.class);

	public TuplesLimitException() {
		logger.info("Enter a valid number of tuples please!\n");
	}
	
	final private String message = "Enter a valid number of tuples please!";
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
