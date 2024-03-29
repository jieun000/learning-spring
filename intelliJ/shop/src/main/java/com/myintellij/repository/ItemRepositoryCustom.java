package com.myintellij.repository;

import com.myintellij.dto.ItemSearchDto;
import com.myintellij.dto.MainItemDto;
import com.myintellij.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    // 상품 조회 조건을 담고 있는 itemSearchDto 객체와
    // 페이징 정보를 담고 있는 pageable 객체
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
    
    // 메인 페이지에 보여줄 상품 리스트를 가져오는 메소드
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
