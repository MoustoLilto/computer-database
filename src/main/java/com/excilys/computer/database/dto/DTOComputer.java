package main.java.com.excilys.computer.database.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;

public class DTOComputer {
	final private static Logger logger = LogManager.getLogger(DTOComputer.class);
	
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
		} catch(Exception e) {
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
			} catch(Exception e) {
				logger.info("Please enter a valid Date on the good format!\n");
			}				
		}
		return -1;
	}
	
	public int ajoutCompany(Computer computer, String company) {
		try {
			long company_id = Long.parseLong(company);
			computer.setCompany_id(company_id);
			return 0;
		} catch (Exception e) {
			logger.info("Please enter a valid Date on the good format!\n");
		}
		return -1;
	}
	
	public List<Computer> getAllComputersDTO(){
		return ServiceComputer.getService().getAllComputer();
	}
	
	/**
	 * Recupere les informations entree, et ajoute un computer a la BDD
	 */
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
		if (ajoutCompany(computer, company_id)!=0) {
			return false;
		}
		logger.debug(computer);
		
		if (service.addComputer(computer)==0) {
			logger.info("Computer '" + computer.getName() + "' is added!\n\n");
			return true;
		}
		logger.info("Error adding the computer '" + computer.getName() + "'\n\n");
		return false;
	}
}
