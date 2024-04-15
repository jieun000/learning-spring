package com.mystsb.sbb_board.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AnswerForm {
	
	@NotEmpty(message = "내용은 필수 입력")
	private String content;
	
}
