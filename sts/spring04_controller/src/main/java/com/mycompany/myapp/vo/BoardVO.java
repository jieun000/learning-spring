package com.mycompany.myapp.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
public class BoardVO {
	private long bno; // 번호
	private String title; // 제목
	private String content; // 내용
	private String writer; // 작성자
	private Date regDate; // 등록일
	private Date updateDate; // 수정일
}
