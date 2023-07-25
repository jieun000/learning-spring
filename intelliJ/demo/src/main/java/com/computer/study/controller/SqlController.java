package com.computer.study.controller;

import com.computer.study.mapper.SqlMapper;
import com.computer.study.vo.SqlVO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SqlController {

    @Setter(onMethod_ = @Autowired)
    private SqlMapper mapper;

    @GetMapping("/insertPage")
    public String is(SqlVO vo) {
        SqlVO v = new SqlVO();
        v.setId(123456789);
        v.setName("홍길동");
        mapper.insert(v);
        return "listPage";
    }
    @GetMapping("/listPage")
    public String l(Model model) {
        List<SqlVO> list = mapper.list();
        model.addAttribute("send", list);
        return "listPage";
    }
    @GetMapping("/readPage")
    public String r(int autoNumber, Model model) {
        SqlVO one = mapper.read(autoNumber);
        model.addAttribute("send", one);
        return  "readPage";
    }
    @GetMapping("deletePage")
    public String d(int autoNumber) {
        mapper.delete(autoNumber);
        return "redirect:/list";
    }
    @PostMapping("/updatePage")
    public String u(SqlVO vo) {
        mapper.update(vo);
        return "redirect:/list";
    }
}
