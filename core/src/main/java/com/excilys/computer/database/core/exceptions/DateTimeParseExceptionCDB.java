package com.excilys.computer.database.core.exceptions;

import java.time.format.DateTimeParseException;

public class DateTimeParseExceptionCDB extends DateTimeParseException{
	public DateTimeParseExceptionCDB(String message, CharSequence parsedData, int errorIndex) {
		super(message, parsedData, errorIndex);
	}

	private static final long serialVersionUID = 1L;

	final private String message = "Please enter a valid date!";
	
	@Override
	public String getMessage() {
		return this.message;
	}

}
