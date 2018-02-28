package main.java.com.excilys.computer.database.Exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class YearLimitException extends Exception{
	private static final long serialVersionUID = 1L;
	final private static Logger logger = LogManager.getLogger(YearLimitException.class);

	public YearLimitException() {
		logger.info("The year must be superior to 1970!\n");
	}
}
