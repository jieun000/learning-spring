package com.mystsb.sbb_board.answer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mystsb.sbb_board.question.Question;
import com.mystsb.sbb_board.user.SiteUser;

public interface AanswerRepository extends JpaRepository<Answer, Integer>{
	
	Page<Answer> findAllByQuestion(Question question, Pageable pageable);
	Optional<List<Answer>> findAllByAuthor(SiteUser user);
	
}
