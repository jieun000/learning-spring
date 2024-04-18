package com.mystsb.sbb_board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@GetMapping("sbb")
	@ResponseBody // URL요청에 대한 응답으로 문자열을 리턴하라
	public String index() {
		return "index";
	}
	
	@GetMapping("/") // 루트 URL
	public String root() {
		return "redirect:/question/list/qna";
	}
	
}
