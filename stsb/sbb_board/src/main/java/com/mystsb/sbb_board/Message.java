package com.mystsb.sbb_board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Message {
	private String message;
	private String href;
	
	public Message(String message, String href) {
		this.message = message;
		this.href = href;
	}
	
}
