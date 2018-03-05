package main.java.com.excilys.computer.database.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import main.java.com.excilys.computer.database.Exceptions.IntroducedSuperiorException;
import main.java.com.excilys.computer.database.Exceptions.PageLimitException;
import main.java.com.excilys.computer.database.Exceptions.TuplesLimitException;
import main.java.com.excilys.computer.database.Exceptions.YearLimitException;

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
	
	public Boolean controleDate(String date) throws YearLimitException, DateTimeParseException{
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
	
	public Boolean controlePage(String page, int nbrPageMax) throws NumberFormatException, PageLimitException{
		int numPage = Integer.parseInt(page);
		if (numPage > nbrPageMax || numPage < 1) {
			throw new PageLimitException();
		}
		return true;
	}
	
	public Boolean controleNbrTuples(String tuples, int nbrTupleMax) throws NumberFormatException, TuplesLimitException{
		int nbrTuple = Integer.parseInt(tuples);
		if (nbrTuple > nbrTupleMax || nbrTuple < 1) {
			throw new TuplesLimitException();
		}
		return true;
	}
}
