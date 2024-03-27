package com.myintellij.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        // 로그인 한 사용자 정보를 조회해 사용자 이름을 등록자와 수정자로 지정
        if(authentication != null) userId = authentication.getName();
        return Optional.of(userId);
    }

}
