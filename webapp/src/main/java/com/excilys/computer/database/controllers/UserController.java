package com.excilys.computer.database.controllers;

import java.util.Locale;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.computer.database.core.exceptions.DroitInsuffisantException;
import com.excilys.computer.database.core.exceptions.IllegalCharacterException;
import com.excilys.computer.database.core.exceptions.UserAlreadyExistException;
import com.excilys.computer.database.core.modele.Users;
import com.excilys.computer.database.dto.DTOUser;
import com.excilys.computer.database.mapper.MapperUser;
import com.excilys.computer.database.services.ServiceUser;
import com.excilys.computer.database.validator.Validator;

@Controller
public class UserController {
	private final Validator validator;
	private final ServiceUser serviceUser;
	private final MapperUser mapperUser;
	
	public UserController(Validator validator, ServiceUser serviceUser, MapperUser mapperUser) {
		this.validator = validator;
		this.serviceUser = serviceUser;
		this.mapperUser = mapperUser;
	}
	
	
	@GetMapping("login")
	public ModelAndView login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
	  ModelAndView model = new ModelAndView();
	  if (error != null) {
		model.addObject("error", "Invalid username and password!");
	  }

	  if (logout != null) {
		model.addObject("msg", "You've been logged out successfully.");
	  }
	  model.setViewName("login");

	  return model;
	}
	
	public Users enteredUser(DTOUser dtouser) throws IllegalCharacterException, UserAlreadyExistException {
		validator.controleText(dtouser.getUsername());
		validator.controleText(dtouser.getPassword());
		validator.isUserExist(dtouser.getUsername());
		Users user = mapperUser.toUser(dtouser);
		return user;
	}
	public Users enteredUpdateUser(DTOUser dtouser) throws IllegalCharacterException {
		validator.controleText(dtouser.getUsername());
		validator.controleText(dtouser.getPassword());
		Users user = mapperUser.toUser(dtouser);
		return user;
	}
	
	@GetMapping("addUser")
	public String addUser(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir, Locale locale) {
		model.addAttribute("DTOUser", new DTOUser());
		return "addUser";
	}
	
	@PostMapping("addUser")
	public String postUser(@ModelAttribute("DTOUser") DTOUser dtouser, ModelMap model, RedirectAttributes redir, Locale locale) {
		Users user = null;
		try {
			user = enteredUser(dtouser);
		}catch (IllegalCharacterException | UserAlreadyExistException e) {
			redir.addFlashAttribute("error", e.getClass().getSimpleName());
			return "redirect:addUser";
		}
		
		serviceUser.addUser(user);
		return "redirect:dashboard";
	}
	
	@GetMapping("updateUser")
	public String updateUser(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir, Locale locale) throws DroitInsuffisantException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		validator.controleAuth(auth);
		
		String username = params.get("username");
		Users user = serviceUser.getUser(username);
		DTOUser dtoUser = mapperUser.toDTo(user);
		model.addAttribute("dtoUser", dtoUser);
		model.addAttribute("DTOUser", new DTOUser());
		return "updateUser";
	}
	
	@PostMapping("updateUser")
	public String postUpdateUser(@ModelAttribute("DTOUser") DTOUser dtouser, ModelMap model, RedirectAttributes redir, Locale locale) {
		Users user = null;
		try {
			user = enteredUpdateUser(dtouser);
		}catch (IllegalCharacterException e) {
			redir.addFlashAttribute("error", e.getClass().getSimpleName());
			return "redirect:updateUser?username="+dtouser.getUsername();
		}
		
		serviceUser.updateUser(user);
		return "redirect:dashboard";
	}
}
