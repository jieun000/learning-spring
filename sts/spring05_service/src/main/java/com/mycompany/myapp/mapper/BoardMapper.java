package com.mycompany.myapp.mapper;

import java.util.List;

import com.mycompany.myapp.vo.BoardVO;

public interface BoardMapper {
	List<BoardVO> getList(); // 전체 목록 조회
	BoardVO getOne(Long bno); // 번호로 조회
	List<BoardVO> getListWithWriter(String writer); // 작가로 조회
	void insert(BoardVO vo); // DB추가
	void update(BoardVO vo); // DB수정
	void delete(long bno); // DB삭제
}
