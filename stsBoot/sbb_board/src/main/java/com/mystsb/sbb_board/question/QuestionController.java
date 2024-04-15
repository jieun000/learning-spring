package com.mystsb.sbb_board.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mystsb.sbb_board.answer.AnswerForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question") // URL prefix
public class QuestionController {
	
//	@GetMapping("/question/list")
////	@ResponseBody
//	public String list() {
//		return "question_list";
//	}
	
//	private final QuestionRepository questionRepository;
	private final QuestionService questionService;
	
//	@GetMapping("/list")
//	public String list(Model model) {
//		//List<Question> questionList = this.questionRepository.findAll();
//		List<Question> questionList = this.questionService.getList();
//		model.addAttribute("questionList", questionList);
//		return "question_list";
//	}
	@GetMapping("/list")
	public String list(Model model, 
			@RequestParam(value = "page", defaultValue = "0") int page) {
		//List<Question> questionList = this.questionRepository.findAll();
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging);
		return "question_list";
	}
	
//	@GetMapping(value = "/detail/{id}")
//	public String detail(Model model, @PathVariable("id") Integer id) {
//		Question question = this.questionService.getQuestion(id);
//		model.addAttribute("question", question);
//		return "question_detail";
//	}
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, 
			@PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
//	@PostMapping("/create")
//	public String questionCreate(
//			@RequestParam(value = "subject") String subject, 
//			@RequestParam(value = "content") String content) {
//		this.questionService.create(subject, content);
//		return "redirect:/question/list";
//	}
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		this.questionService.create(questionForm.getSubject(), questionForm.getContent());
		return "redirect:/question/list";
	}
	
}
