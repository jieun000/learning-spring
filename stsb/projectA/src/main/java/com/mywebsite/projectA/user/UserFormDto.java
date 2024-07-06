package com.mywebsite.projectA.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserFormDto {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String username;
    
    @NotEmpty(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
    
    @NotEmpty(message = "비밀번호는 필수 입력 항목입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    private String password;
 
}
