package com.zcxa.share.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zcxa.share.service.ReportService;
import com.zcxa.share.vo.Page;
import com.zcxa.share.vo.PageSort;
import com.zcxa.share.vo.Report;
import com.zcxa.share.utils.Constants;

@RestController
@RequestMapping("/report")
public class ReportController extends BaseController{
	
	@Autowired
	private ReportService service;
	
	/**
	 * 跳转至报告列表
	 * @return
	 */
	@RequestMapping("/listView")
	public ModelAndView listView() {
		ModelAndView view =  new ModelAndView("/report/list.jsp");
		return view;
	}
	
	/**
	 * 列表查询
	 * @param report
	 * @return
	 */
	@RequestMapping("/list")
	public Object list(Report report, HttpServletRequest req, PageSort pageSort) {
		Page<Report> page = service.list(report, pageSort);
		return page;
	}
	
	
	@RequestMapping("/to-view")
	public ModelAndView toPage() {
		ModelAndView view = new ModelAndView("/report/view.jsp");
		return view;
	}
	
	@RequestMapping("/{id}")
	public Object save(@PathVariable Long id, Report r, HttpServletRequest req) {
		return id == Constants.ISADD ? insert(r, req) : update(r, req);
	}
	
	/**
	 * 新增
	 * @param r
	 */
	public Object insert(Report r, HttpServletRequest req) {
		int id = service.doSave(r);
		return success(Constants.SAVE_SUCCESS_MSG, id);
	}
	
	/**
	 * 修改
	 * @param r
	 * @return
	 */
	public Object update(Report r, HttpServletRequest req) {
		return r;
		
	}
	
}
