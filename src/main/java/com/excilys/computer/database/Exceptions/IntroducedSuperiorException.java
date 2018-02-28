package main.java.com.excilys.computer.database.Exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntroducedSuperiorException extends Exception{
	private static final long serialVersionUID = 1L;
	final private static Logger logger = LogManager.getLogger(IntroducedSuperiorException.class);

	public IntroducedSuperiorException() {
		logger.info("The discontinued date must be greater than the introduced date!\n");
	}

}
