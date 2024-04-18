package com.mystsb.sbb_board.comment;

import java.time.LocalDateTime;

import com.mystsb.sbb_board.answer.Answer;
import com.mystsb.sbb_board.question.Question;
import com.mystsb.sbb_board.user.SiteUser;

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
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;

	@ManyToOne
	private SiteUser author;
	@ManyToOne
	private Question question;
	@ManyToOne
	private Answer answer;
	
	public Integer getQuestionId() {
		Integer questionId = null;
		if(this.question != null) {
			questionId = this.question.getId();
		}
		else if(this.answer != null) {
			questionId = this.answer.getQuestion().getId();
		}
		return questionId;
	}
}
