package com.mystsb.start;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HWController {
	
	@GetMapping("/")
	@ResponseBody
	public String hello() {
		return "Hello Java StsB World";
	}
	
}
