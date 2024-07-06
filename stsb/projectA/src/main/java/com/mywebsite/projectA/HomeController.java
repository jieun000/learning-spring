package com.mywebsite.projectA;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@GetMapping("/")
	@ResponseBody
	public String root() {
		return "server run";
//		return "redirect:/question/list/qna";
	}
	
}
