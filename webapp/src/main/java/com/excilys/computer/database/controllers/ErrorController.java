package com.excilys.computer.database.controllers;

import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ErrorController {
	
	@GetMapping("404")
	public String get404(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir, Locale locale) {
		return "404";
	}
	
	@GetMapping("403")
	public String get403(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir, Locale locale) {
		return "403";
	}
	
	@GetMapping("500")
	public String get500(ModelMap model, @RequestParam Map<String, String> params, RedirectAttributes redir, Locale locale) {
		return "500";
	}

}
