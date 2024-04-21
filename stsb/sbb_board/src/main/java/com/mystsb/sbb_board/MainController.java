package com.mystsb.sbb_board;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mystsb.sbb_board.user.PrincipalDetails;

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
	
//	@GetMapping("/test/login")
//	public @ResponseBody String testLogin(Authentication authentication,
//			@AuthenticationPrincipal PrincipalDetails userDetails) {
//		System.out.println("=============/test/login===========");
//		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
////		System.out.println("authentication: " + authentication.getPrincipal());
//		System.out.println("authentication: " + principalDetails.getUser());
//		System.out.println("userDetails: " + userDetails.getUser());
//		return "세션 정보 확인하기";
//	}
//	@GetMapping("/test/oauth/login")
//	public @ResponseBody String testOAuthLogin(Authentication authentication,
//			@AuthenticationPrincipal OAuth2User oauth) {
//		System.out.println("=============/test/oauth/login===========");
//		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
//		System.out.println("authenticateion: " + oauth2User.getAttributes());
//		System.out.println("oauth2User: " + oauth.getAttributes());
//		return "OAuth 세션 정보 확인하기";
//	}
	
}
