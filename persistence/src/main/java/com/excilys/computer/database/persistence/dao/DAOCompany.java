package com.excilys.computer.database.persistence.dao;

import java.util.List;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;

import com.excilys.computer.database.core.modele.Company;
import com.excilys.computer.database.core.modele.QCompany;

@Repository
@Transactional
public class DAOCompany {
	private SessionFactory sessionFactory;
	private static QCompany qcompany = QCompany.company;
	
	private Supplier<HibernateQueryFactory> queryFactory =
			() -> new HibernateQueryFactory(sessionFactory.getCurrentSession());
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
			
	public com.excilys.computer.database.core.modele.Company getCompany(long companyId) {	
		return queryFactory.get().select(qcompany).from(qcompany).where(qcompany.id.eq(companyId)).fetchOne();
	}
	
	public int getNombre() {
		return (int) queryFactory.get().select(qcompany).from(qcompany).fetchCount();
	}
	
	public List<Company> getAllCompany() {
		return queryFactory.get().select(qcompany).from(qcompany).fetch();
	}

	public void rmCompany(Company company) {
		queryFactory.get().delete(qcompany).where(qcompany.id.eq(company.getId()));
	}
}