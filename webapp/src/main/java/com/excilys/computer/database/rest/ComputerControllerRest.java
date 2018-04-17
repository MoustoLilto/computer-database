package com.excilys.computer.database.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computer.database.core.exceptions.DateTimeParseExceptionCDB;
import com.excilys.computer.database.core.exceptions.IllegalCharacterException;
import com.excilys.computer.database.core.exceptions.IntroducedSuperiorException;
import com.excilys.computer.database.core.exceptions.RequestNotFoundException;
import com.excilys.computer.database.core.exceptions.YearLimitException;
import com.excilys.computer.database.core.modele.Computer;
import com.excilys.computer.database.binding.dto.DTOComputer;
import com.excilys.computer.database.binding.mapper.MapperComputer;
import com.excilys.computer.database.services.ServiceComputer;
import com.excilys.computer.database.validator.Validator;
import com.google.common.base.Preconditions;

@RestController
public class ComputerControllerRest {
	private final Validator validator;
	private final ServiceComputer serviceComputer;
	private final MapperComputer mapperComputer;
	
	public ComputerControllerRest(Validator validator, ServiceComputer serviceComputer, MapperComputer mapperComputer) {
		this.validator = validator;
		this.serviceComputer = serviceComputer;
		this.mapperComputer = mapperComputer;
	}
	
	public Computer enteredComputer(DTOComputer dtoComputer) throws IntroducedSuperiorException, IllegalCharacterException, DateTimeParseExceptionCDB, YearLimitException {
		validator.controleID(dtoComputer.getId());
		validator.controleText(dtoComputer.getName());
		validator.controleDate(dtoComputer.getIntroduced());
		validator.controleDate(dtoComputer.getDiscontinued());
		validator.controleID(dtoComputer.getCompanyID());
		validator.compareDate(dtoComputer.getIntroduced(), dtoComputer.getDiscontinued());
		Computer computer = mapperComputer.toComputer(dtoComputer);
		return computer;
	}
	
	@GetMapping("/computers")
	public List<DTOComputer> getAllComputer(){
		return mapperComputer.listToDTO(serviceComputer.getAllComputer());
	}
	
	@GetMapping("/computers/{id}")
	public DTOComputer getComputer(@PathVariable("id") Long id){
		return mapperComputer.toDTO(serviceComputer.detailComputer(id));
	}
	
	@GetMapping("/computers/nombre")
	public int getNbreComputer(){
		return serviceComputer.getNombre();
	}
	
	@GetMapping("/computers/{numTuple}/{nbreTuples}")
	public List<DTOComputer> getSomeComputer(@PathVariable("numTuple") int numTuple, @PathVariable("nbreTuples") int nbreTuples){
		return mapperComputer.listToDTO(serviceComputer.getSomeComputers(numTuple, nbreTuples, "computer.id", "ASC"));
	}
	
	@DeleteMapping("/computers/delete/{id}")
	public HttpStatus rmComputer(@PathVariable("id") Long id) {
		Computer computer = null;
		try {
			validator.restRessource(computer = serviceComputer.detailComputer(id));
		} catch (RequestNotFoundException e) {
			return HttpStatus.NOT_FOUND;
		}
		serviceComputer.rmComputer(computer);
		return HttpStatus.OK;
	}
	
	@PostMapping("/computers/create")
	public HttpStatus createComputer(@RequestBody DTOComputer dtoComputer) {
		Preconditions.checkNotNull(dtoComputer);
		Computer computer;
		try {
			computer = enteredComputer(dtoComputer);
		} catch (DateTimeParseExceptionCDB e1) {
			return HttpStatus.BAD_REQUEST;
		} catch (IntroducedSuperiorException e1) {
			return HttpStatus.METHOD_NOT_ALLOWED;
		} catch (IllegalCharacterException e1) {
			return HttpStatus.NOT_ACCEPTABLE;
		} catch (YearLimitException e1) {
			return HttpStatus.METHOD_NOT_ALLOWED;
		}
		
		try {
			validator.restRessource(serviceComputer.detailComputer(computer.getId()));
		} catch (RequestNotFoundException e) {
			return HttpStatus.NOT_FOUND;
		}
		serviceComputer.addComputer(computer);
		return HttpStatus.OK;
	}
	
	@PostMapping("/computers/update")
	public HttpStatus updateComputer(@RequestBody DTOComputer dtoComputer) {
		Preconditions.checkNotNull(dtoComputer);
		Computer computer;
		try {
			computer = enteredComputer(dtoComputer);
		} catch (DateTimeParseExceptionCDB e1) {
			return HttpStatus.BAD_REQUEST;
		} catch (IntroducedSuperiorException e1) {
			return HttpStatus.METHOD_NOT_ALLOWED;
		} catch (IllegalCharacterException e1) {
			return HttpStatus.NOT_ACCEPTABLE;
		} catch (YearLimitException e1) {
			return HttpStatus.METHOD_NOT_ALLOWED;
		}
		
		try {
			validator.restRessource(serviceComputer.detailComputer(computer.getId()));
		} catch (RequestNotFoundException e) {
			return HttpStatus.NOT_FOUND;
		}
		serviceComputer.updateComputer(computer);
		return HttpStatus.OK;
	}
}
