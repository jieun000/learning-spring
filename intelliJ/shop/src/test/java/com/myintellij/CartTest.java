package com.myintellij;

import com.myintellij.dto.MemberFormDto;
import com.myintellij.entity.Cart;
import com.myintellij.entity.Member;
import com.myintellij.repository.CartRepository;
import com.myintellij.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartTest {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @PersistenceContext
    EntityManager em;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 어디구 어디동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }
    
    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest() {
        Member member = new Member();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        em.flush(); // JPA: 영속성 컨텍스트에 데이터를 저장 후 트랜잭션이 끝날 때 flush()를 호출해 데이터베이서에 반영
        em.clear(); // JPA: 영속성 컨텍스트로부터 엔티티 조회 후 영속성 컨테스트에 엔티티가 없을 경우 데이터베이스를 조회

        Cart savedCart = cartRepository.findById(cart.getId()) // 저장된 장바구니 엔티티를 조회
                .orElseThrow(EntityNotFoundException::new);
        // member 엔티티 id와 savedCart에 매핑된 member 엔티티의 id를 비교
        assertEquals(savedCart.getMember().getId(), member.getId());
    }
}
