package com.excilys.computer.database.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.computer.database.dto.DTOCompany;
import com.excilys.computer.database.core.modele.Company;

@Component
public class MapperCompany {
	public MapperCompany() {
	}

	public DTOCompany toDTO(Company company) {
		String dtoCompanyID = String.valueOf(company.getId());
		return new DTOCompany(dtoCompanyID, company.getName());
	}
	
	public Company toCompany(DTOCompany dtoCompany) {
		long companyId = Long.parseLong(dtoCompany.getId());
		return new Company(companyId, dtoCompany.getName());
	}
	
	public List<DTOCompany> listToDTO(List<Company> companies){
		return companies.stream().map(c -> toDTO(c)).collect(Collectors.toList());
	}
	
	public List<Company> listToCompany(List<DTOCompany> dtoCompanies){
		return dtoCompanies.stream().map(c -> toCompany(c)).collect(Collectors.toList());
	}
}
