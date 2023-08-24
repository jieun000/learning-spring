package com.mycompany.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.myapp.mapper.BoardMapper;
import com.mycompany.myapp.vo.BoardVO;

import lombok.Setter;

@Controller
public class BoardController {
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@GetMapping("/list")
	public String getList(Model model) {
		List<BoardVO> list = mapper.getList();
		model.addAttribute("list", list);
		return "/list";
	}
	
	@GetMapping("/insert")
	public String getInsertPage() {
		return "/insertPage";
	}
	@PostMapping("/insert") // Server Path 조정해야함
	public String insertPost(BoardVO vo, RedirectAttributes rttr) {
		System.out.println("컨트롤러에서 추가 vo: " + vo);
		mapper.insert(vo);
		rttr.addFlashAttribute("result", vo.getBno());
		return "redirect:/list";
	}
	
	@GetMapping("/read")
	public String getOne(Long bno, Model model) {
		BoardVO vo = mapper.getOne(bno);
		model.addAttribute("one", vo);
		return "read";
	}
	
	@PostMapping("/update")
	public String update(BoardVO vo) {
		mapper.update(vo);
		return "redirect:/list";
	}                               
	
	@GetMapping
	public String delete(Long bno) {
		mapper.delete(bno);
		return "redirect:/list";
	}
}
