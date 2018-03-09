package main.java.com.excilys.computer.database.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import main.java.com.excilys.computer.database.exceptions.DateTimeParseExceptionCDB;
import main.java.com.excilys.computer.database.exceptions.IllegalCharacterException;
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
		LocalDate introd;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try{
			introd = LocalDate.parse(date, formatter);
		} catch(DateTimeParseException e) {
			throw new DateTimeParseExceptionCDB(date, date, 0);
		}
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
	
	public Boolean controleID(String l) throws NumberFormatExceptionCDB{
		try{
			Long.parseLong(l);
		} catch(NumberFormatException e) {
			throw new NumberFormatExceptionCDB();
		}
		return true;
	}
	
	public Boolean controleText(String text) throws IllegalCharacterException{
		char[] interdit = {'[', '!', '@', '#', '%', '^', '&', '*', '(', ')', '<', '>', ']'};
		for (char lettre : interdit) {
			if (text.indexOf(lettre) >= 0) {
				throw new IllegalCharacterException();
			}
		}
		return true;
	}
	
	public Boolean controlePage(String page, int nbrPageMax) throws NumberFormatExceptionCDB, PageLimitException{
		int numPage;
		try {
			numPage = Integer.parseInt(page);
		} catch (NumberFormatException e){
			throw new NumberFormatExceptionCDB();
		}
		if (numPage > nbrPageMax || numPage < 1) {
			throw new PageLimitException();
		}
		return true;
	}
	
	public Boolean controleNbrTuples(String tuples, int nbrTupleMax) throws NumberFormatExceptionCDB, TuplesLimitException{
		int nbrTuple;
		try {
			nbrTuple = Integer.parseInt(tuples);
		} catch (NumberFormatException e){
			throw new NumberFormatExceptionCDB();
		}
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
