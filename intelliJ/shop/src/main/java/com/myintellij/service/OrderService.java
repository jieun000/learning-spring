package com.myintellij.service;

import com.myintellij.dto.OrderDto;
import com.myintellij.dto.OrderHistDto;
import com.myintellij.dto.OrderItemDto;
import com.myintellij.entity.*;
import com.myintellij.repository.ItemImgRepository;
import com.myintellij.repository.ItemRepository;
import com.myintellij.repository.MemberRepository;
import com.myintellij.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

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
        Order order = Order.createOrder(member, orderItemList);
        // 생성한 주문 엔티티를 저장
        orderRepository.save(order);
        return order.getId();
    }

    private final ItemImgRepository itemImgRepository;

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {
        // (이메일, 페이징 조건)으로 주문 목록 조회
        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email); // 유저의 총 주문 개수
        List<OrderHistDto> orderHistDtos = new ArrayList<>();
        for(Order order : orders) { // 주문 리스트를 순회하며 구매 이력 페이지에 전달할 DTO 생성
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem : orderItems) {
                // 주문 상품 대표 이미지 조회
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        } // 페이지 구현 객체를 생성해 반환
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email) {
        // 현재 로그인한 사용자와 주문 데이터 생성 사용자 같은지 검사.
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();
        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            return false;
        }
        return true;
    }
    // 주문 취소 상태로 변경 시 변경 감지 기능에 의해 트랜잭션이 끝날 때 update 쿼리 실행
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String email) {
        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();
        for(OrderDto orderDto : orderDtoList) { // 주문할 상품 리스트를 만듦
            Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }
        Order order = Order.createOrder(member, orderItemList); // (회원, 주문 상품 목록)으로 주문 엔티티 만듦
        orderRepository.save(order); // 주문 데이터를 저장
        return order.getId();
    }

}
