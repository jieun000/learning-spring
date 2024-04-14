package com.mystsb.sbb_board.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mystsb.sbb_board.question.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

	private final AanswerRepository answerRepository;
	
	public void create(Question question, String content) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateData(LocalDateTime.now());
		answer.setQuestion(question);
		this.answerRepository.save(answer);
	}
	
}
