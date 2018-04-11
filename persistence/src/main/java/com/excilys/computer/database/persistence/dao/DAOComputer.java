package com.excilys.computer.database.persistence.dao;

import java.util.List;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

import com.excilys.computer.database.core.modele.Company;
import com.excilys.computer.database.core.modele.Computer;
import com.excilys.computer.database.core.modele.QCompany;
import com.excilys.computer.database.core.modele.QComputer;

@Repository
@Transactional
public class DAOComputer {	
	private SessionFactory sessionFactory;
	private static QComputer qcomputer = QComputer.computer;
	private static QCompany qcompany = QCompany.company;
	
	private Supplier<HibernateQueryFactory> queryFactory =
			() -> new HibernateQueryFactory(sessionFactory.getCurrentSession());
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public HibernateQuery<Computer> ordermanagement(HibernateQuery<Computer> request, String orderBy, String order) {
		switch (order) {
        case "ASC":
            switch (orderBy) {
            case "computer.id":
            	request = request.orderBy(qcomputer.id.asc());
            	break;
            case "company.name":
            	request = request.orderBy(qcompany.name.asc());
                break;
            case "computer.name":
            	request = request.orderBy(qcomputer.name.asc());
                break;
            case "introduced":
            	request = request.orderBy(qcomputer.introduced.asc());
                break;
            case "discontinued":
            	request = request.orderBy(qcomputer.discontinued.asc());
                break;
            }
            break;
        case "DESC":
            switch (orderBy) {
            case "computer.id":
            	request = request.orderBy(qcomputer.id.desc());
            	break;
            case "company.name":
            	request = request.orderBy(qcompany.name.desc());
                break;
            case "computer.name":
            	request = request.orderBy(qcomputer.name.desc());
                break;
            case "introduced":
            	request = request.orderBy(qcomputer.introduced.desc());
                break;
            case "discontinued":
            	request = request.orderBy(qcomputer.discontinued.desc());
                break;
            }
            break;
		}
		return request;
	}
			
	public int getNombre() {
		return (int) queryFactory.get().select(qcomputer).from(qcomputer)
				.leftJoin(qcompany)
				.on(qcompany.id.eq(qcomputer.company.id))
				.fetchCount();
	}
	
	public List<Computer> getAllComputer() {
		return queryFactory.get().select(qcomputer).from(qcomputer)
				.leftJoin(qcompany)
				.on(qcompany.id.eq(qcomputer.company.id))
				.fetch();
	}
	
	public List<Computer> getSomeComputers(long position, long numberOfRows, String orderBy, String order){
		HibernateQuery<Computer> request = queryFactory.get().select(qcomputer).from(qcomputer)
															.leftJoin(qcompany)
															.on(qcompany.id.eq(qcomputer.company.id))
															.limit(numberOfRows)
															.offset(position);

        return ordermanagement(request, orderBy, order).fetch();
	}
	
	public int getSearchNumber(String recherche) {
		return (int) queryFactory.get().select(qcomputer).from(qcomputer)
							.where(qcomputer.name.like(recherche + "%").or(qcomputer.company.name.like(recherche + "%")))
							.fetchCount();
	}
	
	public List<Computer> searchComputers(String recherche, long position, long numberOfRows, String orderBy, String order){
		HibernateQuery<Computer> request = queryFactory.get().select(qcomputer).from(qcomputer)
															.where(qcomputer.name.like(recherche + "%").or(qcomputer.company.name.like(recherche + "%")))
															.limit(numberOfRows)
															.offset(position);

        return ordermanagement(request, orderBy, order).fetch();
	}
	
	public void addComputer(Computer computer) {
		sessionFactory.getCurrentSession().save(computer);
	}
	
	public void rmComputer(Computer computer) {
		queryFactory.get().delete(qcomputer).where(qcomputer.id.eq(computer.getId())).execute();
	}
	
	public void rmComputerByCompany(Company company) {
		queryFactory.get().delete(qcomputer).where(qcomputer.company.id.eq(company.getId())).execute();
	}
	
	public Computer detailComputer(long id) {
		return queryFactory.get().select(qcomputer).from(qcomputer)
				.leftJoin(qcompany)
				.on(qcompany.id.eq(qcomputer.company.id))
				.where(qcomputer.id.eq(id))
				.fetchOne();
	}
	
	public void updateComputer(Computer computer) {
		queryFactory.get().update(qcomputer)
		 .where(qcomputer.id.eq(computer.getId()))
		 .set(qcomputer.name, computer.getName())
		 .set(qcomputer.introduced, computer.getIntroduced())
		 .set(qcomputer.discontinued, computer.getDiscontinued())
		 .set(qcomputer.company, computer.getCompany())
		 .execute();
	}
}