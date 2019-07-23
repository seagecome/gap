package com.ethan.gap.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ethan.gap.biz.mq.spring.MessageSender;
import com.ethan.gap.web.config.WebsiteApplicationConfig;

@Controller
public class DefaultController {

	@Autowired
	private WebsiteApplicationConfig config;
	@Autowired
	private MessageSender messageSender;
	
	@RequestMapping(value="/")
	public String homePage(HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute("config", config);
		return "index";
	}
	
	@RequestMapping(value="/send")
	public String sendMsg(HttpServletRequest request, HttpServletResponse response, Model model){
		
		
		messageSender.sendMsg();
		
		model.addAttribute("config", config);
		return "index";
	}
	
}
