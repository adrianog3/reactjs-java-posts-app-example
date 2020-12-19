package com.posts.api.interceptor;

import com.posts.api.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ValidationInterceptor {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDto> handleValidationExceptions(MethodArgumentNotValidException e) {
		List<String> messages = new ArrayList<>();

		for (ObjectError error : e.getBindingResult().getAllErrors()) {
			messages.add(error.getDefaultMessage());
		}

		Collections.sort(messages);

		ResponseDto responseDto = new ResponseDto(messages);

		return ResponseEntity.badRequest().body(responseDto);
	}

}
