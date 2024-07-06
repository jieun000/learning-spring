package com.mywebsite.projectA.user;

import com.mywebsite.projectA.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class SiteUser extends BaseTimeEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	@Column(unique = true)
    private String email;
	
	@Enumerated(EnumType.STRING)
    private UserRole role;
	
    public static SiteUser createUser(UserFormDto userFormDto) {
    	SiteUser user = new SiteUser();
    	user.setUsername(userFormDto.getUsername());
    	user.setEmail(userFormDto.getEmail());
        user.setPassword(userFormDto.getPassword());
        user.setRole(UserRole.ADMIN);
        return user;
    }
}
