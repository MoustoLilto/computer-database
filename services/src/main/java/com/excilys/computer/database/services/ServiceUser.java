package com.excilys.computer.database.services;

import org.springframework.stereotype.Service;

import com.excilys.computer.database.core.modele.Users;
import com.excilys.computer.database.persistence.dao.DAOUsers;

@Service
public class ServiceUser {
	private final DAOUsers daouser;
	
	public ServiceUser(DAOUsers daouser) {
		this.daouser = daouser;
	}

	public void addUser(Users user) {
		daouser.addUser(user);
		daouser.adduserRole(user);
	}
	
	public void updateUser(Users user) {
		daouser.updateUser(user);
	}
	
	public Users getUser(String username) {
		return daouser.getUser(username);
	}
}
