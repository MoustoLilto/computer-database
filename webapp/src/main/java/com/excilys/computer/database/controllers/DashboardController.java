package com.excilys.computer.database.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computer.database.core.exceptions.DroitInsuffisantException;
import com.excilys.computer.database.core.exceptions.IllegalCharacterException;
import com.excilys.computer.database.core.exceptions.NumberFormatExceptionCDB;
import com.excilys.computer.database.core.exceptions.PageLimitException;
import com.excilys.computer.database.core.exceptions.TuplesLimitException;
import com.excilys.computer.database.core.exceptions.champInconnueException;
import com.excilys.computer.database.core.modele.Computer;
import com.excilys.computer.database.dto.Attribute;
import com.excilys.computer.database.dto.ReqAttribute;
import com.excilys.computer.database.mapper.MapperComputer;
import com.excilys.computer.database.services.ServiceComputer;
import com.excilys.computer.database.validator.Validator;

@Controller
public class DashboardController {
	
	private final ServiceComputer serviceComputer;
	private final Validator validator;
	private final MapperComputer mapperComputer;
	
	public DashboardController(ServiceComputer serviceComputer, Validator validator, MapperComputer mapperComputer) {
		this.serviceComputer = serviceComputer;
		this.validator = validator;
		this.mapperComputer = mapperComputer;
	}

	public void orderManagement(ReqAttribute reqAttribute, Attribute attribute) throws champInconnueException{
		//Cas de changement d'ordre
		if (reqAttribute.beforeOrderBy!= null && !reqAttribute.beforeOrderBy.equals("") && reqAttribute.order!= null && !reqAttribute.order.equals("")) {
			validator.controleAttribute(reqAttribute.beforeOrderBy);
			validator.controleOrder(reqAttribute.order);
			validator.controleAttribute(reqAttribute.orderBy);
			
			attribute.orderBy = reqAttribute.beforeOrderBy;
			attribute.order = reqAttribute.order;
			if (reqAttribute.orderBy.equals(attribute.orderBy)) {
				attribute.orderBy = reqAttribute.orderBy;
				if (attribute.order.equals("ASC")) {
					attribute.order = "DESC";
				}
				else {
					attribute.order = "ASC";
				}
			} else {
				attribute.orderBy = reqAttribute.orderBy;
				attribute.order = "ASC";
			}
		}
		
		//Cas de traitement normal
		else if (reqAttribute.orderBy!= null && !reqAttribute.orderBy.equals("") && !reqAttribute.orderBy.equals("computer.id") && reqAttribute.order!= null && !reqAttribute.order.equals("")) {
			validator.controleAttribute(reqAttribute.orderBy);
			validator.controleOrder(reqAttribute.order);
			attribute.orderBy = reqAttribute.orderBy;
			attribute.order = reqAttribute.order;
		}
	}
	
	public void nbrTupleManagement(ReqAttribute reqAttribute, Attribute attribute) throws NumberFormatExceptionCDB, TuplesLimitException {
		if ( reqAttribute.nbreTuples!= null && !reqAttribute.nbreTuples.equals("")) {
			validator.controleNbrTuples(reqAttribute.nbreTuples, attribute.numberOfRows);
			attribute.numeroPage = 1;
		}
		attribute.nbreTuples = reqAttribute.nbreTuples == null ? attribute.nbreTuples : Integer.parseInt(reqAttribute.nbreTuples);
	}
	
	public void numPageManagement(ReqAttribute reqAttribute, Attribute attribute) throws NumberFormatExceptionCDB, PageLimitException {
		if ( reqAttribute.numeroPage != null && !reqAttribute.numeroPage.equals("")) {
			validator.controlePage(reqAttribute.numeroPage, attribute.nbrPageMax);
		}
		attribute.numeroPage = reqAttribute.numeroPage == null ? attribute.numeroPage : Integer.parseInt(reqAttribute.numeroPage);
	}
	
