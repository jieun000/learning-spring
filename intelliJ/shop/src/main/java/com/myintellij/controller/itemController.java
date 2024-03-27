package com.myintellij.controller;

import com.myintellij.dto.ItemFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class itemController {
    @GetMapping(value = "/admin/item/new")
    public String itemForm() {
        return "/item/itemForm";
    }
    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "/item/itemForm";
    }

}
