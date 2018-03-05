package main.java.com.excilys.computer.database.Exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TuplesLimitException extends Exception{
	private static final long serialVersionUID = 1L;
	final private static Logger logger = LogManager.getLogger(YearLimitException.class);

	public TuplesLimitException() {
		logger.info("Enter a valid number of tuples please!\n");
	}

}
