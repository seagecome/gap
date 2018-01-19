package com.ethan.gap.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

	@RequestMapping(value="/")
	public String homePage(HttpServletRequest request, HttpServletResponse response, Model model){
		return "index";
	}
	
}
