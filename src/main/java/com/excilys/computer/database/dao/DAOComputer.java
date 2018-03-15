package main.java.com.excilys.computer.database.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.modele.Computer;

@Repository
public class DAOComputer {	
	private final JdbcTemplate jdbcTemplate;
	private final ComputerRowMapper computerRowMapper;
	
	@Autowired
	public DAOComputer(JdbcTemplate jdbcTemplate, ComputerRowMapper computerRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.computerRowMapper = computerRowMapper;
	}

	public int getNombre() {
		String query = RequetesComputerSQL.NOMBRE.toString();
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public List<Computer> getAllComputer() {
		String query = RequetesComputerSQL.ALL.toString();
		return jdbcTemplate.query(query, computerRowMapper);
	}
	
	public List<Computer> getSomeComputers(long position, long numberOfRows, String orderBy, String order){
		String[] splittedQuerry = RequetesComputerSQL.SOME_WITH_ORDER.toString().split("---");
		String query = splittedQuerry[0] + orderBy +splittedQuerry[1] + order + splittedQuerry[2];
		return jdbcTemplate.query(query, new Object[] { numberOfRows, position},  computerRowMapper);
	}
	
	public int getSearchNumber(String recherche) {
		String query = RequetesComputerSQL.SEARCH_NOMBRE.toString();
		return jdbcTemplate.queryForObject(query, new Object[] { "%" + recherche + "%", "%" + recherche + "%"},  Integer.class);
	}
	
	public List<Computer> searchComputers(String recherche, long position, long numberOfRows, String orderBy, String order){
		String[] splittedQuerry = RequetesComputerSQL.SEARCH_WITH_ORDER.toString().split("---");
		String query = splittedQuerry[0] + orderBy +splittedQuerry[1] + order + splittedQuerry[2];
		return jdbcTemplate.query(query, new Object[] { "%" + recherche + "%", "%" + recherche + "%", numberOfRows, position},  computerRowMapper);
	}
	
	public void addComputer(Computer computer) {
		String query = RequetesComputerSQL.ADD.toString();
		jdbcTemplate.update(query, new Object[] { computer.getName(),
						computer.getIntroduced() == null ? null : Date.valueOf(computer.getIntroduced()),
						computer.getDiscontinued() == null ? null : computer.getDiscontinued(),
						computer.getCompany().getId() == 0 ? null : computer.getCompany().getId() });
	}
	
	public void rmComputer(Computer computer) {
		String query = RequetesComputerSQL.DELETE.toString();
		jdbcTemplate.update(query, new Object[] { computer.getId() });
	}
	
	public void rmComputerByCompany(Company company) {
		String query = RequetesComputerSQL.DELETE_COMPANY.toString();
		jdbcTemplate.update(query, new Object[] { company.getId() });
	}
	
	public Computer detailComputer(long id) {
		String query = RequetesComputerSQL.DETAIL.toString();
		List<Computer> computers = jdbcTemplate.query(query, new Object[] { id }, computerRowMapper);
		return computers.get(0);
	}
	
	public void updateComputer(Computer computer) {
		String query = RequetesComputerSQL.UPDATE.toString();
		jdbcTemplate.update(query, new Object[] { computer.getName(),
				computer.getIntroduced() == null ? null : Date.valueOf(computer.getIntroduced()),
				computer.getDiscontinued() == null ? null : computer.getDiscontinued(),
				computer.getCompany().getId() == 0 ? null : computer.getCompany().getId(),
				computer.getId()});
	}
}