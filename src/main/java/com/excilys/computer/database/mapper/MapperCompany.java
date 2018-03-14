package main.java.com.excilys.computer.database.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import main.java.com.excilys.computer.database.dto.DTOCompany;
import main.java.com.excilys.computer.database.modele.Company;

@Component
public class MapperCompany {
	public DTOCompany toDTO(Company company) {
		return new DTOCompany(company.getId(), company.getName());
	}
	
	public Company toCompany(DTOCompany dtoCompany) {
		return new Company(dtoCompany.getId(), dtoCompany.getName());
	}
	
	public List<DTOCompany> listToDTO(List<Company> companies){
		return companies.stream().map(c -> toDTO(c)).collect(Collectors.toList());
		
		/*List<DTOCompany> dtoCompanies = new ArrayList<>();
		for (Company company : companies) {
			DTOCompany dtoCompany = toDTO(company);
			dtoCompanies.add(dtoCompany);
		}
		return dtoCompanies;*/
	}
	
	public List<Company> listToCompany(List<DTOCompany> dtoCompanies){
		return dtoCompanies.stream().map(c -> toCompany(c)).collect(Collectors.toList());
		
		/*List<Company> companies = new ArrayList<>();
		for (DTOCompany dtoCompany : dtoCompanies) {
			Company company = toCompany(dtoCompany);
			companies.add(company);
		}
		return companies;*/
	}
}
