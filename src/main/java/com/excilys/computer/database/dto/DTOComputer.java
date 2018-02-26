package main.java.com.excilys.computer.database.dto;

import java.util.List;

import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceComputer;

public class DTOComputer {
	
	public List<Computer> getAllComputersDTO(){
		return ServiceComputer.getService().getAllComputer();
	}
	
	public void addComputer(String name, String introduced, String discontinued, String company_id) {
		System.out.println(name + introduced + discontinued + company_id);
	}

}
