package com.mywebsite.projectA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.mywebsite.projectA.user.SiteUser;
import com.mywebsite.projectA.user.UserFormDto;
import com.mywebsite.projectA.user.UserService;

import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ProjectAApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureMockMvc
public class MemberServiceTest {
	
	@Autowired
	private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    
    // 회원 정보를 입력한 SiteUser 엔티티를 만드는 메소드
    public SiteUser createUser() {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUsername("홍길동");
        userFormDto.setEmail("test@email.com");
        userFormDto.setPassword("12345678");
        return SiteUser.createUser(userFormDto);
    }

	@Test
	@DisplayName("회원가입 테스트")
	void saveMemberTest() {
        SiteUser user = createUser();
        SiteUser savedUser = userService.createUser(user);
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getRole(), savedUser.getRole());
	}
	
}
