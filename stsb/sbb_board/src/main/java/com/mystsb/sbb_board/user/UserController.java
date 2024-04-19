package com.mystsb.sbb_board.user;

import java.security.Principal;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mystsb.sbb_board.Message;
import com.mystsb.sbb_board.answer.Answer;
import com.mystsb.sbb_board.answer.AnswerService;
import com.mystsb.sbb_board.question.Question;
import com.mystsb.sbb_board.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	private final QuestionService questionService;
	private final AnswerService answerService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, 
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
			return "signup_form";
		}
		
		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		} catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFaild", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFaild", e.getMessage());
			return "signup_form";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("login")
	public String login() {
		return "login_form";
	}
	
	
	@GetMapping("/info")
	public String getInfo(Model model, Principal principal) {
		SiteUser user = this.userService.getUser(principal.getName());
		List<Question> questionList = this.questionService.getListByUser(user);
		List<Answer> answerList = this.answerService.getListByUser(user);
		model.addAttribute("questionList", questionList);
		model.addAttribute("answerList", answerList);
		model.addAttribute("user", user);
		return "profile";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/password")
	public String modifyPassword(PasswordForm passwordForm) {
		return "password_form";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/password")
	public String modifyPassword(PasswordForm passwordForm, 
			BindingResult bindingResult, Principal principal, Model model) {
		SiteUser user = this.userService.getUser(principal.getName());
		if(bindingResult.hasErrors()) {
			return "password_form";
		}
		if(!this.userService.isSamePassword(user, passwordForm.getBeforePassword()) ) {
			bindingResult.rejectValue("beforePassword", "notBeforePassword", "이전 비밀번호와 일치하지 않습니다.");
			return "password_form";
		}
		if(!passwordForm.getPassword1().equals(passwordForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
			return "password_form";
		}
		try {
			userService.modifyPassword(user, passwordForm.getPassword1());
		} catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("modifyPasswordFaild", e.getMessage());
			return "password_form";
		}
		model.addAttribute("data", new Message("비밀번호가 변경되었습니다.", "/user/info"));
		return "message";
	}
	
}
