package main.java.com.excilys.computer.database.dao;

import java.sql.Date;
import java.util.List;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;

import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.modele.QComputer;

@Repository
public class DAOComputer {	
	private final JdbcTemplate jdbcTemplate;
	private final ComputerRowMapper computerRowMapper;
	
	public DAOComputer(JdbcTemplate jdbcTemplate, ComputerRowMapper computerRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.computerRowMapper = computerRowMapper;
	}
	
	private SessionFactory sessionFactory;
	private static QComputer qcomputer = QComputer.computer;
	
	private Supplier<HibernateQueryFactory> queryFactory =
			() -> new HibernateQueryFactory(sessionFactory.getCurrentSession());
			
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public int getNombre() {
		return (int) queryFactory.get().select(qcomputer).from(qcomputer).fetchCount();
	}
	
	@Transactional
	public List<Computer> getAllComputer() {
		return queryFactory.get().select(qcomputer).from(qcomputer).fetch();
	}
	
	@Transactional
	public List<Computer> getSomeComputers(long position, long numberOfRows, String orderBy, String order){
		return queryFactory.get().select(qcomputer).from(qcomputer).offset(position).limit(numberOfRows).fetch();
		//queryFactory.get().select(qcomputer).from(qcomputer).offset(position).limit(numberOfRows).orderBy(qcomputer.id.asc())
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