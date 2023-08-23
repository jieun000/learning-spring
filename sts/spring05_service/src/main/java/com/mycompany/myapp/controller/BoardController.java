package com.mycompany.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.myapp.service.BoardService;
import com.mycompany.myapp.vo.BoardVO;

import lombok.Setter;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Setter(onMethod_ = @Autowired)
	private BoardService service;
	
	@GetMapping("/list")
	public String getList(Model model) {
		List<BoardVO> list = service.getListS();
		model.addAttribute("list", list);
		return "/board/list";
	}
	
	@GetMapping("/insert")
	public String getInsertPage() {
		return "/board/insertPage";
	}
	@PostMapping("/insert") // Server Path 조정해야함
	public String insertPost(BoardVO vo, RedirectAttributes rttr) {
		System.out.println("컨트롤러에서 추가 vo: " + vo);
		service.insertS(vo);
		rttr.addFlashAttribute("result", vo.getBno());
		return "redirect:/board/list";
	}
	
	@GetMapping("/read")
	public String getOne(Long bno, Model model) {
		BoardVO vo = service.getOneS(bno);
		model.addAttribute("one", vo);
		return "/board/read";
	}
	
	// 에러
	@PostMapping("/update")
	public String update(BoardVO vo) {
		service.updateS(vo);
		return "redirect:/board/list";
	}                               
	
	@GetMapping("/delete")
	public String delete(Long bno) {
		service.deleteS(bno);
		return "redirect:/board/list";
	}
}
