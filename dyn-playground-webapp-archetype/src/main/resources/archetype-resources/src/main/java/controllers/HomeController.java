/*
 * Copyright 2013 the original author or authors.
 *
 */
package ${package}.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Basic main @Controller template
 *  @author Yves Nicolas
 */
@Controller
public class HomeController {

    private String projectName;
    
	@Autowired
	public HomeController(String projectName) {
		this.projectName = projectName;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("name", projectName);
		return "home";
	}

}
