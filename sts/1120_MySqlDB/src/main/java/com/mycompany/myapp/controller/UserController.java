package com.mycompany.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.myapp.mapper.UserMapper;
import com.mycompany.myapp.vo.UserVO;

import lombok.Setter;

@Controller
public class UserController {
	
	@Setter(onMethod_ = @Autowired)
	private UserMapper mapper;
	
	@GetMapping("/list")
	public String getList(Model model) {
		List<UserVO> list = mapper.getList();
		model.addAttribute("list", list);
		return "/list";
	}
	
	@GetMapping("/insert")
	public String getInsertPage() {
		return "/insertPage";
	}
	
	@PostMapping("/insert")
	public String insertPost(UserVO vo, RedirectAttributes rttr) {
		System.out.println("컨트롤러에서 추가 vo: " + vo);
		mapper.insert(vo);
		rttr.addFlashAttribute("result", vo.getNum());
		return "redirect:/list";
	}
	
}
