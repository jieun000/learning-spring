package com.myintellij.dto;

import com.myintellij.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {
    private String searchDateType; // 현재 시간과 상품 등록일 비교해 상품 데이터 조회
    private ItemSellStatus searchSellStatus; // 상품 판매상태 기준 상품 데이터 조회
    private String searchBy; // 상품 조회 시 어떤 유형으로 조회할지 선택
    private String searchQuery = ""; // 검색어 저장할 변수
}
