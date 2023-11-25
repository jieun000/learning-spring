package com.mycompany.myapp.mapper;

import java.util.List;

import com.mycompany.myapp.vo.UserVO;

public interface UserMapper {
	
	List<UserVO> getList(); // 전체 목록 조회
	void insert(UserVO vo); // DB추가
	
}