	public void rechercheManagement(ReqAttribute reqAttribute, Attribute attribute) throws IllegalCharacterException {
		if (reqAttribute.recherche != null && !reqAttribute.recherche.equals("")) {
			validator.controleText(reqAttribute.recherche);
			attribute.numberOfRows = serviceComputer.getSearchNumber(reqAttribute.recherche);
			attribute.numeroPage = 1;
		} 
		else {
			attribute.numberOfRows = serviceComputer.getNombre();
		}
	}
	
	public void computersManagement(ReqAttribute reqAttribute, Attribute attribute) throws IllegalCharacterException {
		if (reqAttribute.recherche != null && !reqAttribute.recherche.equals("")) {
			validator.controleText(reqAttribute.recherche);
			attribute.recherche = reqAttribute.recherche;
			attribute.computers = serviceComputer.seachComputers(reqAttribute.recherche, attribute.numTuple, attribute.nbreTuples, attribute.orderBy, attribute.order);
			attribute.allComputers = mapperComputer.listToDTO(attribute.computers);	
		}
		else {
			attribute.recherche = "";
			attribute.computers = serviceComputer.getSomeComputers(attribute.numTuple, attribute.nbreTuples,  attribute.orderBy, attribute.order);
			attribute.allComputers = mapperComputer.listToDTO(attribute.computers);
		}
	}
	
	public void nbrPageMaxManagement(Attribute attribute) {
		attribute.nbrPageMax = (int) (Math.ceil((double)attribute.numberOfRows/(double)attribute.nbreTuples));
	}
	
	public void numTupleManagement(Attribute attribute) {
		attribute.numTuple = (attribute.numeroPage*attribute.nbreTuples)-attribute.nbreTuples;
	}
	
	public Attribute attributeManager(Map<String, String> params) throws NumberFormatExceptionCDB, IllegalCharacterException, TuplesLimitException, PageLimitException, champInconnueException {
		String ordreByReq = params.get("orderBy");
		String beforeOrderBy = params.get("beforeOrderBy");
		String nbreTupleReq = params.get("tuples");
		String numPageReq = params.get("page");
		String rechercheReq = params.get("search");
		String order = params.get("order");
		ReqAttribute attributeReq = new ReqAttribute(ordreByReq, beforeOrderBy, order, nbreTupleReq, numPageReq, rechercheReq);
		Attribute attribute = new Attribute(); 
		
		orderManagement(attributeReq, attribute);
		rechercheManagement(attributeReq, attribute);
		nbrTupleManagement(attributeReq, attribute);
		nbrPageMaxManagement(attribute);
		numPageManagement(attributeReq, attribute);
		numTupleManagement(attribute);
		computersManagement(attributeReq, attribute);
		
		return attribute;
	}

	@GetMapping("dashboard")
	public String getDashboardPage(ModelMap model, @RequestParam Map<String, String> params, Locale locale) throws NumberFormatExceptionCDB, IllegalCharacterException, TuplesLimitException, PageLimitException, champInconnueException {
		Attribute attribute = attributeManager(params);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();	
			model.addAttribute("username", userDetail.getUsername());
		}
		model.addAttribute("attribute", attribute);
		
		return "dashboard";
	}

	@PostMapping("dashboard")
	public String deleteComputer(ModelMap model, @RequestParam Map<String, String> params, Locale locale) throws NumberFormatExceptionCDB, IllegalCharacterException, TuplesLimitException, PageLimitException, champInconnueException, DroitInsuffisantException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		validator.controleAuth(auth);
		
		String selection = params.get("selection");		
		if (selection != null && !selection.equals("")) {
			List<String> computersIDString = new ArrayList<String>(Arrays.asList(selection.split(",")));
			for (String computerIDString : computersIDString) {
				long computerID = Long.parseLong(computerIDString);
				Computer computer = serviceComputer.detailComputer(computerID);
				if (computer != null) {
					serviceComputer.rmComputer(computer);
				}
			}
		}
		
		return "redirect:dashboard";
	}
}
