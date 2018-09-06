package com.zcxa.share.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcxa.share.dao.ReportMapper;
import com.zcxa.share.enums.FlagEnum;
import com.zcxa.share.vo.Page;
import com.zcxa.share.vo.PageSort;
import com.zcxa.share.vo.Product;
import com.zcxa.share.vo.Report;

@Service
public class ReportService{
	
	@Autowired
	private ReportMapper reportMapper;
	
	/**
	 * 保存报表信息
	 * @param report
	 * @return
	 */
	public int doSave(Report report) {
		//初始化创建时间创建人
		report.setIsDel(FlagEnum.NO.getId());
		report.setCreateDate(new Date());
		int id = reportMapper.insert(report);
		return id;
	}
	
	/**
	 * 列表查询
	 * @param report
	 * @param pageSort
	 * @return
	 */
	public Page<Report> list(Report report, PageSort ps) {
		PageHelper.startPage(ps.getPage(), ps.getRows());
		List<Report> list = reportMapper.selectAll();
		Page<Report> p = new Page<>();
		p.setRows(list);
		//取记录总条数
		PageInfo<Report> pageInfo = new PageInfo<>(list);
		p.setTotal(pageInfo.getTotal());
		return p;
	}
	
}
