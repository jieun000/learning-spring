package com.myintellij.repository;

import com.myintellij.dto.ItemSearchDto;
import com.myintellij.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    // 상품 조회 조건을 담고 있는 itemSearchDto 객체와
    // 페이징 정보를 담고 있는 pageable 객체
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
