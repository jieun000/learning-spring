package com.mycompany.myapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mycompany.myapp.mapper.BoardMapper;
import com.mycompany.myapp.vo.BoardVO;

import lombok.Setter;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MapperTest {
	
	@Setter(onMethod_ = @Autowired)
	BoardMapper mapper;
	
	// @Test
	public void testInsert() {
		BoardVO vo = new BoardVO();
		vo.setName("mapperTest4");
		mapper.insert(vo);
	}
	
}
