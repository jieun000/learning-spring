package com.computer.kukje.fullstack.mapper;

import com.computer.kukje.fullstack.vo.FamilyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // myBatis
public interface FamilyMapper {
    // void a(FamilyVO a); // 여기서 a는 xml의 아이디값
    void insertOne(FamilyVO vo);

    // List<FamilyVO> b();
    List<FamilyVO> listAll();

    FamilyVO getOne(Long fno);

    void delete(Long fno);

    void update(FamilyVO vo);


}
