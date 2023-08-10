package com.computer.kukje.fullstack.controller;

import com.computer.kukje.fullstack.mapper.FamilyMapper;
import com.computer.kukje.fullstack.vo.FamilyVO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SampleController {
    // private List<String> list = new ArrayList<>();
    @Setter(onMethod_= @Autowired)
    private FamilyMapper mapper;

    private List<FamilyVO> list = new ArrayList<>();

    @GetMapping("/sample") // url이 여기로 온다는 뜻
    public String s(FamilyVO a) { // url의 정보가 a에 실림 -> xml로 감
        list.add(a);
        System.out.println(list.size()); //sout
        //list.forEach(i-> System.out.println(i));
        for(FamilyVO v : list) {
            System.out.println(v);
        }
        mapper.insertOne(a); // FM타입의 mapper안에 있는 insertOne함수에 a를 넣을거얌
        return "sample";
    }

    @GetMapping("/form")
    public String f() {
        return "form";
    }

    @GetMapping("/read")
    public String r(String fno, Model model) {
        System.out.println(fno);
        FamilyVO one = mapper.getOne(Long.parseLong(fno));
        System.out.println(one);
        // Model 추가 후
        model.addAttribute("one", one);
        return "read";
    }

    @GetMapping("/delete")
    public String d(String fno) {
        mapper.delete(Long.parseLong(fno));
        return "redirect:/list";
    }

    @PostMapping("/update")
    public String u(FamilyVO vo) {
        mapper.update(vo);
        return "redirect:/list";
    }
}
