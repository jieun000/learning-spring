package com.mystsb.sbb_board.oauth.provider;

public interface OAuth2UserInfo {
	
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
	
}
