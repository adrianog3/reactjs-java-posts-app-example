package com.posts.api.controller;

import com.posts.api.dto.CreatePostDto;
import com.posts.api.dto.PostDto;
import com.posts.api.exception.EntityNotFoundException;
import com.posts.api.mapper.PostMapper;
import com.posts.api.model.Post;
import com.posts.api.service.PostService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "When posts is found",
			content = {@Content(schema = @Schema(implementation = Page.class, subTypes = {PostDto.class}))}
		),
		@ApiResponse(responseCode = "500", description = "When an unexpected error happens")
	})
	@GetMapping(value = "/posts")
	public ResponseEntity<?> searchPosts(
		@RequestParam(value = "page_number", defaultValue = "0") Integer pageNumber,
		@RequestParam(value = "page_items", defaultValue = "5") Integer pageItems
	) {
		try {
			if (pageNumber < 0) {
				return response(HttpStatus.BAD_REQUEST, "Número da página deve ser maior ou igual a zero");
			}

			if (pageItems < 1) {
				return response(HttpStatus.BAD_REQUEST, "Quantidade de itens por página deve ser maior que zero");
			}

			Page<PostDto> posts = postService.searchPosts(pageNumber, pageItems);

			return ResponseEntity.status(HttpStatus.OK).body(posts);
		} catch (Exception e) {
			log.error("Failed to list posts", e);
			return response(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao listar postagens");
		}
	}

	@ApiResponses(value = {
		@ApiResponse(responseCode = "202", description = "Add upvote to post"),
		@ApiResponse(responseCode = "404", description = "When post not found"),
		@ApiResponse(responseCode = "500", description = "When an unexpected error happens")
	})
	@PutMapping(value = "/posts/{post_id}/upvote")
	public ResponseEntity<?> upvote(@PathVariable(value = "post_id") String postId) {
		try {
			if (!NumberUtils.isDigits(postId)) {
				return response(HttpStatus.BAD_REQUEST, "Identificador da postagem deve ser um número válido");
			}

			postService.upvote(Long.valueOf(postId));

			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (EntityNotFoundException e) {
			return response(HttpStatus.NOT_FOUND, "Postagem não encontrada");
		} catch (Exception e) {
			log.error("Failed to upvote post {}", postId, e);
			return response(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao adicionar voto");
		}
	}

}
