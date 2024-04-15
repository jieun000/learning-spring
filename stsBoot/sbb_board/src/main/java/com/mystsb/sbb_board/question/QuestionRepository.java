package com.mystsb.sbb_board.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
// JpaRepository: JPA가 제공하는 인터페이스 중 하나, 
// CRUD 작업을 처리하는 메서드들을 내장하고 있어 데이터 관리 작업을 편리하게 처리할 수 있다
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	// 리포지터리 메서드명을 분석해 쿼리 실행 기능
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	List<Question> findBySubjectLike(String subject);
	Page<Question> findAll(Pageable pageable);
	
}
// entity: DB 테이블 생성
// repository: DB의 데이터들을 저장, 조회, 수정, 삭제 등을 할 수 있도록 도와주는 인터페이스