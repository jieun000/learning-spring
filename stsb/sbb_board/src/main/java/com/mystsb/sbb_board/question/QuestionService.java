package com.mystsb.sbb_board.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.mystsb.sbb_board.DataNotFoundException;
import com.mystsb.sbb_board.answer.Answer;
import com.mystsb.sbb_board.category.Category;
import com.mystsb.sbb_board.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service // 스프링에서 데이터 처리를 위해 작성하는 클래스
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;

	public Page<Question> getList(int page, String kw, Category category) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Question> spec = search(kw, category.getId());
//		return this.questionRepository.findAllByKeyword(kw, pageable);
//		return this.questionRepository.findAllByKeyword(kw, category.getId(), pageable);
		return this.questionRepository.findAll(spec, pageable);
	}
	
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		if(question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");		
		}
	}
	public Page<Question> getQuestion(Integer id, @RequestParam(value = "page", defaultValue = "0") int page) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
		return this.questionRepository.findAll(pageable);
	}
	
	public void create(String subject, String content, SiteUser user, Category category) {
		Question q = new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		q.setCategory(category);
		this.questionRepository.save(q);
	}
	
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
	
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
	
	private Specification<Question> search(String kw, int categoryId) {
		return new Specification<>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복 제거
				Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
				Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
				return	cb.and(cb.equal(q.get("category").get("id"), categoryId),
							cb.or(
								cb.like(q.get("subject"), "%" + kw + "%"), // 제목
								cb.like(q.get("content"), "%" + kw + "%"), // 내용
								cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
								cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
								cb.like(u2.get("username"), "%" + kw + "%") // 답변 작성자
							)
						);
			}
		};
	}
	
}
