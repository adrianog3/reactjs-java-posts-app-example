package com.posts.api.controller;

import com.posts.api.dto.CreatePostDto;
import com.posts.api.mapper.PostMapper;
import com.posts.api.model.Post;
import com.posts.api.service.PostService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController extends CommonController {

	private final PostService postService;
	private final PostMapper postMapper;

	@ApiResponses(value = {
		@ApiResponse(responseCode = "202", description = "Create a post"),
		@ApiResponse(responseCode = "500", description = "When an unexpected error happens")
	})
	@PostMapping(
		value = "/posts",
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostDto createPostDto) {
		try {
			Post post = postMapper.toPost(createPostDto);

			postService.savePost(post);

			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			log.error("Failed to save post", e);
			return response(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao adicionar postagem");
		}
	}

}
