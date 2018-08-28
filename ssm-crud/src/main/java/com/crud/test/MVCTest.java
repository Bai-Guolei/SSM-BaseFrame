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
 * ʹ��spring����ģ���ṩ�Ĳ��������ܣ�����crud�������ȷ�ԡ�
 * spring4���Ե�ʱ����Ҫservlet-api 3.0֧��
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/spring-mvc.xml"})
public class MVCTest {
	//����MVC���󣬻�ȡ��������
	MockMvc mockMvc;
	
	@Autowired
	WebApplicationContext context;
	
	@Before
	public void ininMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() throws Exception {
		//ģ�������õ�����ֵ
		MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5"))
		.andReturn();
		//����ɹ����������л���pageInfo,����ȥ��PageInfo��֤��
		MockHttpServletRequest request = result.getRequest();
		PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
		System.out.println("��ǰҳ��"+pageInfo.getPageNum());
		System.out.println("��ҳ��"+pageInfo.getPages());
		System.out.println("�ܼ�¼��"+pageInfo.getTotal());
		int[] nums = pageInfo.getNavigatepageNums();
		System.out.println("��ҳ����Ҫ��ʾ������ҳ����");
		for(int i: nums) {
			System.out.print(" "+i);
		}
		//��ȡԱ������
//		List<Employee> list = pageInfo.getList();
//		for(Employee e :list) {
//			System.out.println("ID-->"+e.getdId()+"====����--->"+e.getEmpName());
//		}
	}
	
	
	
}
