package com.myintellij.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}") // properties에 설정한 'updatePath' 프로퍼티 값을 읽어옴
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/**로 시작하는 URL에 접근하면 실제 서버의 파일 시스템에서
        // uploadPath에 해당하는 경로에 있는 파일을 찾아 응답
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath); // 로컬 컴퓨터에 저장된 파일을 읽어올 root 경로를 설정
    }

}
