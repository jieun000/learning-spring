package com.mystsb.sbb_board.question;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mystsb.sbb_board.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service // 스프링에서 데이터 처리를 위해 작성하는 클래스
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	public List<Question> getList() {
		return this.questionRepository.findAll();
	}
	
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		if(question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");		}
	}
}
