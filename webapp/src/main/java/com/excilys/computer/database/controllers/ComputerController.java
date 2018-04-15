package com.excilys.computer.database.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.computer.database.dto.DTOCompany;
import com.excilys.computer.database.dto.DTOComputer;
import com.excilys.computer.database.core.exceptions.DateTimeParseExceptionCDB;
import com.excilys.computer.database.core.exceptions.IllegalCharacterException;
import com.excilys.computer.database.core.exceptions.IntroducedSuperiorException;
import com.excilys.computer.database.core.exceptions.YearLimitException;
import com.excilys.computer.database.mapper.MapperCompany;
import com.excilys.computer.database.mapper.MapperComputer;
import com.excilys.computer.database.core.modele.Computer;
import com.excilys.computer.database.services.ServiceCompany;
import com.excilys.computer.database.services.ServiceComputer;
import com.excilys.computer.database.validator.Validator;

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
	public String getAddComputer(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir, Locale locale) {
		List<DTOCompany> allCompanies = mapperCompany.listToDTO(serviceCompany.getAllCompany());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();	
			model.addAttribute("username", userDetail.getUsername());
		}
		
		model.addAttribute("DTOComputer", new DTOComputer());
		model.addAttribute("allCompanies", allCompanies);
		
		return "addComputer";
	}
	
	@PostMapping("addComputer")
	public String postAddComputer(@ModelAttribute("DTOComputer") DTOComputer dtoComputer, ModelMap model, RedirectAttributes redir, Locale locale){
		Computer computer = null;
		try {
			computer = enteredComputer(dtoComputer);
		} catch (DateTimeParseExceptionCDB | IntroducedSuperiorException | IllegalCharacterException
				| YearLimitException e) {
			redir.addFlashAttribute("error", e.getClass().getSimpleName());
			return "redirect:addComputer";
		}
		serviceComputer.addComputer(computer);
		return "redirect:dashboard";
	}
	
	@GetMapping("editComputer")
	public String getEditComputer(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir, Locale locale) {
		String id = params.get("id");
		
		List<DTOCompany> allCompanies = mapperCompany.listToDTO(serviceCompany.getAllCompany());
		Computer computerBase = serviceComputer.detailComputer(Long.parseLong(id));
		DTOComputer dtoComputerBase = mapperComputer.toDTO(computerBase);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();	
			model.addAttribute("username", userDetail.getUsername());
		}
		
		model.addAttribute("DTOComputer", new DTOComputer());
		model.addAttribute("allCompanies", allCompanies);
		model.addAttribute("dtoComputerBase", dtoComputerBase);
		
		return "editComputer";
	}
	
	@PostMapping("editComputer")
	public String postEditComputer(@ModelAttribute("DTOComputer") DTOComputer dtoComputer, ModelMap model, RedirectAttributes redir, Locale locale){
		Computer computer = null;
		try {
			computer = enteredComputer(dtoComputer);
		} catch (DateTimeParseExceptionCDB | IntroducedSuperiorException | IllegalCharacterException
				| YearLimitException e) {
			redir.addFlashAttribute("error", e.getClass().getSimpleName());
			return "redirect:editComputer?id="+Long.parseLong(dtoComputer.getId());
		}
		serviceComputer.updateComputer(computer);
		return "redirect:dashboard";
	}
}
