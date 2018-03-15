package main.java.com.excilys.computer.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.modele.Company;

@Component
public class CompanyRowMapper implements RowMapper<Company>{
	
	@Override
	public Company mapRow(ResultSet results, int tuple) throws SQLException {
		Company company = new Company();
		
		company.setId(results.getLong(1));
		company.setName(results.getString(2));
		
		return company;
	}
}
