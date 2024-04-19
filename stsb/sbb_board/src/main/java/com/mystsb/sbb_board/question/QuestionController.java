package com.mystsb.sbb_board.question;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mystsb.sbb_board.answer.Answer;
import com.mystsb.sbb_board.answer.AnswerForm;
import com.mystsb.sbb_board.answer.AnswerService;
import com.mystsb.sbb_board.category.Category;
import com.mystsb.sbb_board.category.CategoryService;
import com.mystsb.sbb_board.user.SiteUser;
import com.mystsb.sbb_board.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question") // URL prefix
public class QuestionController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	private final CategoryService categoryService;

	@GetMapping("/list/{category}")
	public String list(Model model, 
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw,
			@PathVariable(value = "category") String category
			) {
		Category cate = this.categoryService.getCategoryByTitle(category);
		//List<Question> questionList = this.questionRepository.findAll();
		Page<Question> paging = this.questionService.getList(page, kw, cate);
		model.addAttribute("kw", kw);
		model.addAttribute("paging", paging);
		model.addAttribute("category", category);
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, 
		@RequestParam(value = "page", defaultValue = "0") int page,
		@PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		this.questionService.viewsUp(question);
		Page<Answer> paging = this.answerService.getList(question, page);
		model.addAttribute("question", question);
		model.addAttribute("paging", paging);
		return "question_detail";
	}
	
	@PreAuthorize("isAuthenticated()")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/create/{category}")
	public String questionCreate(QuestionForm questionForm, Model model, 
			@PathVariable("category") String category) {
		model.addAttribute("category", category);
		return "question_form";
	}

	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{category}")
	public String questionCreate(@Valid QuestionForm questionForm, 
			BindingResult bindingResult, Principal principal, Model model,
			@PathVariable("category") String category) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("category", category);
			return "question_form";
		}
		SiteUser author = this.userService.getUser(principal.getName());
		Category cate = this.categoryService.getCategoryByTitle(category);
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), author, cate);
		return String.format("redirect:/question/list/%s", category);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm,
			@PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm,
			BindingResult bindingResult, Principal principal,
			@PathVariable("id") Integer id) {
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		return String.format("redirect:/question/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal,
			@PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName()) ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		this.questionService.delete(question);
		return "redirect:/";
	}
	
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, 
			@PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.vote(question, siteUser);
		return String.format("redirect:/question/detail/%s", id);
	}
}
