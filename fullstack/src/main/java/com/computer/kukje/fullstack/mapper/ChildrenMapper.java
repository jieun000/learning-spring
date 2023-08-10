package com.computer.kukje.fullstack.mapper;

import com.computer.kukje.fullstack.vo.ChildrenVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // myBatis
public interface ChildrenMapper {
    void insertOne(ChildrenVO vo);

//    List<ChildrenVO> listAll();
//
//    ChildrenVO getOne(Long fno);
//
//    void delete(Long fno);
//
//    void update(ChildrenVO vo);

}
