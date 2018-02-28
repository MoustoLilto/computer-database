package main.java.com.excilys.computer.database.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.computer.database.dao.DAOCompany;
import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.modele.Computer;

public class MapperComputer {
	private static MapperComputer mapperComputer = null;
	
	private MapperComputer() {
	}
	
	public static MapperComputer getInstance() {
		if (mapperComputer == null) {
			mapperComputer = new MapperComputer();
		}
		return mapperComputer;
	}
	
	public DTOComputer toDTO(Computer computer) {
		long id;
		String name = null;
		String introduced = null;
		String discontinued = null;
		long companyID = 10000;
		String companyName = null;
		
		id = computer.getId();
		if (computer.getName() != null) {
			name = computer.getName();
		}
		if (computer.getIntroduced() != null) {
			introduced = computer.getIntroduced().toString();
		}
		if (computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued().toString();
		}
		if(computer.getCompany() != null) {
			companyID = computer.getCompany().getId();
			companyName = computer.getCompany().getName();
		}
		
		DTOComputer dtoComputer = new DTOComputer(id, name, introduced, discontinued, companyID, companyName);
		return dtoComputer;
	}
	
	public Computer toComputer(DTOComputer dtoComputer){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		long id;
		String name = null;
		LocalDate introduced = null;
		LocalDate discontinued = null;
		long companyID = 10000;
		Company company = null;
		
		id = dtoComputer.getId();
		if (dtoComputer.getName() != null) {
			name = dtoComputer.getName();
		}
		if (dtoComputer.getIntroduced() != null) {
			introduced = LocalDate.parse(dtoComputer.getIntroduced(), formatter);
		}
		if (dtoComputer.getDiscontinued() != null) {
			discontinued = LocalDate.parse(dtoComputer.getDiscontinued(), formatter);
		}
		companyID = dtoComputer.getCompanyID();
		company = DAOCompany.getInstance().getCompany(companyID);
		
		Computer computer = new Computer(id, name, introduced, discontinued, company);
		return computer;
	}
	
	public List<DTOComputer> listToDTO(List<Computer> computers){
		List<DTOComputer> dtoComputers = new ArrayList<>();
		for (Computer computer : computers) {
			DTOComputer dtoComputer = toDTO(computer);
			dtoComputers.add(dtoComputer);
		}
		return dtoComputers;
	}
	
	public List<Computer> listToComputer(List<DTOComputer> dtoComputers){
		List<Computer> computers = new ArrayList<>();
		for (DTOComputer dtoComputer : dtoComputers) {
			Computer computer = toComputer(dtoComputer);
			computers.add(computer);
		}
		return computers;
	}
}
