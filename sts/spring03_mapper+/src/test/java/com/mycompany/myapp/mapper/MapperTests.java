package com.mycompany.myapp.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mycompany.myapp.vo.BoardVO;

import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class) // pom.xml 요함
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MapperTests {

	@Setter(onMethod_ = @Autowired)
	BoardMapper mapper;
	
	@Test // DB commit; 안 하면 못 쓴당~~
	public void listTest() {	// system쓰려면 jdk잘 맞춰야함
		mapper.getList().forEach(i->System.out.println(i));
		System.out.println("성공!");
	}
	
//	@Test
	public void OneTest() {
		System.out.println(mapper.getOne(1L));
	}
	
//	@Test
	public void ListWithWriter() {
		mapper.getListWithWriter("작가얌!").forEach(i-> System.out.println(i));
	}
	
//	@Test
	public void deleteTest() {
		mapper.delete(2L);
	}

//	@Test
	public void updateTest() {
		BoardVO vo = new BoardVO();
		vo.setBno(1L);
		vo.setTitle("newTitle");
		vo.setContent("newContent");
		vo.setWriter("newWriter");
		mapper.update(vo);
	}
	
//	@Test
	public void insertTest() {
		BoardVO vo = new BoardVO();
		vo.setTitle("plusTitle");
		vo.setContent("plusContent");
		vo.setWriter("plusWriter");
		mapper.insert(vo);
	}
	
//	@Test
	public void insertDummy() {
		for(int i=0; i<10; i++) {
			BoardVO vo = new BoardVO();
			vo.setTitle("dummyDataT"+(i+1));
			vo.setContent("dummyDataC"+(i+1));
			vo.setWriter("dummyDataW"+(i+1));
			mapper.insert(vo);
		}
	}
}
