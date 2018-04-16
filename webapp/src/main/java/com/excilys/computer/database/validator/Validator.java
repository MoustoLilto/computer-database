package com.excilys.computer.database.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.excilys.computer.database.core.exceptions.DateTimeParseExceptionCDB;
import com.excilys.computer.database.core.exceptions.DroitInsuffisantException;
import com.excilys.computer.database.core.exceptions.IllegalCharacterException;
import com.excilys.computer.database.core.exceptions.IntroducedSuperiorException;
import com.excilys.computer.database.core.exceptions.NumberFormatExceptionCDB;
import com.excilys.computer.database.core.exceptions.PageLimitException;
import com.excilys.computer.database.core.exceptions.TuplesLimitException;
import com.excilys.computer.database.core.exceptions.UserAlreadyExistException;
import com.excilys.computer.database.core.exceptions.YearLimitException;
import com.excilys.computer.database.core.exceptions.champInconnueException;
import com.excilys.computer.database.services.ServiceUser;

@Component
public class Validator {
	private final ServiceUser serviceUser;
	
	public Validator(ServiceUser serviceUser) {
		this.serviceUser = serviceUser;
	}
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public Boolean controleDate(String date) throws YearLimitException, DateTimeParseExceptionCDB{
		if (date.equals("") || date == null) {
			return true;
		}
		LocalDate introd;
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
	
	public Boolean compareDate(String introd, String discont) throws IntroducedSuperiorException{
		
		if (!discont.equals("") && discont != null) {
			if(!introd.equals("") && introd != null) {
				if ((LocalDate.parse(introd, formatter)).isAfter(LocalDate.parse(discont, formatter))) {
					throw new IntroducedSuperiorException();
				}
			}
			else {
				throw new IntroducedSuperiorException();
			}
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
		if (numPage > (nbrPageMax+10) || numPage < 0) {
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
	
	public Boolean controleOrder(String order) throws champInconnueException{
		if (order.equals("ASC") || order.equals("DESC")) {
			return true;
		}
		throw new champInconnueException(); 
	}
	
	public Boolean controleAuth(Authentication auth) throws DroitInsuffisantException {
		if ((auth instanceof AnonymousAuthenticationToken)) {
			throw new DroitInsuffisantException();
		}
		return true;
	}
	
	public Boolean isUserExist(String username) throws UserAlreadyExistException {
		if (serviceUser.getUser(username) != null) {
			throw new UserAlreadyExistException();
		}
		return true;
	}
}
