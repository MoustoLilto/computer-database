package main.java.com.excilys.computer.database.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import main.java.com.excilys.computer.database.Exceptions.IntroducedSuperiorException;
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
	
	/*public String controleNom(String name) {
		return name;
	}*/
	
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
	
	/*public String DatetoString(LocalDate date) {
		if (date.equals("") || date == null) {
			return null;
		}
		return date.toString();
	}*/
	
	/*final private static Logger logger = LogManager.getLogger(Validator.class);
	
	public int ajoutNom(Computer computer, String name) {
		computer.setName(name);
		return 0; 
	}
	
	public int ajoutIntroduced(Computer computer, String date) {
		if (date.equals("") || date == null) {
			return 0;
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			LocalDate introd = LocalDate.parse(date, formatter);
			if (introd.getYear() > 1970) {
				computer.setIntroduced(introd);
				return 0;
			}
			else {
				logger.info("The year must be superior to 1970!\n");
			}
		} catch(DateTimeParseException e) {
			logger.info("Please enter a valid Date on the good format!\n");
		}
		return -1;
	}
	
	public int ajoutDiscontinued(Computer computer, String date) {
		if (date.equals("") || date == null) {
			return 0;
		}
		
		if (computer.getIntroduced()!=null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			try {
				LocalDate discont = LocalDate.parse(date, formatter);
				if ((discont.isAfter(computer.getIntroduced())) && (discont.getYear() > 1970)) {
					computer.setDiscontinued(discont);
					return 0;
				}
				else {
					logger.info("Please enter a date greater than the introduced date and the year must be superior to 1970!\n");
				}
			} catch(DateTimeParseException e) {
				logger.info("Please enter a valid Date on the good format!\n");
			}				
		}
		return -1;
	}
	
	public int ajoutCompany(Computer computer, ServiceComputer service, String company) {
		try {
			long companyID = Long.parseLong(company);
        	computer.setCompany(service.getCompany(companyID));
			return 0;
		} catch (NumberFormatException e) {
			logger.info("There is a problem with the company ID!\n");
		}
		return -1;
	}
	
	public List<Computer> getAllComputersDTO(){
		return ServiceComputer.getService().getAllComputer();
	}

	public Boolean addComputer(String name, String introduced, String discontinued, String company_id) {
		logger.debug("name: " + name + " introduced: " + introduced + " discontinued: " + discontinued + " company_id: " + company_id);
		Computer computer = new Computer();
		ServiceComputer service = ServiceComputer.getService();
		
		if (ajoutNom(computer, name)!=0) {
			return false;
		}
		if (ajoutIntroduced(computer, introduced)!=0) {
			return false;
		}		
		if (ajoutDiscontinued(computer, discontinued)!=0) {
			return false;
		}
		if (ajoutCompany(computer, service, company_id)!=0) {
			return false;
		}
		logger.debug(computer);
		
		if (service.addComputer(computer)==0) {
			logger.info("Computer '" + computer.getName() + "' is added!\n\n");
			return true;
		}
		logger.info("Error adding the computer '" + computer.getName() + "'\n\n");
		return false;
	}*/
}
