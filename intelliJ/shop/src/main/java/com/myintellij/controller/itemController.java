package com.myintellij.controller;

import com.myintellij.dto.ItemFormDto;
import com.myintellij.dto.ItemSearchDto;
import com.myintellij.entity.Item;
import com.myintellij.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class itemController {
    private final ItemService itemService;
    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {

        if(bindingResult.hasErrors()) return "item/itemForm"; // 필수 값이 없다면 다시 상품 페이지로 전환

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫 번째 상품 이미지는 필수 입력");
            return "item/itemForm";
        }
        try {
            // 상품 저장 로직(상품 정보, 상품 이미지 정보)
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch(Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러 발생");
            return "item/itemForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto); // 조회 상품 데이터를 모델에 담아 뷰에 전달
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }
        return "item/itemForm";
    }

    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        if(bindingResult.hasErrors()) return "item/itemForm";
        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId()==null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력");
            return "item/itemForm";
        }
        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러 발생");
            return "item/itemForm";
        }
        return "redirect:/";
    }

    // value에 상품 관리 화면 진입 시 URL에 1.페이지 번호가 없는 경우와 2.페이지 번호가 있는 경우의 2가지 매핑
    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, Model model,
                             @PathVariable("page") Optional<Integer> page) {
        // 페이징을 위해 PageRequest of 메소드를 통해 Pageable 객체를 생성함 (조회할 때 페이지 번호, 한 번에 가지고 올 데이터 수)
        // URL 경로에 페이지 번호가 있으면 해당 페이지를 조회하도록 세팅, 페이지 번호가 없으면 0페이지를 조회하도록 함.
        Pageable pageable = (Pageable) PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable); // (조회 조건, 페이징 정보)
        model.addAttribute("items", items); // 조회한 상품 데이터 및 페이지 정보를 뷰에 전달
        // 페이지 전환 시 기존 검색 조건을 유지한 채 이동할 수 있게 뷰에 다시 전달
        model.addAttribute("itemSearchDto", itemSearchDto);
        // 상품 관리 메뉴 하단에 보여줄 페이지 번호의 최대 개수.
        model.addAttribute("maxPage", 5);
        return "/item/itemMng";
    }

    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item", itemFormDto);
        return "item/itemDtl";
    }
}
