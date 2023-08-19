package com.mycompany.myapp;

import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith 어노테이션: JUnit의 테스트 실행 방법을 확장하여 Spring Framework와 통합함.
//(SpringJUnit4ClassRunner 클래스): Spring 컨텍스트를 로드하고 관리하는 JUnit 테스트 러너.
//: 어노테이션을 사용하여 Spring 컨텍스트와 함께 테스트를 실행.
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
// @ContextConfiguration 어노테이션: Spring 컨텍스트 설정 파일의 경로를 지정.
// 지정한 경로의 Spring 컨텍스트 설정 파일을 로드하여 테스트 실행 시 Spring 애플리케이션 컨텍스트를 구성함.
@Log4j
// @Log4j 어노테이션: Logger 객체를 생성하고 초기화할 필요 없이 클래스 내에서 로그 메시지를 출력하는 데 사용함.
public class DataSourceTest {

	@Setter(onMethod_ = @Autowired)
	private DataSource dataSource;
	
	@Test
	public void testConnection() {
		try(Connection con = dataSource.getConnection()) {
			log.info(con);
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
}
