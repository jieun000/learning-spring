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
//    private LocalDateTime regTime; => extends BaseEntity
//    private LocalDateTime updateTime;

    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item); // 주문 상품
        orderItem.setCount(count); // 주문 수량
        orderItem.setOrderPrice(item.getPrice()); // 현재 시간 기준 상품 가격을 주문 가격으로 세팅
        item.removeStock(count); // 주문 수량만큼 상품 재고 수량 감소
        return orderItem;
    }
    public int getTotalPrice() { // 주문 가격과 주문 수량을 곱해 해당 상품 총 가격 계산.
        return orderPrice * count;
    }

}
