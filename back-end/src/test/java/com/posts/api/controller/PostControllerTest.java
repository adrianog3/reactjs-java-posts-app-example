package com.posts.api.controller;

import com.posts.api.dto.PostDto;
import com.posts.api.mapper.PostMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.posts.api.dto.CreatePostDto;
import com.posts.api.model.Post;
import com.posts.api.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({PostController.class, PostMapper.class})
public class PostControllerTest {

	private static final String POST_POSTS = "/api/v1/posts";
	private static final String GET_POSTS = "/api/v1/posts";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	@Autowired
	private PostMapper postMapper;

	private static final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void whenCreatePostWithoutBodyShouldReturn400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(POST_POSTS)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void whenCreatePostWithEmptyBodyShouldReturn400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(POST_POSTS)
			.content("{}")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.messages[0]", is("Autor da postagem não informado")))
			.andExpect(jsonPath("$.messages[1]", is("Texto da postagem não informado")))
			.andExpect(jsonPath("$.messages[2]", is("Título da postagem não informado")));
	}

	@Test
	public void whenCreatePostWithoutTitleShouldReturn400() throws Exception {
		CreatePostDto createPostDto = CreatePostDto.builder()
			.text("This is a text message")
			.author("fulano.silva")
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post(POST_POSTS)
			.content(mapper.writeValueAsBytes(createPostDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.messages[0]", is("Título da postagem não informado")));
	}

	@Test
	public void whenCreatePostWithoutTextShouldReturn400() throws Exception {
		CreatePostDto createPostDto = CreatePostDto.builder()
			.title("Post Title")
			.author("fulano.silva")
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post(POST_POSTS)
			.content(mapper.writeValueAsBytes(createPostDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.messages[0]", is("Texto da postagem não informado")));
	}

	@Test
	public void whenCreatePostWithoutAuthorShouldReturn400() throws Exception {
		CreatePostDto createPostDto = CreatePostDto.builder()
			.title("Post Title")
			.text("This is a text message")
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post(POST_POSTS)
			.content(mapper.writeValueAsBytes(createPostDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.messages[0]", is("Autor da postagem não informado")));
	}

	@Test
	public void whenCreatePostThrowsExceptionShouldReturn500() throws Exception {
		doThrow(RuntimeException.class).when(postService).savePost(any());

		CreatePostDto createPostDto = CreatePostDto.builder()
			.author("Author")
			.title("Post Title")
			.text("This is a text message")
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post(POST_POSTS)
			.content(mapper.writeValueAsBytes(createPostDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$.messages[0]", is("Falha ao adicionar postagem")));
	}

	@Test
	public void whenCreatePostWithValidDataShouldReturn201() throws Exception {
		CreatePostDto createPostDto = CreatePostDto.builder()
			.author("Author")
			.title("Post Title")
			.text("This is a text message")
			.build();

		ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);

		mockMvc.perform(MockMvcRequestBuilders.post(POST_POSTS)
			.content(mapper.writeValueAsBytes(createPostDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated());

		verify(postService, times(1)).savePost(postCaptor.capture());

		Post capturedPost = postCaptor.getValue();

		assertNotNull(capturedPost);
		assertEquals(Long.valueOf(0), capturedPost.getVotesCount());
		assertEquals(createPostDto.getTitle(), capturedPost.getTitle());
		assertEquals(createPostDto.getText(), capturedPost.getText());
		assertEquals(createPostDto.getAuthor(), capturedPost.getAuthor());
	}

	@Test
	public void whenSearchPostsWithPageNumberLessThanZeroShouldReturn400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(GET_POSTS)
			.param("page_number", "-1")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.messages[0]", is("Número da página deve ser maior ou igual a zero")));
	}

	@Test
	public void whenSearchPostsWithPageItemsLessThanOneShouldReturn400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(GET_POSTS)
			.param("page_number", "0")
			.param("page_items", "0")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.messages[0]", is("Quantidade de itens por página deve ser maior que zero")));
	}

	@Test
	public void whenSearchPostsThrowsUnexpectedExceptionShouldReturn500() throws Exception {
		when(postService.searchPosts(anyInt(), anyInt())).thenThrow(RuntimeException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(GET_POSTS)
			.param("page_number", "0")
			.param("page_items", "5")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$.messages[0]", is("Falha ao listar postagens")));
	}

	@Test
	public void whenSearchPostsWithValidParamsAndServiceNotThrowsExceptionShouldReturn200() throws Exception {
		Page<PostDto> page = new PageImpl<>(List.of(
			PostDto.builder()
				.id(1L)
				.title("Title")
				.text("This is a text")
				.author("Author")
				.build()
		));

		when(postService.searchPosts(anyInt(), anyInt())).thenReturn(page);

		mockMvc.perform(MockMvcRequestBuilders.get(GET_POSTS)
			.param("page_number", "0")
			.param("page_items", "5")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].title", is("Title")))
			.andExpect(jsonPath("$.content[0].text", is("This is a text")))
			.andExpect(jsonPath("$.content[0].author", is("Author")));
	}

}
