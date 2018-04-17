package com.excilys.computer.database.persistence.dao;

import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computer.database.core.modele.QUsers;
import com.excilys.computer.database.core.modele.UserRoles;
import com.excilys.computer.database.core.modele.Users;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository
@Transactional
public class DAOUsers {
	private SessionFactory sessionFactory;
	private static QUsers quser = QUsers.users;
	
	private Supplier<HibernateQueryFactory> queryFactory =
			() -> new HibernateQueryFactory(sessionFactory.getCurrentSession());
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void adduserRole(Users user) {
		UserRoles userRole = new UserRoles();
		userRole.setUser(user);
		sessionFactory.getCurrentSession().save(userRole);
	}
	
	public void addUser(Users user) {
		sessionFactory.getCurrentSession().save(user);
	}
	
	public Users getUser(String username) {
		return queryFactory.get().select(quser).from(quser).where(quser.username.eq(username)).fetchOne();
	}
	
	public void updateUser(Users user) {
		queryFactory.get().update(quser)
		 .where(quser.username.eq(user.getUsername()))
		 .set(quser.password, user.getPassword())
		 .set(quser.enabled, user.getEnabled())
		 .execute();
	}
}
