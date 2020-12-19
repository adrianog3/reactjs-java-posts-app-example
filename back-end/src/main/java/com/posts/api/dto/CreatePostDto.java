package com.posts.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDto {

	@NotBlank(message = "Título da postagem não informado")
	private String title;

	@NotBlank(message = "Texto da postagem não informado")
	private String text;

	@NotBlank(message = "Autor da postagem não informado")
	private String author;

}
