package com.excilys.computer.database.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computer.database.core.exceptions.*;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(champInconnueException.class)
    public ModelAndView champInconnueExceptionHandler(final champInconnueException e) {
		return page500(e.getClass().getSimpleName());
    }
	@ExceptionHandler(IllegalCharacterException.class)
    public ModelAndView IllegalCharacterExceptionHandler(final IllegalCharacterException e) {
		return page500(e.getClass().getSimpleName());
    }
	@ExceptionHandler(NumberFormatExceptionCDB.class)
    public ModelAndView NumberFormatExceptionCDBHandler(final NumberFormatExceptionCDB e) {
		return page500(e.getClass().getSimpleName());
    }
	@ExceptionHandler(TuplesLimitException.class)
    public ModelAndView TuplesLimitExceptionHandler(final TuplesLimitException e) {
		return page404(e.getClass().getSimpleName());
    }
	@ExceptionHandler(PageLimitException.class)
    public ModelAndView PageLimitExceptionHandler(final PageLimitException e) {
		return page404(e.getClass().getSimpleName());
    }
	
	@ExceptionHandler(DroitInsuffisantException.class)
    public ModelAndView DroitInsuffisantExceptionHandler(final DroitInsuffisantException e) {
		return page403(e.getClass().getSimpleName());
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
}
