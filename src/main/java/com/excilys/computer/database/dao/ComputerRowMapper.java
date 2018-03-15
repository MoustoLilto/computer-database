package main.java.com.excilys.computer.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.modele.Computer;

@Component
public class ComputerRowMapper implements RowMapper<Computer>{

	@Override
	public Computer mapRow(ResultSet results, int tuple) throws SQLException {
		Computer computer = new Computer();												//Creation du tuple
    	
    	computer.setId(results.getLong(1));												//Ajout des attributs au tuple
    	computer.setName(results.getString(2));
    	if (results.getTimestamp(3) == null) {
    		computer.setIntroduced(null);
    	}
    	else {
    		computer.setIntroduced(results.getTimestamp(3).toLocalDateTime().toLocalDate());
    	}
    	if (results.getTimestamp(4) == null) {
    		computer.setDiscontinued(null);
    	}
    	else {
    		computer.setDiscontinued(results.getTimestamp(4).toLocalDateTime().toLocalDate());
    	}
    	long companyID = results.getLong(5);
    	String companyName = results.getString(6);
    	Company company = null;
    	if (companyID != 0) {
    		company = new Company(companyID, companyName);
    	}
    	computer.setCompany(company);
    	
    	return computer;
	}
}
