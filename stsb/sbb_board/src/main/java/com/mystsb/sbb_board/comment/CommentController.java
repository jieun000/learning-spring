package com.mystsb.sbb_board.comment;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mystsb.sbb_board.question.Question;
import com.mystsb.sbb_board.question.QuestionService;
import com.mystsb.sbb_board.user.SiteUser;
import com.mystsb.sbb_board.user.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/create/question/{id}")
	public String createQuestionComment(CommentForm commentForm) {
		return "comment_form";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/create/question/{id}")
	public String createQuestionComment(
			@PathVariable("id") Integer id, 
			@Valid CommentForm commentForm, 
			BindingResult bindingResult, Principal principal) {
		Optional<Question> question = Optional.ofNullable(this.questionService.getQuestion(id));
		Optional<SiteUser> user = Optional.ofNullable(this.userService.getUser(principal.getName()));
		
		if(question.isPresent() && user.isPresent()) {
			if(bindingResult.hasErrors()) {
				return "conmment_form";
			}
			Comment c = this.commentService.create(question.get(), user.get(), commentForm.getContent());
			return String.format("redirect:/question/detail/%s", c.getQuestionId());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
		}
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/modify/{id}")
	public String modifyComment(@Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
		return "comment_form";
	}
	
}
