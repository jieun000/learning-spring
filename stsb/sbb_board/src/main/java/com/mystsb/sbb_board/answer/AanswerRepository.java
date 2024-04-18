package com.mystsb.sbb_board.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mystsb.sbb_board.question.Question;

public interface AanswerRepository extends JpaRepository<Answer, Integer>{
	
	Page<Answer> findAllByQuestion(Question question, Pageable pageable);

	
}
