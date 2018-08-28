package com.zcxa.share.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zcxa.share.service.ProductService;
import com.zcxa.share.vo.Page;
import com.zcxa.share.vo.PageSort;
import com.zcxa.share.vo.Product;

@RestController
public class HomeController {
	
	private static final Logger lg = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	ProductService productService;
	
	@RequestMapping("/")
	public ModelAndView toHomePage() {
		lg.debug("跳转至首页...");
		ModelAndView view = new ModelAndView("/home/index.jsp");
		return view;
	}
	
	/**
	 * 
	 * @param name
	 * @param req
	 * @return
	 */
	@RequestMapping("/product/list")
	public Object list(PageSort pageSort, HttpServletRequest req) {
		Page<Product> pages = productService.list(pageSort);
		return pages;
	}
}
