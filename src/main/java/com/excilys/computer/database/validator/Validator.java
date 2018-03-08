package main.java.com.excilys.computer.database.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import main.java.com.excilys.computer.database.exceptions.DateTimeParseExceptionCDB;
import main.java.com.excilys.computer.database.exceptions.IntroducedSuperiorException;
import main.java.com.excilys.computer.database.exceptions.NumberFormatExceptionCDB;
import main.java.com.excilys.computer.database.exceptions.PageLimitException;
import main.java.com.excilys.computer.database.exceptions.TuplesLimitException;
import main.java.com.excilys.computer.database.exceptions.YearLimitException;
import main.java.com.excilys.computer.database.exceptions.champInconnueException;

public class Validator {
	private static Validator validator = null;
	
	private Validator() {
	}
	
	public static Validator getIntsance() {
		if (validator == null) {
			validator = new Validator();
		}
		return validator;
	}
	
	public Boolean controleDate(String date) throws YearLimitException, DateTimeParseExceptionCDB{
		if (date.equals("") || date == null) {
			return true;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate introd = LocalDate.parse(date, formatter);
		if (introd.getYear() > 1970) {
			return true;
		}
		throw new YearLimitException();
	}
	
	public Boolean compareDate(LocalDate introduced, LocalDate discontinued) throws IntroducedSuperiorException{
		if (introduced.isAfter(discontinued)) {
			throw new IntroducedSuperiorException();
		}
		return true;
	}
	
	public Boolean controleID(String l) throws NumberFormatException{
		Long.parseLong(l);
		return true;
	}
	
	public Boolean controlePage(String page, int nbrPageMax) throws NumberFormatExceptionCDB, PageLimitException{
		int numPage = Integer.parseInt(page);
		if (numPage > nbrPageMax || numPage < 1) {
			throw new PageLimitException();
		}
		return true;
	}
	
	public Boolean controleNbrTuples(String tuples, int nbrTupleMax) throws NumberFormatExceptionCDB, TuplesLimitException{
		int nbrTuple = Integer.parseInt(tuples);
		if (nbrTuple > nbrTupleMax+100 || nbrTuple < 1) {
			throw new TuplesLimitException();
		}
		return true;
	}
	
	public Boolean controleAttribute(String attribute) throws champInconnueException{
		if (attribute.equals("computer.id") || attribute.equals("company.name") || attribute.equals("computer.name") 
				|| attribute.equals("introduced") || attribute.equals("discontinued")) {
			return true;
		}
		throw new champInconnueException(); 
	}
}
