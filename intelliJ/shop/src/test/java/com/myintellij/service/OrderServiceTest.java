package com.myintellij.service;

import com.myintellij.ShopApplication;
import com.myintellij.constant.ItemSellStatus;
import com.myintellij.dto.OrderDto;
import com.myintellij.entity.Item;
import com.myintellij.entity.Member;
import com.myintellij.entity.Order;
import com.myintellij.entity.OrderItem;
import com.myintellij.repository.ItemRepository;
import com.myintellij.repository.MemberRepository;
import com.myintellij.repository.OrderRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;

    public Item saveItem() {
        Item item =  new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
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
    @DisplayName("주문 테스트")
    public void order() {
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto(); // 상품과 수량을 orderDto 객체에 세팅
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        // 주문 로직 호출 결과 생성된 주문 번호를 orderId 변수에 저장
        Long orderId = orderService.order(orderDto, member.getEmail());
        // 주문 번호로 저장된 주문 정보를 호출
        Order order = orderRepository.findById(orderId).orElseThrow(EntityExistsException::new);
        List<OrderItem> orderItems = order.getOrderItems();
        // 주문 상품 총 가격 계산
        int totalPrice = orderDto.getCount() * item.getPrice();
        // 주문 상품 총 가격과 DB에 저장된 상품의 가격을 비교해 같으면 테스트가 성공 종료.
        assertEquals(totalPrice, order.getTotalPrice());
    }
}
