package com.computer.kukje.fullstack.controller;

import com.computer.kukje.fullstack.mapper.ChildrenMapper;
import com.computer.kukje.fullstack.vo.ChildrenVO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChildrenController {

    @Setter(onMethod_ = @Autowired)
    private ChildrenMapper mapper;

    @GetMapping("/cinsert")
    public String in() {
        return  "childrenInsert";
    }

    @PostMapping("/insertData")
    public String insertData(ChildrenVO vo) {
        System.out.println("post 추가: " + vo);
        mapper.insertOne(vo);
        return "a";
    }

}
