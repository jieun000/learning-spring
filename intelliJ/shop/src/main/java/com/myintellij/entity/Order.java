package com.myintellij.entity;

import com.myintellij.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 한 명의 회원은 여러 번 주문
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL // mappedBy: 연관 관계의 주인을 설정
            ,orphanRemoval = true, fetch = FetchType.LAZY) // 고아 객체 제거를 위함
    private List<OrderItem> orderItems = new ArrayList<>(); // 하나의 주문이 여러 개의 주문 상품을 갖기에 List 자료형으로 매핑

    private LocalDateTime regTime;
    private LocalDateTime updateTime;


    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem); // orderItems 에 주문 상품 정보들 담아줌
        orderItem.setOrder(this); // Order, OrderItem(양방향 참조 관계)이므로 여기도 세팅
    }
    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member); // 상품 주문 회원 정보 세팅
        // 여러 개의 주문 상품을 장바구니 페이지에 담을 수 있도록 리스트 형태로 받아 주문 객체에 orderItem 객체를 추가
        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER); // 주문 상태를 'ORDER'로 세팅
        order.setOrderDate(LocalDateTime.now()); // 주문 시간 = 현재 시간
        return order;
    }
    public int getTotalPrice() { // 총 주문 금액 계산
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}
