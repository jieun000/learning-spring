package com.mystsb.sbb_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbbBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbbBoardApplication.class, args);
	}

}
//프로젝트 구조
//src/main/java: 자바 파일을 저장하는 공간
//com.mystsb.sbb_board: sbb_board의 자바 파일을 저장하는 공간
//SbbBoardApplication.java: 프로젝트명+Application으로 시작을 담당하는 파일 자동 생성
//src/main/resources: 자바 파일을 제외한 HTML, CSS, JS, 환경 파일(프로젝트의 설정 정보) 등을 저장하는 공간
//templates: 템플릿 파일(자바 코드를 삽입할 수 있는 HTML 형식의 파일)을 저장하는 공간
//static: 스타일시트(css), js, 이미지 파일(ipg, png 등) 등을 저장하는 공간
//application.properties: 프로젝트의 환경을 설정(환경 변수, 데이터베이스 등의 설정)
//src/test/java: 프로젝트에서 작성한 파일을 테스트하는 코드를 저장하는 공간. 
//	JUnit와 스프링 부트의 테스트 도구를 사용해 서버 미실행 상태로 
//	src/main/java에 작성한 코드를 테스트 가능
//build.gradle: 그레이들(그루비Groovy를 기반으로 한 빌드 도구로 
//		Ant, Maven 같은 이전 세대의 단점을 보완하고 장점을 취합해 만들어짐)
//		이 사용하는 환경 파일. 프로젝트에 필요한 플러그인과 라이브러리를 설치하기 위한 내용 작성
//			그루비: 그레이들 빌드 스크립트를 작성하는 데 사용하는 스크립트 언어