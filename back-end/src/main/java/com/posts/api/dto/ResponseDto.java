package com.posts.api.dto;

import com.posts.api.constant.DateTimeConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResponseDto {

	private List<String> messages;

	@JsonFormat(pattern = DateTimeConstants.UTC0)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime timestamp;

	public ResponseDto(List<String> messages) {
		this.messages = messages;
		this.timestamp = LocalDateTime.now();
	}

	public ResponseDto(String message) {
		this.messages = new ArrayList<>();
		this.messages.add(message);
		this.timestamp = LocalDateTime.now();
	}

}
