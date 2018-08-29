package com.zcxa.share.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/report")
public class ReportController{
	
	@RequestMapping("/to-view")
	public ModelAndView toPage() {
		ModelAndView view = new ModelAndView("/report/view.jsp");
		return view;
	}
}
