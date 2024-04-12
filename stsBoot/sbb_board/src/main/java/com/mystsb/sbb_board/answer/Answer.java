package com.mystsb.sbb_board.answer;

import java.time.LocalDateTime;

import com.mystsb.sbb_board.question.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createData;
	
	@ManyToOne // DB에서는 외래키 관계 생성됨
	private Question question; // 질문 엔티티를 참조하기 위함
	
}
