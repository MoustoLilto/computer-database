package main.java.com.excilys.computer.database.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.dao.DAOCompany;
import main.java.com.excilys.computer.database.dao.DAOComputer;
import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.modele.Computer;

@Component
public class ServiceComputer {
	final static Logger logger = LogManager.getLogger(ServiceComputer.class);
	private final DAOComputer daoComputer;
	private final DAOCompany daoCompany;
	
	@Autowired
	public ServiceComputer(DAOComputer daoComputer, DAOCompany daoCompany) {
		this.daoComputer = daoComputer;
		this.daoCompany = daoCompany;
	}

	public int getNombre() {
		return daoComputer.getNombre();
	}
	
	public Company getCompany(long companyID) {
		return daoCompany.getCompany(companyID);
	}
	
 	public List<Computer> getAllComputer(){
		return daoComputer.getAllComputer();
	}
	
	public List<Computer> getSomeComputers(int numTuple, int nbreTuples, String orderBy, String order){		
		return daoComputer.getSomeComputers(numTuple, nbreTuples, orderBy, order);		
	}
	
	public int getSearchNumber(String recherche) {
		return daoComputer.getSearchNumber(recherche);
	}
	
	public List<Computer> seachComputers(String recherche, long position, long numberOfRows, String orderBy, String order){
		return daoComputer.searchComputers(recherche, position, numberOfRows, orderBy, order);
	}
	
	public int addComputer(Computer computer)  {
		if (daoComputer.addComputer(computer)==0) {
			logger.info("No rows have been added!\n\n");
			return -1;
		}
		return 0;
	}
	
	public int rmComputer(Computer computer) {
		if (detailComputer(computer.getId())==null) {
			return -1;
		}
		if (daoComputer.rmComputer(computer)==0) {
			logger.info("No rows have been deleted!\n\n");
			return -1;
		}
		return 0;
	}
	
	public int updateComputer(Computer computer) {
		if (daoComputer.updateComputer(computer)==0) {
			logger.info("No rows have been updated!\n\n");
			return -1;
		}
		return 0;
	}
	
	public Computer detailComputer(long id) {
		return daoComputer.detailComputer(id);
	}
}