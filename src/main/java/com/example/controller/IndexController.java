package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/views")
public class IndexController {
	/**
	 * 锁屏
	 * @return 
	 */
	@RequestMapping("/LockScreen")
	public ModelAndView LockScreen(){
		return new ModelAndView("LockScreen");
	}
	
	@RequestMapping("/console")
	public ModelAndView console(){
		return new ModelAndView("/views/console");
	}
	
	@RequestMapping("/form")
	public ModelAndView form(){
		return new ModelAndView("/views/form");
	}
	
	@RequestMapping("/users")
	public ModelAndView users(){
		return new ModelAndView("/views/users");
	}
	
	@RequestMapping("/operaterule")
	public ModelAndView operaterule(){
		return new ModelAndView("/views/operaterule");
	}
	
}
