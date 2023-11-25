package com.mycompany.myapp.vo;

import java.util.Date;

import lombok.Data;

@Data
public class UserVO {
	private int Num; // 기본키
	private String person; // 사람
	private Date regDate; // 등록일
	
}
