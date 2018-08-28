package com.crud.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//import com.crud.bean.Employee;
import com.github.pagehelper.PageInfo;

/**
 * 使用spring测试模块提供的测试请求功能，测试crud请求的正确性。
 * spring4测试的时候，需要servlet-api 3.0支持
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/spring-mvc.xml"})
public class MVCTest {
	//虚拟MVC请求，获取到处理结果
	MockMvc mockMvc;
	
	@Autowired
	WebApplicationContext context;
	
	@Before
	public void ininMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() throws Exception {
		//模拟请求拿到返回值
		MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5"))
		.andReturn();
		//请求成功后，请求域中会有pageInfo,可以去除PageInfo验证。
		MockHttpServletRequest request = result.getRequest();
		PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
		System.out.println("当前页码"+pageInfo.getPageNum());
		System.out.println("总页码"+pageInfo.getPages());
		System.out.println("总记录数"+pageInfo.getTotal());
		int[] nums = pageInfo.getNavigatepageNums();
		System.out.println("在页面需要显示的连续页码是");
		for(int i: nums) {
			System.out.print(" "+i);
		}
		//获取员工数据
//		List<Employee> list = pageInfo.getList();
//		for(Employee e :list) {
//			System.out.println("ID-->"+e.getdId()+"====姓名--->"+e.getEmpName());
//		}
	}
	
	
	
}
