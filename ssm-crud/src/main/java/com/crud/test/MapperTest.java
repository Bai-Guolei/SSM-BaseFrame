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
 * ����dao��Ĺ���
 * @author Administrator
 * �Ƽ�pring����Ŀ��ʹ��spring�ĵ�Ԫ���ԣ������Զ�ע��������Ҫ�����
 * 1.����spring testģ��
 * 2.@ContextConfiguration�ƶ�spring�������ļ�·��
 * 3.ֱ��@AutowiredҪʹ�õ����
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
		//1.����spring����
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("applicationContext.xml");
		//2.�������л�ȡmapper
//		DepartMentMapper departMentMapper = context.getBean(DepartMentMapper.class);
		//���������Ƿ�ɹ�
//		System.out.println(departMentMapper);
		
		//1.���벿����Ϣ
//		departMentMapper.insertSelective(new DepartMent(null, "������"));
//		departMentMapper.insertSelective(new DepartMent(null, "���Բ�"));
		
		//2.����Ա����Ϣ
//		employeeMapper.insertSelective(new Employee(null, "Jerry", "F", "Jerry@163.com", 1));
		
		//3.��������Ա����Ϣ��ʹ��sqlSession
//		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//		for(int i = 0; i<1000; i++) {
//			String uid = UUID.randomUUID().toString().substring(0, 5) + i;
//			mapper.insertSelective(new Employee(null, uid, "M", uid+"@atguigu.com", 1));
//		}
	}
	
}
