package com.mystsb.sbb_board.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

	private Map<String, Object> attributes; // oauth2User.getAttributes()
	
	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getProviderId() {
		return (String) attributes.get("id").toString();
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	 @Override
    public String getEmail() {
        return getNestedValue("kakao_account", "email");
    }
	
	@Override
	public String getName() {
	    return getNestedValue("kakao_account", "name");
	}
	
	private String getNestedValue(String outerKey, String innerKey) {
	    Map<String, Object> outerMap = (Map<String, Object>) attributes.get(outerKey);
	    return (String) outerMap.get(innerKey);
	}

}
