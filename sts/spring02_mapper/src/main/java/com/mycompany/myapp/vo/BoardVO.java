package com.mycompany.myapp.vo;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
	private int num;
	private String name;
	// sql말고 util import
	private Date date;
}
