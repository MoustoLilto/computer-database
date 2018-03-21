package main.java.com.excilys.computer.database.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.java.com.excilys.computer.database.dto.DTOCompany;
import main.java.com.excilys.computer.database.dto.DTOComputer;
import main.java.com.excilys.computer.database.exceptions.DateTimeParseExceptionCDB;
import main.java.com.excilys.computer.database.exceptions.IllegalCharacterException;
import main.java.com.excilys.computer.database.exceptions.IntroducedSuperiorException;
import main.java.com.excilys.computer.database.exceptions.YearLimitException;
import main.java.com.excilys.computer.database.mapper.MapperCompany;
import main.java.com.excilys.computer.database.mapper.MapperComputer;
import main.java.com.excilys.computer.database.modele.Computer;
import main.java.com.excilys.computer.database.services.ServiceCompany;
import main.java.com.excilys.computer.database.services.ServiceComputer;
import main.java.com.excilys.computer.database.validator.Validator;

@Controller
public class ComputerController {
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private final ServiceComputer serviceComputer;
	private final ServiceCompany serviceCompany;
	private final Validator validator;
	private final MapperCompany mapperCompany;
	private final MapperComputer mapperComputer;
	
	public ComputerController(ServiceComputer serviceComputer, ServiceCompany serviceCompany, Validator validator,
			MapperCompany mapperCompany, MapperComputer mapperComputer) {
		this.serviceComputer = serviceComputer;
		this.serviceCompany = serviceCompany;
		this.validator = validator;
		this.mapperCompany = mapperCompany;
		this.mapperComputer = mapperComputer;
	}
	
	public Computer enteredComputer(DTOComputer dtoComputer) throws IntroducedSuperiorException, IllegalCharacterException, DateTimeParseExceptionCDB, YearLimitException {
		validator.controleID(dtoComputer.getId());
		validator.controleText(dtoComputer.getName());
		validator.controleDate(dtoComputer.getIntroduced());
		validator.controleDate(dtoComputer.getDiscontinued());
		validator.controleID(dtoComputer.getCompanyID());
		if (!dtoComputer.getDiscontinued().equals("") && dtoComputer.getDiscontinued() != null) {
			if(!dtoComputer.getIntroduced().equals("") && dtoComputer.getIntroduced() != null) {
				validator.compareDate(LocalDate.parse(dtoComputer.getIntroduced(), formatter), LocalDate.parse(dtoComputer.getDiscontinued(), formatter)); 
			}
			else {
				throw new IntroducedSuperiorException();
			}
		}
		Computer computer = mapperComputer.toComputer(dtoComputer);
		return computer;
	}
	
	@GetMapping("addComputer")
	public String getAddComputer(ModelMap model, @RequestParam Map<String, String> params) {
		List<DTOCompany> allCompanies = mapperCompany.listToDTO(serviceCompany.getAllCompany());
		
		model.addAttribute("DTOComputer", new DTOComputer());
		model.addAttribute("allCompanies", allCompanies);
		
		return "addComputer";
	}
	
	@PostMapping("addComputer")
	public String postAddComputer(@ModelAttribute("DTOComputer") DTOComputer dtoComputer, ModelMap model) throws IllegalCharacterException, DateTimeParseExceptionCDB, YearLimitException, IntroducedSuperiorException {
		Computer computer = enteredComputer(dtoComputer);
		serviceComputer.addComputer(computer);
		
		return "redirect:dashboard";
	}
	
	@GetMapping("editComputer")
	public String getEditComputer(ModelMap model, @RequestParam Map<String, String> params) {
		String id = params.get("id");
		
		List<DTOCompany> allCompanies = mapperCompany.listToDTO(serviceCompany.getAllCompany());
		Computer computerBase = serviceComputer.detailComputer(Long.parseLong(id));
		DTOComputer dtoComputerBase = mapperComputer.toDTO(computerBase);
		
		model.addAttribute("DTOComputer", new DTOComputer());
		model.addAttribute("allCompanies", allCompanies);
		model.addAttribute("dtoComputerBase", dtoComputerBase);
		
		return "editComputer";
	}
	
	@PostMapping("editComputer")
	public String postEditComputer(@ModelAttribute("DTOComputer") DTOComputer dtoComputer, ModelMap model) throws IllegalCharacterException, DateTimeParseExceptionCDB, YearLimitException, IntroducedSuperiorException {
		Computer computer = enteredComputer(dtoComputer);
		serviceComputer.updateComputer(computer);
		
		return "redirect:dashboard";
	}
}
