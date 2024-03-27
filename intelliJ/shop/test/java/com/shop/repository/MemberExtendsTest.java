package test.java.com.shop.repository;

import com.myintellij.ShopApplication;
import com.myintellij.entity.Member;
import com.myintellij.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberExtendsTest {
    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "gildong", roles = "USER") // 시큐리티 제공, 지정한 사용자가 로그인한 상태라고 가정하고 테스트
    public void auditingTest() {
        Member newMember = new Member();
        memberRepository.save(newMember);
        em.flush();
        em.clear();
        Member member = memberRepository.findById(newMember.getId()).orElseThrow(EntityNotFoundException::new);
        System.out.println("register time: " + member.getRegTime());
        System.out.println("update time: " + member.getUpdateTime());
        System.out.println("create time: " + member.getCreatedBy());
        System.out.println("modify time: " + member.getModifiedBy());
    }
}