package main.java.com.excilys.computer.database.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.dao.DAOCompany;
import main.java.com.excilys.computer.database.modele.Company;

@Component
public class ServiceCompany {
	final static Logger logger = LogManager.getLogger(ServiceCompany.class);
	
	@Autowired
	DAOCompany daoCompany;
	
	public int getNombre() {
		return daoCompany.getNombre();
	}
	
	public List<Company> getAllCompany(){
		return daoCompany.getAllCompany();
	}
	
	public Company getCompany(long companyID) {
		return daoCompany.getCompany(companyID);
	}
	
	public int rmCompany(Company company) {
		if (getCompany(company.getId())==null) {
			return -1;
		}
		if (daoCompany.rmCompany(company)==0) {
			logger.info("No rows have been deleted!\n\n");
			return -1;
		}
		return 0;
	}
}

