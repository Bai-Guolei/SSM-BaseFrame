package com.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试dao层的工作
 * @author Administrator
 * 推荐pring的项目就使用spring的单元测试，可以自动注入我们需要的组件
 * 1.导入spring test模块
 * 2.@ContextConfiguration制定spring的配置文件路径
 * 3.直接@Autowired要使用的组件
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
//	@Autowired
//	DepartMentMapper departMentMapper;
//	
//	@Autowired
//	EmployeeMapper employeeMapper;
	
	@Autowired
	SqlSession sqlSession;
	@Test
	public void testCRUD() {
		//1.创建spring容器
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("applicationContext.xml");
		//2.从容器中获取mapper
//		DepartMentMapper departMentMapper = context.getBean(DepartMentMapper.class);
		//测试连接是否成功
//		System.out.println(departMentMapper);
		
		//1.插入部门信息
//		departMentMapper.insertSelective(new DepartMent(null, "开发部"));
//		departMentMapper.insertSelective(new DepartMent(null, "测试部"));
		
		//2.插入员工信息
//		employeeMapper.insertSelective(new Employee(null, "Jerry", "F", "Jerry@163.com", 1));
		
		//3.批量插入员工信息，使用sqlSession
//		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//		for(int i = 0; i<1000; i++) {
//			String uid = UUID.randomUUID().toString().substring(0, 5) + i;
//			mapper.insertSelective(new Employee(null, uid, "M", uid+"@atguigu.com", 1));
//		}
	}
	
}
