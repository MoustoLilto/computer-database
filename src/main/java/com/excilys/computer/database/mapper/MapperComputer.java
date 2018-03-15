package main.java.com.excilys.computer.database.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.dao.DAOCompany;
import main.java.com.excilys.computer.database.dto.DTOComputer;
import main.java.com.excilys.computer.database.modele.Company;
import main.java.com.excilys.computer.database.modele.Computer;

@Component
public class MapperComputer {
	final private DAOCompany daoCompany;
	
	@Autowired
	public MapperComputer(DAOCompany daoCompany) {
		this.daoCompany = daoCompany;
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
		if (!dtoComputer.getIntroduced().equals("") && dtoComputer.getIntroduced() != null) {
			introduced = LocalDate.parse(dtoComputer.getIntroduced(), formatter);
		}
		if (!dtoComputer.getDiscontinued().equals("") && dtoComputer.getDiscontinued() != null) {
			discontinued = LocalDate.parse(dtoComputer.getDiscontinued(), formatter);
		}
		companyID = dtoComputer.getCompanyID();
		company = daoCompany.getCompany(companyID);
		
		Computer computer = new Computer(id, name, introduced, discontinued, company);
		return computer;
	}
	
	public List<DTOComputer> listToDTO(List<Computer> computers){
		return computers.stream().map(c -> toDTO(c)).collect(Collectors.toList());
	}
	
	public List<Computer> listToComputer(List<DTOComputer> dtoComputers){
		return dtoComputers.stream().map(c -> toComputer(c)).collect(Collectors.toList());
	}
}
