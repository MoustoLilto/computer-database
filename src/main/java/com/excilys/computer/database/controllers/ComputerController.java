package main.java.com.excilys.computer.database.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		validator.compareDate(dtoComputer.getIntroduced(), dtoComputer.getDiscontinued());
		Computer computer = mapperComputer.toComputer(dtoComputer);
		return computer;
	}
	
	@GetMapping("addComputer")
	public String getAddComputer(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir) {
		List<DTOCompany> allCompanies = mapperCompany.listToDTO(serviceCompany.getAllCompany());
		
		model.addAttribute("DTOComputer", new DTOComputer());
		model.addAttribute("allCompanies", allCompanies);
		
		return "addComputer";
	}
	
	@PostMapping("addComputer")
	public String postAddComputer(@ModelAttribute("DTOComputer") DTOComputer dtoComputer, ModelMap model, RedirectAttributes redir){
		Computer computer = null;
		try {
			computer = enteredComputer(dtoComputer);
		} catch (DateTimeParseExceptionCDB | IntroducedSuperiorException | IllegalCharacterException
				| YearLimitException e) {
			redir.addFlashAttribute("error", e.getMessage());
			return "redirect:addComputer";
		}
		serviceComputer.addComputer(computer);
		return "redirect:dashboard";
	}
	
	@GetMapping("editComputer")
	public String getEditComputer(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir) {
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
	public String postEditComputer(@ModelAttribute("DTOComputer") DTOComputer dtoComputer, ModelMap model, RedirectAttributes redir){
		Computer computer = null;
		try {
			computer = enteredComputer(dtoComputer);
		} catch (DateTimeParseExceptionCDB | IntroducedSuperiorException | IllegalCharacterException
				| YearLimitException e) {
			redir.addFlashAttribute("error", e.getMessage());
			return "redirect:editComputer";
		}
		serviceComputer.updateComputer(computer);
		return "redirect:dashboard";
	}
}
