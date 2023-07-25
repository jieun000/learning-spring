package com.computer.study.mapper;

import com.computer.study.vo.SqlVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SqlMapper {

    void insert(SqlVO vo);
    List<SqlVO> list();
    SqlVO read(int autoNumber);
    void delete(int autoNumber);
    void update(SqlVO vo);

}
