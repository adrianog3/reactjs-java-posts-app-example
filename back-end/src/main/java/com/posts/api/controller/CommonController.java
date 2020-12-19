package com.posts.api.controller;

import com.posts.api.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class CommonController {

	protected ResponseEntity<ResponseDto> response(HttpStatus httpStatus, String message) {
		ResponseDto responseDto = new ResponseDto(message);

		return ResponseEntity.status(httpStatus).body(responseDto);
	}

}
