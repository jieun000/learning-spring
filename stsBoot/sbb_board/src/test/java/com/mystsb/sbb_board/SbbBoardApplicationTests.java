package com.mystsb.sbb_board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mystsb.sbb_board.answer.AanswerRepository;
import com.mystsb.sbb_board.answer.Answer;
import com.mystsb.sbb_board.question.Question;
import com.mystsb.sbb_board.question.QuestionRepository;
import com.mystsb.sbb_board.question.QuestionService;

import jakarta.transaction.Transactional;

@SpringBootTest // 스프링 부트 테스트 클래스임 의미
class SbbBoardApplicationTests {
	
	// 의존성 주입(DI, Dependency Injection)
	// : 스프링이 객체를 대신 생성하여 주입하는 기법
	@Autowired
//	private QuestionRepository questionRepository;
	private QuestionService questionService;
	
	@Autowired
	private AanswerRepository ansAanswerRepository;
	
	
////	@Test // testJpa()가 테스트 메서드임 의미
//	void testJpa1() {
//		Question q1 = new Question();
//		q1.setSubject("sbb는 무엇의 약어인가요?");
//		q1.setContent("알려주세요");
//		q1.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q1);
//		
//		Question q2 = new Question();
//		q2.setSubject("spring boot model question");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateDate(LocalDateTime.now());
//		this.questionRepository.save(q2);
//	}
//	
////	@Test
//	void testJpa2() {
//		List<Question> all = this.questionRepository.findAll();
//		assertEquals(2, all.size()); // 데이터 사이즈 비교
//		Question q = all.get(0);
//		assertEquals("sbb는 무엇의 약어인가요?", q.getSubject());
//	}
//	
////	@Test
//	void testJpa3() {
//		// Optional: null값을 유연하게 처리하기 위한 클래스
//		Optional<Question> oq = this.questionRepository.findById(1);
//		if(oq.isPresent()) {
//			Question q = oq.get();
//			assertEquals("sbb는 무엇의 약어인가요?", q.getSubject());
//		}
//	}
//	
////	@Test
//	void testJpa4() {
//		Question q = this.questionRepository.findBySubject("sbb는 무엇의 약어인가요?");
//		assertEquals(1, q.getId());
//	}
//	
////	@Test
//	void testJpa5() {
//		Question q = this.questionRepository.findBySubjectAndContent("sbb는 무엇의 약어인가요?", "알려주세요");
//		assertEquals(1, q.getId());
//	}
//
////	@Test
//	void testJpa6() {
//		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
//		Question q = qList.get(0);
//		assertEquals("sbb는 무엇의 약어인가요?", q.getSubject());
//	}
//	
////	@Test
//	void testJpa7() {
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		q.setSubject("수정된 제목");
//		this.questionRepository.save(q);
//	}
//	
////	@Test
//	void testJpa8() {
//		assertEquals(2, this.questionRepository.count());
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		this.questionRepository.delete(q);
//		assertEquals(1, this.questionRepository.count());
//	}
//	
////	@Test
//	void testJpa9() {
//		Optional<Question> oq = this.questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		
//		Answer a = new Answer();
//		a.setContent("자동으로 생성됩니다.");
//		a.setQuestion(q);
//		a.setCreateData(LocalDateTime.now());
//		this.ansAanswerRepository.save(a);
//	}
//	
////	@Test
//	void testJpa10() {
//		Optional<Answer> oa = this.ansAanswerRepository.findById(1);
//		assertTrue(oa.isPresent());
//		Answer a = oa.get();
//		assertEquals(2, a.getQuestion().getId());
//	}
//	
//	@Transactional // 메서드가 종료될 때까지 DB 세션 유지
////	@Test
//	void testJpa11() {
//		Optional<Question> oq = this.questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		
//		List<Answer> answerList = q.getAnswerList();
//		assertEquals(1, answerList.size());
//		assertEquals("자동으로 생성됩니다.", answerList.get(0).getContent());
//	}
	
//	@Test
//	void testJpa12() {
//		for(int i=1; i<=300; i++) {
//			String subject = String.format("테스트 데이터: [%03d]", i);
//			String content = "내용 없음";
//			this.questionService.create(subject, content);
//		}
//	}
	
	@Test
	void testJpa13() {
		for(int i=1; i<=300; i++) {
			String subject = String.format("테스트 데이터: [%03d]", i);
			String content = "내용 없음";
			this.questionService.create(subject, content, null);
		}
	}
}
