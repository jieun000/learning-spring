package com.myintellij.service;

import com.myintellij.entity.Member;
import com.myintellij.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // 비즈니스 로직을 담당하는 서비스 계층 클래스. 로직을 처리하다 에러가 발생하면, 변경된 데이터를 로직을 수행하기 이전 상태로 콜백시킴
@RequiredArgsConstructor // 빈에 생성자가 1개이고 생성자의 파라미터 타입이 빈으로 등록 가능하므로 @Autowired 불요
public class MemberService implements UserDetailsService {
                                // implements UserDetailsService: MemberService가 UserDetailsService을 구현함
    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member); // 이미 가입된 회원이면 IllegalStateException 예외 발생
        System.out.println("회원 가입" + member);
        return memberRepository.save(member);
    }
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    // UserDetailsService가 loadUserByUsername() 메소드를 오버라이딩함. 로그인할 유저의 email을 파라미터로 전달받음
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if(member == null){
            throw new UsernameNotFoundException(email);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

}
