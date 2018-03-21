package main.java.com.excilys.computer.database.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import main.java.com.excilys.computer.database.modele.Company;

@Repository
public class DAOCompany {
	private final JdbcTemplate jdbcTemplate;
	private final CompanyRowMapper companyRowMapper;
	
	public DAOCompany(JdbcTemplate jdbcTemplate, CompanyRowMapper companyRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.companyRowMapper = companyRowMapper;
	}

	public int getNombre() {
		String query = RequetesCompanySQL.NOMBRE.toString();
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public Company getCompany(long companyId) {
		String query = RequetesCompanySQL.DETAIL.toString();
		List<Company> companys = jdbcTemplate.query(query, new Object[] { companyId }, companyRowMapper);
		return companys.get(0);
	}
	
	public List<Company> getAllCompany() {
		String query = RequetesCompanySQL.ALL.toString();
		return jdbcTemplate.query(query, companyRowMapper);
	}
	
	public void rmCompany(Company company) {
		String query = RequetesCompanySQL.DELETE.toString();
		jdbcTemplate.update(query, new Object[] { company.getId() });
	}
}