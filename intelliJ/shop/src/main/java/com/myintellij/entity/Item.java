package com.myintellij.entity;

import com.myintellij.constant.ItemSellStatus;
import com.myintellij.dto.ItemFormDto;
import com.myintellij.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Entity // 반드시 기본키를 가져야 함
@Table(name="item") // 어떤 테이블과 매핑될지 지정
@Getter @Setter
@ToString
public class Item extends BaseEntity {

    @Id // 기본키
    @Column(name="item_id") // 매핑될 컬럼명
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키 생성 전략 AUTO
    private Long id; // 상품 코드

    @Column(nullable = false, length = 50) // not null
    private String itemNm; // 상품명
    @Column(name="price", nullable = false)
    private int price; // 가격
    @Column(nullable = false)
    private int stockNumber; // 재고수량
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태
//    private LocalDateTime regTime; // 등록 시간
//    private LocalDateTime updateTime; // 수정 시간

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber; // 주문 후 남은 수량
        if(restStock < 0) { // 재고 부족 예외 처리
            throw new OutOfStockException("상품 재고 부족. (현재 재고 수량: " + this.stockNumber + ")");
        }
        // 주문 후 남은 재고 수량을 현재 재고 값으로 할당
        this.stockNumber = restStock;
    }

    // 상품 재고 증가 메소드
    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }
}
