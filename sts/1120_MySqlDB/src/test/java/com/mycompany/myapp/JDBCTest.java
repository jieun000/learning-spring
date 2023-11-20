package com.mycompany.myapp;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j // lombok
public class JDBCTest {
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// @Test
	public void testConntection() {
		try(Connection con 
			= DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/fiding", "root", "1234")) {
			log.info(con);
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
}
/* F11 실행! - 초록바시 JDBC 연결 성공! */
// JDBC(Java Database Connectivity): 자바에서 데이터베이스에 접속할 수 있도록 하는 자바 API
// API(애플리케이션 프로그래밍 인터페이스, 응용 프로그램 프로그래밍 인터페이스): 
// 컴퓨터나 컴퓨터 프로그램 사이의 연결, 일종의 소프트웨어 인터페이스.