package com.mycompany.myapp.service;

import java.util.List;

import com.mycompany.myapp.vo.BoardVO;

public interface BoardService {
	List<BoardVO> getListS(); // 전체 목록 조회
	BoardVO getOneS(Long bno); // 번호로 조회
	List<BoardVO> getListWithWriterS(String writer); // 작가로 조회
	void insertS(BoardVO vo); // DB추가
	void updateS(BoardVO vo); // DB수정
	void deleteS(long bno); // DB삭제
}
