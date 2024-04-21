package com.mystsb.sbb_board.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.mystsb.sbb_board.oauth.provider.FacebookUserInfo;
import com.mystsb.sbb_board.oauth.provider.GoogleUserInfo;
import com.mystsb.sbb_board.oauth.provider.KakaoUserInfo;
import com.mystsb.sbb_board.oauth.provider.NaverUserInfo;
import com.mystsb.sbb_board.oauth.provider.OAuth2UserInfo;
import com.mystsb.sbb_board.user.PrincipalDetails;
import com.mystsb.sbb_board.user.SiteUser;
import com.mystsb.sbb_board.user.UserRepository;
import com.mystsb.sbb_board.user.UserRepository2;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRepository2 userRepository2;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// System.out.println("userRequest: " + userRequest);
		// registrationId로 어떤 OAuth로 로그인 했는지 확인 가능
		System.out.println("getClientRegistaion: " + userRequest.getClientRegistration());
		System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		// 구글로그인 버튼 클릭 → 구글로그인창 → 로그인 완료 → code를 리턴(OAuth-Client라이브러리) → AccessToken 요청
		// userRequest 정보 → loadUser 함수 호출 → 구글로부터 회원 프로필 받아줌.
		System.out.println("getAttribures: " + super.loadUser(userRequest).getAttributes());
		
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			System.out.println("페이스북 로그인 요청");
			oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			System.out.println("카카오 로그인 요청");
			oAuth2UserInfo = new KakaoUserInfo((Map)oauth2User.getAttributes());
		} else {
			System.out.println("미지원 로그인 방식입니다.");
		}
//		String provider = userRequest.getClientRegistration().getRegistrationId(); // google
//		// String providerId = oauth2User.getAttribute("sub"); // google
//		String providerId = oauth2User.getAttribute("id"); // facebook
//		String username = provider + "_" + providerId;
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String password = passwordEncoder.encode("겟인데어");
//		String email = oauth2User.getAttribute("email");
//		String role = "ROLE_USER";
		
		String provider = oAuth2UserInfo.getProvider();
		String providerId = oAuth2UserInfo.getProviderId();
		String username = provider + "_" + providerId;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode("겟인데어");
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";
		
		
		SiteUser userEntity = userRepository2.findByUsername(username);
		if(userEntity == null) {
			System.out.println("최초 소셜 로그인을 감지하였습니다. \n회원가입을 진행합니다.");
//			userEntity = SiteUser.builder()
//					.username(username)
//					.password(password)
//					.email(email)
//					.role(role)
					// .provider(provider)
					// .providerId(providerId)
					// .build();
//			userRepository.save(oauthUser);
			userEntity = this.oauthCreate(username, email, password);
		} else {
			System.out.println("기존 회원입니다. 로그인을 진행합니다.");
		}
//		return new PrincipalDetails(oauthUser, oauth2User.getAttributes());
		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
	}
	
	public SiteUser oauthCreate(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		this.userRepository.save(user);
		return user;
	}
	
}
