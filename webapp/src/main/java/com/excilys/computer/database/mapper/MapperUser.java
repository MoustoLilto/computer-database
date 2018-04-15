package com.excilys.computer.database.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.core.modele.Users;
import com.excilys.computer.database.dto.DTOUser;

@Component
public class MapperUser {
	
	public DTOUser toDTo(Users user) {
		return new DTOUser(user.getUsername(), user.getPassword());
	}
	
	public Users toUser(DTOUser dtoUser) {
		Users user = new Users();
		user.setEnabled(true);
		user.setPassword(dtoUser.getPassword());
		user.setUsername(dtoUser.getUsername());
		return user;
	}
	
	public List<DTOUser> toDtos(List<Users> users){
		return users.stream().map(c -> toDTo(c)).collect(Collectors.toList());
	}
	
	public List<Users> toUsers(List<DTOUser> dtoUsers){
		return dtoUsers.stream().map(c -> toUser(c)).collect(Collectors.toList());
	}

}
