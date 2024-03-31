package com.myintellij;

import com.myintellij.constant.ItemSellStatus;
import com.myintellij.entity.Item;
import com.myintellij.entity.Member;
import com.myintellij.entity.Order;
import com.myintellij.entity.OrderItem;
import com.myintellij.repository.ItemRepository;
import com.myintellij.repository.MemberRepository;
import com.myintellij.repository.OrderItemRepository;
import com.myintellij.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @PersistenceContext
    EntityManager em;

    public Item createItem() {
        Item item =  new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }

//    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {
        Order order = new Order();
        for(int i=0; i<3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem); // 아직 영속성 컨텍스트에 저장되지 않은 orderItem 엔티티를 order 엔티티에 담음
        }
        // order 엔티티를 강제로 저장하면서 강제로 flush를 호출해 영속성 컨텍스트에 있는 객체들을 데이터베이스에 반영
        orderRepository.saveAndFlush(order);
        em.clear(); // 영속성 컨텍스트의 상태를 초기화

        Order savedOrder = orderRepository.findById(order.getId()) // 초기화 했기에 데이터베이스에 주문 엔티티 조회
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size()); // itemOrder 엔티티가 3개가 DB에 저장되었는지 검사
    }

    @Autowired
    MemberRepository memberRepository;

    public Order createOrder() {
        Order order = new Order();
        for(int i=0; i<3; i++) {
            Item item = createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);
        }
        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

//    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest() {
        Order order = this.createOrder();
        order.getOrderItems().remove(0); // order 엔티티에서 관리하는 orderItem 리스트의 0번째 인덱스 요소를 제거
        em.flush();
    }

    @Autowired
    OrderItemRepository orderItemRepository;

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest() {
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();
        // 영속성 컨텍스트 초기화 후 order 엔티티에 저장했던 주문 상품 아이디를 이용해 orderItem을 DB에서 다시 조회
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);
        System.out.println("Order class: " +
                orderItem.getOrder().getClass()); // orderItem 엔티티에 있는 order 객체의 클랙스를 출력.
        System.out.println("==================================================");
        orderItem.getOrder().getOrderDate();
        System.out.println("==================================================");
    }
}
