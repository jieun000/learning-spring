package com.mywebsite.projectA.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;

    public List<SiteUser> getAllUsers() {
        return userRepository.findAll();
    }

    public SiteUser saveUser(SiteUser user) {
        return userRepository.save(user);
    }
	    
}
