package com.excilys.computer.database.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computer.database.binding.dto.DTOCompany;
import com.excilys.computer.database.binding.mapper.MapperCompany;
import com.excilys.computer.database.core.exceptions.RequestNotFoundException;
import com.excilys.computer.database.core.modele.Company;
import com.excilys.computer.database.services.ServiceCompany;
import com.excilys.computer.database.validator.Validator;

@RestController
public class CompanyControllerRest {
	private final Validator validator;
	private final ServiceCompany serviceCompany;
	final private MapperCompany mapperCompany;
	
	public CompanyControllerRest(ServiceCompany serviceCompany,Validator validator, MapperCompany mapperCompany) {
		this.serviceCompany = serviceCompany;
		this.validator = validator;
		this.mapperCompany = mapperCompany;
	}
	
	@GetMapping("/companies")
    public List<DTOCompany> getAllCompany() {
		return mapperCompany.listToDTO(serviceCompany.getAllCompany());
	}
	
	@GetMapping("/companies/{id}")
    public DTOCompany getCompany(@PathVariable("id") Long companyID) {
		return mapperCompany.toDTO(serviceCompany.getCompany(companyID));
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
		if (serviceCompany.rmCompany(company)==0) {
			return HttpStatus.OK;
		}
		return HttpStatus.NO_CONTENT;
	}
}
