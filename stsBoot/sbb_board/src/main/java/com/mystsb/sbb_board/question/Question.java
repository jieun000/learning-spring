package com.mystsb.sbb_board.question;

import java.time.LocalDateTime;
import java.util.List;

import com.mystsb.sbb_board.answer.Answer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter @Setter
public class Question {
	@Id // 기본키
	// 자동으로 1씩 증가, IDENTITY: 해당 속성만 별도로 번호가 차례로 증가함
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200) // 테이블 열 이름과 일치 (열의 세부 설정)
	private String subject;
	
	// TEXT를 열 데이터로 넣을 수 있음, 글자 수를 제한할 수 없는 경우
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	
	// mappedBy="참조 엔티티 속성명", cascade=질문 삭제시 답변도 모두 삭제
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
}
