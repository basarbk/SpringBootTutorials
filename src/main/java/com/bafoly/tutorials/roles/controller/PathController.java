package com.bafoly.tutorials.roles.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PathController {
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/")
	public String home(Principal principal, Model model){
		model.addAttribute("username", principal.getName());
		return "movielist";
	}

}
