package com.myintellij.controller;

import com.myintellij.dto.OrderDto;
import com.myintellij.dto.OrderHistDto;
import com.myintellij.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 비동기 처리(@ResponseBody(), @RequestBody)
    // @ResponseBody(): HTTP 요청의 본문 body에 담긴 내용을 자바 객체로 전달
    // @RequestBody(): 자바 객체를 HTTP 요청의 body로 전달
    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order (
            @RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal) {
        // 주문 정보를 받는 orderDto 객체에 데이터 바인딩 시 에러가 있는지 검사
        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors) sb.append(fieldError.getDefaultMessage());
            // 에러 정보를 ResponseEntity 객체에 담아 반환
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        // 현재 로그인 유저의 정보를 얻기 위해 @Controller이 선언된 클래스에서 메소드 인자로
        // principal 객체를 넘겨줄 경우 해당 객체에 직접 접근 가능. principal 객체에서 현재 로그인한 회원 이메일 정보 조회.
        String email = principal.getName();
        Long orderId;
        try {
            orderId = orderService.order(orderDto, email); // 화면으로부터 넘어오는 (주문 정보, 회원 이메일 정보)로 주문 로직 호출
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } // 결과값으로 생선된 주문 번호와 요청이 성공했다는 HTTP 응답 상태 코드를 반환.
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4); // 한 번에 가져올 주문 개수 4개
        // 로그인 한 회원의 (이메일, 페이징 객체)로 화면에 전달할 주문 목록 데이터를 리턴값으로 받음
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(principal.getName(), pageable);
        model.addAttribute("orders", orderHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);
        return "order/orderHist";
    }

    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal) {
        // 자바스크립트에서 취소할 주문 번호는 조작이 가능하므로
        // 다른 사람의 주문을 취소하지 못하게 주문 취소 권한 검사
        if(!orderService.validateOrder(orderId, principal.getName())) {
            return new ResponseEntity<String>("주문 취소 권한 없음", HttpStatus.FORBIDDEN);
        } // 주문 취소 로직 호출
        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
