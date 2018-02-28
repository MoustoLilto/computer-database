package main.java.com.excilys.computer.database.dto;

import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.computer.database.modele.Company;

public class MapperCompany {
	private static MapperCompany mapperCompany = null;
	
	private MapperCompany() {
	}
	
	public static MapperCompany getInstance() {
		if (mapperCompany == null) {
			mapperCompany = new MapperCompany();
		}
		return mapperCompany;
	}
	
	public DTOCompany toDTO(Company company) {
		DTOCompany dtoCompany = new DTOCompany(company.getId(), company.getName());
		return dtoCompany;
	}
	
	public Company toCompany(DTOCompany dtoCompany) {
		Company company = new Company(dtoCompany.getId(), dtoCompany.getName());
		return company;
	}
	
	public List<DTOCompany> listToDTO(List<Company> companies){
		List<DTOCompany> dtoCompanies = new ArrayList<>();
		for (Company company : companies) {
			DTOCompany dtoCompany = toDTO(company);
			dtoCompanies.add(dtoCompany);
		}
		return dtoCompanies;
	}
	
	public List<Company> listToCompany(List<DTOCompany> dtoCompanies){
		List<Company> companies = new ArrayList<>();
		for (DTOCompany dtoCompany : dtoCompanies) {
			Company company = toCompany(dtoCompany);
			companies.add(company);
		}
		return companies;
	}

}
