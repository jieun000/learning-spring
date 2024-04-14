package com.mystsb.sbb_board.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("/list")
	public String list(Model model) {
		//List<Question> questionList = this.questionRepository.findAll();
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	
}
