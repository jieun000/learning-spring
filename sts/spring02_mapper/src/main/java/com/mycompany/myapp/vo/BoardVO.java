package com.mycompany.myapp.vo;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
	private int num;
	private String name;
	private Date date;	// sql말고 util import
}
