package com.zcxa.share.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zcxa.share.vo.Category;
import com.zcxa.share.vo.Product;

public interface ProductMapper {
	public List<Product> selectAllProduct();
	
	public Product findProductById(Long id);
	
	public void insertProduct(Product product);
	
	public void updateProduct(Product product);
	
	public void deleteProductById(Long id);
	
	public Product ifTest();
	
	public List<Product> foreachTest();
	
	public Product selectProWithCate(int id);
	
	public List<Category> selectCollection(int id);
	
	public Product selectProductMap(Map<String , Object> map);
	
	public Product selectProductParams(@Param("pname") String pname, @Param("pnew") String pnew);
}
