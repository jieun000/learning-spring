package com.computer.kukje.fullstack.controller;

import com.computer.kukje.fullstack.mapper.FamilyMapper;
import com.computer.kukje.fullstack.vo.FamilyVO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FullController {

    @Setter(onMethod_ = @Autowired)
    private FamilyMapper mapper;

    // List<FamilyVO> list = new ArrayList<>();

    @GetMapping("/insertDummy")
    public String a(Model model) {      // Model -> FamilyMapper.java -> xml -> 다시 여기
        System.out.println("here is controller a page");
        int x = mapper.listAll().size(); // List에 제공하는 메서드(함수) 리스트에 들어있는 데이터 개수
        // 배열에서 length()와 동일
        for(int i=x; i< x+100; i++) {
            FamilyVO a = new FamilyVO();
            a.setBrother("아우: " + (i+1));
            a.setFather("아빠: " + (i+5));
            a.setMother("엄마: " + (i+1)*10);
            // list.add(a);
             mapper.insertOne(a);
        }
        return "a";
    }
    @GetMapping("/list")
    public  String list(Model model) {
        List<FamilyVO> list = mapper.listAll();           // DB에서 가져온 정보
        list.forEach(i-> System.out.println(i));    // 출력

        model.addAttribute("v", list);  // 브라우저에 실어보낸다.
        return "list";
    }
}
