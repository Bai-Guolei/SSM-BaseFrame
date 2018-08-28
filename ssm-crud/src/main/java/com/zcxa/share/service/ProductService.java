package com.zcxa.share.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcxa.share.dao.ProductMapper;
import com.zcxa.share.vo.Page;
import com.zcxa.share.vo.PageSort;
import com.zcxa.share.vo.Product;

@Service
public class ProductService {
	@Autowired
	ProductMapper productMapper;
	
	public Product getProductByid(Long id) {
		Product product = productMapper.findProductById(id);
		return product;
	}
	
	/**
	 * 查询所有的产品
	 * @return
	 */
	public Page<Product> list(PageSort ps) {
		PageHelper.startPage(ps.getPage(), ps.getRows());
		List<Product> list = productMapper.selectAllProduct();
		Page<Product> p = new Page<>();
		p.setRows(list);
		//取记录总条数
		PageInfo<Product> pageInfo = new PageInfo<>(list);
		p.setTotal(pageInfo.getTotal());
		return p;
	}
}
