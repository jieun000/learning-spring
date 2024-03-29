package com.myintellij.repository;

import com.myintellij.constant.ItemSellStatus;
import com.myintellij.dto.ItemSearchDto;
import com.myintellij.dto.MainItemDto;
import com.myintellij.dto.QMainItemDto;
import com.myintellij.entity.Item;
import com.myintellij.entity.QItem;
import com.myintellij.entity.QItemImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom { // ItemRepositoryCustom를 상속받음
    private JPAQueryFactory queryFactory; // 동적으로 쿼리를 생성하기 위한 JPAQueryFactory

    // JPAQueryFactory의 생성자로 EntityManager 객체 넣기
    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        // 상품 판매 상태 조건이 전체(null)이면 null을 리턴(결과값이 null이면 where절에서 해당 조건 무시)
        // 상품 판매 상태 조건이 판매중 or 품절 상태면 해당 조건 상품만 조회
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        // searchDateType의 값에 따라 dateTime의 값을 이전 시간 축으로 세팅 후 해당 시간 이후 등록된 상품만 조회
        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            // 하루 전으로 세팅해 반환
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            // searchDateType값 = 1m: dateTime의 시간을 한 달 전으로 세팅해 최근 한 달 동안 등록된 상품만 조회하도록 조건값 반환
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            // 한 달 전 세팅 반환
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            // 6개월 전 세팅 반환
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        // searchBy의 값에 따라 상품명에 검색어를 포함하고 있는 상품 또는
        // 상품 생성자의 아이디에 검색어를 포함하고 있는 상품 조회하도록 조건값 반환
        if(StringUtils.equals("itemNm", searchBy)){
            return QItem.item.itemNm.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        // queryFactory를 이용해 쿼리 생성
            // selectFrom(QItem.item): 상품 데이터를 조회하기 위해 QItem의 item을 지정
            // where 조건절: BooleanExpression 반환하는 조건문들을 넣어줌, '.' 단위로 넣어줄 경우 and 조건으로 인식함
            // offset: 데이터를 가지고 올 시작 인덱스를 지정
            // limit: 한 번에 가지고 올 최대 개수를 지정
            // fetchResult(): 조회한 리스트 및 전체 개수를 포함하는 쿼리 결과를 반환
                // 상품 데이터 리스트 조회 상품 및 상품 데이터 전체 개수를 조회하는 2번의 쿼리문이 실행됨.
        List<Item> content = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total); // 조회한 데이터를 Page 클래스의 구현체인 PageImpl 객체로 반환.
    }


    // 검색어가 null이 아니면 상품명에 해당 검색어가 포함되는 상품을 조회하는 조건을 반환.
    private BooleanExpression itemNmLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null
                : QItem.item.itemNm.like("%" + searchQuery + "%");
    }
    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;
        QueryResults<MainItemDto> results = queryFactory.select(
                    new QMainItemDto(item.id, item.itemNm, item.itemDetail, itemImg.imgUrl, item.price)
                ).from(itemImg)
                .join(itemImg.item, item) // itemImg와 item을 내부 조인
                .where(itemImg.repimgYn.eq("Y")) // 상품 이미지는 대표 이미지만 불러옴
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }


}
