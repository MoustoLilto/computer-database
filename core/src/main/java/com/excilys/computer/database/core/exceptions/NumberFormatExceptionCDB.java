package com.excilys.computer.database.core.exceptions;

public class NumberFormatExceptionCDB extends NumberFormatException{
	private static final long serialVersionUID = 1L;
	
	public NumberFormatExceptionCDB() {
		super();
	}

	public NumberFormatExceptionCDB(String s) {
		super(s);
	}

	final private String message = "Please enter a valid number!";
	
	@Override
	public String getMessage() {
		return this.message;
	}

}
