package com.posts.api.integration;

import com.posts.api.integration.config.H2DatabaseConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.posts.api.Application;
import com.posts.api.dto.CreatePostDto;
import com.posts.api.model.Post;
import com.posts.api.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class, H2DatabaseConfig.class}, webEnvironment = RANDOM_PORT)
public class PostControllerIntegrationTest {

	private static final String POST_POSTS = "/api/v1/posts";

	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PostRepository postRepository;

	@BeforeEach
	public void clear() {
		postRepository.deleteAll();
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
			.text("This is a text")
			.author("Author")
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post(POST_POSTS)
			.content(mapper.writeValueAsString(createPostDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.messages[0]", is("Título da postagem não informado")));
	}

	@Test
	public void whenCreatePostWithoutTextShouldReturn400() throws Exception {
		CreatePostDto createPostDto = CreatePostDto.builder()
			.title("Title")
			.author("Author")
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post(POST_POSTS)
			.content(mapper.writeValueAsString(createPostDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.messages[0]", is("Texto da postagem não informado")));
	}

	@Test
	public void whenCreatePostWithoutAuthorShouldReturn400() throws Exception {
		CreatePostDto createPostDto = CreatePostDto.builder()
			.title("Title")
			.text("This is a text")
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post(POST_POSTS)
			.content(mapper.writeValueAsString(createPostDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.messages[0]", is("Autor da postagem não informado")));
	}

	@Test
	public void whenCreatePostWithValidDataShouldSaveOnDomain() throws Exception {
		CreatePostDto createPostDto = CreatePostDto.builder()
			.title("Title")
			.text("This is a text")
			.author("Author")
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post(POST_POSTS)
			.content(mapper.writeValueAsString(createPostDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated());

		List<Post> posts = postRepository.findAll();

		assertEquals(1, posts.size());
		assertNotNull(posts.get(0).getId());
		assertEquals(createPostDto.getTitle(), posts.get(0).getTitle());
		assertEquals(createPostDto.getText(), posts.get(0).getText());
		assertEquals(createPostDto.getAuthor(), posts.get(0).getAuthor());
	}

}
