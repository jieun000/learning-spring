package com.myintellij.service;

import com.myintellij.ShopApplication;
import com.myintellij.constant.ItemSellStatus;
import com.myintellij.dto.CartItemDto;
import com.myintellij.entity.CartItem;
import com.myintellij.entity.Item;
import com.myintellij.entity.Member;
import com.myintellij.repository.CartItemRepository;
import com.myintellij.repository.ItemRepository;
import com.myintellij.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartServiceTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CartService cartService;
    @Autowired
    CartItemRepository cartItemRepository;
    public Item saveItem() {
        Item item =  new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }
    public Member saveMember() {
        Member member = new Member();
        member.setEmail("test@email.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart() {
        Item item = saveItem();
        Member member = saveMember();
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCount(5);
        cartItemDto.setItemId(item.getId()); // 장바구니에 담을 상품과 수량을 cartItemDto 객체에 세팅
        Long cartItemId = cartService.addCart(cartItemDto, member.getEmail()); // 생성된 장바구니 상품 아이디를 저장
        // 장바구니 상품 아이디로 생성된 장바구니 상품 정보를 조회
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        assertEquals(item.getId(), cartItem.getItem().getId()); // (상품 아이디, 상품 아이디) 같으면 테스트 통과
        assertEquals(cartItemDto.getCount(), cartItem.getCount()); // (담은 수량, 실제 저장된 수량) 같으면 테스트 통과

    }
}
