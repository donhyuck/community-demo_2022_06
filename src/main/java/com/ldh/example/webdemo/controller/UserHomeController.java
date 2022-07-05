package com.ldh.example.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserHomeController {

	@RequestMapping("/user/home/main")
	public String showMain() {
		return "user/home/main";
	}

	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/user/home/main";
	}
}