package com.myintellij.controller;

import com.myintellij.dto.CartDetailDto;
import com.myintellij.dto.CartItemDto;
import com.myintellij.dto.CartOrderDto;
import com.myintellij.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto,
                                              BindingResult bindingResult, Principal principal) {
        // 장바구니에 담을 상품 정보를 받는 cartItemDto 객체에 데이터 바인딩 시 에러가 있는지 검사
        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName(); // 현재 로그인한 회원의 이메일 정보
        Long cartItemId;
        try { // (화면으로부터 넘어온 장바구니에 담을 상품 정보, 이메일 정보)로 장바구니에 상품을 담는 로직 호출
            cartItemId = cartService.addCart(cartItemDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHist(Principal principal, Model model) {
        // 이메일로 장바구니에 담긴 상품 정보 조회
        List<CartDetailDto> cartDetailDtoList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailDtoList);
        return "cart/cartList";
    }
    // 1. 장바구니 상품 선택 시 총 주문 금액 계산
    // 2. X버튼 클릭 시 장바구니에 담긴 상품 삭제
    // 3. 장바구니 상품 수량 변경 시 상품 금액 계산
    // 4. 장바구니 상품 수량 변경 시 장바구니에 담긴 상품 수량 업데이트
    // 5. 장바구니 상품 주문하기
    // 6. 장바구니 상품 전체 선택
    @PatchMapping(value = "/cartItem/{cartItemId}") // Patch: 요청된 자원의 일부를 업데이트
    public @ResponseBody ResponseEntity updateCartItem (@PathVariable("cartItemId") Long cartItemId,
                                                        int count, Principal principal) {
        if(count <= 0) {
            // 장바구니에 담긴 상품 개수를 0개 이하로 업데이트 요청 시 에러 메시지 담아 반환
            return new ResponseEntity<String>("최소 1개 이상", HttpStatus.BAD_REQUEST);
        } else if(!cartService.validateCartItem(cartItemId, principal.getName())) {
            // 수정 권한 체크
            return new ResponseEntity<String>("수정 권한 없음", HttpStatus.FORBIDDEN);
        }
        cartService.updateCartItemCount(cartItemId, count); // 장바구니 상품 개수 업데이트
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
    @DeleteMapping(value = "/cartItem/{cartItemId}") // Delete: 요청된 자원을 삭제
    public @ResponseBody ResponseEntity deleteCartItem (@PathVariable("cartItemId") Long cartItemId,
                                                        Principal principal) {
        if(!cartService.validateCartItem(cartItemId, principal.getName())) {
            // 수정 권한 체크
            return new ResponseEntity<String>("수정 권한 없음", HttpStatus.FORBIDDEN);
        }
        cartService.deleteCartItem(cartItemId); // 장바구니 상품을 삭제
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem (@RequestBody CartOrderDto cartOrderDto, Principal principal) {
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품 선택하기", HttpStatus.FORBIDDEN);
        }
        for(CartOrderDto cartOrder : cartOrderDtoList) {
            if(!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())) {
                return new ResponseEntity<String>("주문 권한 없음", HttpStatus.FORBIDDEN);
            }
        }
        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}