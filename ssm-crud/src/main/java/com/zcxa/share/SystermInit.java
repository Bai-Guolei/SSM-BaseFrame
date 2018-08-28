package com.zcxa.share;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystermInit extends HttpServlet{
	/**
	 * 
	 */
	private final Logger lg = LoggerFactory.getLogger(SystermInit.class);
	
	private static final long serialVersionUID = 1L;

	public SystermInit () {};
	
	public void init() {
		lg.debug("系统初始化开始");
	}
}
