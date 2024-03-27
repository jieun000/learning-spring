package test.java.com.shop.repository;

import com.myintellij.ShopApplication;
import com.myintellij.dto.MemberFormDto;
import com.myintellij.entity.Member;
import com.myintellij.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest(classes = ShopApplication.class)
@Transactional // 테스트 실행 후 롤백 처리
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc // for MockMvc 테스트
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired // MockMvc: 웹 브라우저에서 요청을 하는 것처럼 테스트 가능
    private MockMvc mockMvc; // 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜 객체

    public Member createMember() { // 회원 정보를 입력한 Member 엔티티를 만드는 메소드
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("가나시 다라구 마바동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

//    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() { // 저장하려고 요청한 값(기대 값)과 실제 저장된 데이터를 비교
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());
    }

//    @Test
    @DisplayName("중복 회원가입 테스트")
    public void savedDuplicateMemberTest() {
        Member member1 = new Member();
        Member member2 = new Member();
        memberService.saveMember(member1);
        Throwable e = assertThrows(IllegalStateException.class, ()-> {
            memberService.saveMember(member2);
        });
        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

    public Member createMember2(String email, String pw) { // 회원 정보를 입력한 Member 엔티티를 만드는 메소드
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("가나시 다라구 마바동");
        memberFormDto.setPassword(pw);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);
    }
    @Test
    @DisplayName("로그인 성공 테스트") // userParameter()를 이용해 이메일을 아이디로 세팅하고 로그인 URL 에 요청함
    public void loginSuccessTest() throws Exception {
        String email = "test@email.com";
        String pw = "1234";
        this.createMember2(email, pw);
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login")
                        .user(email).password(pw))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }
//    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        String email = "test@email.com";
        String pw = "1234";
        this.createMember2(email, pw);
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login")
                        .user(email).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }
}
