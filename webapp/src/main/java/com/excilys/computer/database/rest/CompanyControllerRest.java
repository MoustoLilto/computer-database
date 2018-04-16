package com.excilys.computer.database.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computer.database.core.exceptions.RequestNotFoundException;
import com.excilys.computer.database.core.modele.Company;
import com.excilys.computer.database.services.ServiceCompany;
import com.excilys.computer.database.validator.Validator;

@RestController
public class CompanyControllerRest {
	private final Validator validator;
	private final ServiceCompany serviceCompany;
	
	public CompanyControllerRest(ServiceCompany serviceCompany,Validator validator) {
		this.serviceCompany = serviceCompany;
		this.validator = validator;
	}
	
	@GetMapping("/companies")
    public List<Company> getAllCompany() {
		return serviceCompany.getAllCompany();
	}
	
	@GetMapping("/companies/{id}")
    public Company getCompany(@PathVariable("id") Long companyID) {
		return serviceCompany.getCompany(companyID);
	}
	
	@GetMapping("/companies/nombre")
    public int getNbreCompany() {
		return serviceCompany.getNombre();
	}
	
	@DeleteMapping("/companies/delete/{id}")
	public HttpStatus rmCompany(@PathVariable("id") Long companyID) {
		Company company = null;
		try {
			validator.restRessource(company = serviceCompany.getCompany(companyID));
		} catch (RequestNotFoundException e) {
			return HttpStatus.NOT_FOUND;
		}
		serviceCompany.rmCompany(company);
		return HttpStatus.OK;
	}
}
