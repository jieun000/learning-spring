package com.myintellij.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart")
@Getter @Setter
@ToString
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // 엔티티와 일대일 매핑
    @JoinColumn(name = "member_id") // 매핑할 외래키 지정, 미지정시 알아서 ID를 찾음
    private Member member;

}
