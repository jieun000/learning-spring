package com.myintellij.service;

import com.myintellij.dto.OrderDto;
import com.myintellij.entity.Item;
import com.myintellij.entity.Member;
import com.myintellij.entity.Order;
import com.myintellij.entity.OrderItem;
import com.myintellij.repository.ItemRepository;
import com.myintellij.repository.MemberRepository;
import com.myintellij.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email) {
        // 주문할 상품을 조회
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email); // 현재 로그인한 회원의 이메일 정보를 이용해 회원 정보 조회
        List<OrderItem> orderItemList = new ArrayList<>();
        // (주문할 상품 엔티티, 주문 수량)로 주문 상품 엔티티 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);
    
        // (회원 정보, 주문할 상품 리스트 정보)로 주문 엔티티를 생성
        Order order = Order.creatOrder(member, orderItemList);
        // 생성한 주문 엔티티를 저장
        orderRepository.save(order);
        return order.getId();
    }
}
