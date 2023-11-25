package com.mycompany.myapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mycompany.myapp.mapper.UserMapper;
import com.mycompany.myapp.vo.UserVO;

import lombok.Setter;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MapperTest {
	
	@Setter(onMethod_ = @Autowired)
	UserMapper mapper;
	
	@Test
	public void testInsert() {
		UserVO vo = new UserVO();
		vo.setPerson("mapperTest");
		mapper.insert(vo);
	}
	
//	@Test // DB commit; 안 하면 못 쓴당~~
//	public void listTest() {	// system쓰려면 jdk잘 맞춰야함
//		mapper.getList().forEach(i->System.out.println(i));
//		System.out.println("성공!");
//	}
	
}
