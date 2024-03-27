package com.myintellij.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
//public class OrderItem {
public class OrderItem extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 방식
    @JoinColumn(name = "item_id")
    private Item item; // 하나의 상품은 여러 주문 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 한 번의 주문에 여러 개의 상품을 주문

    private int orderPrice; // 주문 가격
    private int count; // 수량
//    private LocalDateTime regTime;
//    private LocalDateTime updateTime;
}
