package com.mywebsite.projectA.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mywebsite.projectA.DataNotFoundException;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    public SiteUser createUser(SiteUser user) {
        validateDuplicateMember(user); // 이미 가입된 회원이면 IllegalStateException 예외 발생
        System.out.println("회원 가입 서비스: " + user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    // 중복 회원 확인
    private void validateDuplicateMember(SiteUser user) {
        SiteUser findMember = userRepository.findByEmail(user.getEmail());
        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
    
	// 전체 회원 확인
    public List<SiteUser> getAllUsers() {
        return userRepository.findAll();
    }

    
}
