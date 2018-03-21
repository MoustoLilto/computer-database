package main.java.com.excilys.computer.database.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import main.java.com.excilys.computer.database.exceptions.*;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(champInconnueException.class)
    public ModelAndView champInconnueExceptionHandler(final champInconnueException e) {
		return page500(e.getMessage());
    }
	@ExceptionHandler(IllegalCharacterException.class)
    public ModelAndView IllegalCharacterExceptionHandler(final IllegalCharacterException e) {
		return page500(e.getMessage());
    }
	@ExceptionHandler(NumberFormatExceptionCDB.class)
    public ModelAndView NumberFormatExceptionCDBHandler(final NumberFormatExceptionCDB e) {
		return page500(e.getMessage());
    }
	@ExceptionHandler(TuplesLimitException.class)
    public ModelAndView TuplesLimitExceptionHandler(final TuplesLimitException e) {
		return page404(e.getMessage());
    }
	@ExceptionHandler(PageLimitException.class)
    public ModelAndView PageLimitExceptionHandler(final PageLimitException e) {
		return page404(e.getMessage());
    }
	@ExceptionHandler(YearLimitException.class)
    public ModelAndView YearLimitExceptionHandler(final YearLimitException e) {
		return pageAddComputer(e.getMessage());
    }
	@ExceptionHandler(DateTimeParseExceptionCDB.class)
    public ModelAndView DateTimeParseExceptionCDBHandler(final DateTimeParseExceptionCDB e) {
		return pageAddComputer(e.getMessage());
    }
	@ExceptionHandler(IntroducedSuperiorException.class)
    public ModelAndView IntroducedSuperiorExceptionHandler(final IntroducedSuperiorException e) {
		return pageAddComputer(e.getMessage());
    }
	
	public ModelAndView page404(String message) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", message);
		mav.setViewName("404");
		return mav;
	}
	
	public ModelAndView page500(String message) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", message);
		mav.setViewName("500");
		return mav;
	}
	
	public ModelAndView page403(String message) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", message);
		mav.setViewName("403");
		return mav;
	}
	
	public ModelAndView pageAddComputer(String message) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", message);
		mav.setViewName("addComputer");
		return mav;
	}
	
	public ModelAndView pageEditComputer(String message) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", message);
		mav.setViewName("editComputer");
		return mav;
	}
}
