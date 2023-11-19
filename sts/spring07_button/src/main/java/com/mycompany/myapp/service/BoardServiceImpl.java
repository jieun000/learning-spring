package com.mycompany.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.mapper.BoardMapper;
import com.mycompany.myapp.vo.BoardVO;

import lombok.Setter;

@Service
public class BoardServiceImpl implements BoardService{

	@Setter(onMethod_ = @Autowired)
	BoardMapper mapper;
	
	@Override
	public List<BoardVO> getListS() {
		System.out.println("서비스에서 전체 목록 조회");
		return mapper.getList();
	}

	@Override
	public BoardVO getOneS(Long bno) {
		System.out.println("서비스에서 번호로 조회");
		return mapper.getOne(bno);
	}

	@Override
	public List<BoardVO> getListWithWriterS(String writer) {
		System.out.println("서비스에서 작성자로 조회");
		return mapper.getListWithWriter(writer);
	}

	@Override
	public void insertS(BoardVO vo) {
		System.out.println("서비스에서 추가: "+vo);
		mapper.insert(vo);
	}

	@Override
	public void updateS(BoardVO vo) {
		System.out.println("서비스에서 데이터 수정: "+vo);
		mapper.update(vo);
	}

	@Override
	public void deleteS(long bno) {
		System.out.println("서비스에서 삭제: "+bno);
		mapper.delete(bno);
	}

}
