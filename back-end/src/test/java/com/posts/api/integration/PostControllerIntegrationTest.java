package com.posts.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posts.api.Application;
import com.posts.api.dto.CreatePostDto;
import com.posts.api.dto.PostDto;
import com.posts.api.integration.config.H2DatabaseConfig;
import com.posts.api.mock.PostMocks;
import com.posts.api.model.Post;
import com.posts.api.repository.PostRepository;
import com.posts.api.utils.CustomMapper;
import org.json.JSONObject;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
	private static final String GET_POSTS = "/api/v1/posts";
	private static final String POST_POSTS_UPVOTE = "/api/v1/posts/%s/upvote";

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

	@Test
	public void checkSearchCampaignsWithMoreThanOnePageAndItsSorting() throws Exception {
		List<Post> posts = PostMocks.build();

		postRepository.saveAll(posts);

		String json = mockMvc.perform(MockMvcRequestBuilders.get(GET_POSTS)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();

		JSONObject jsonObject = new JSONObject(json);
		JSONObject pageableObject = jsonObject.getJSONObject("pageable");

		List<PostDto> capturedPosts = CustomMapper.toArrayList(
			jsonObject.getJSONArray("content"),
			PostDto.class
		);

		assertNotNull(capturedPosts);

		// check pagination fields
		assertEquals(5, capturedPosts.size());
		assertFalse(jsonObject.getBoolean("last"));
		assertEquals(7, jsonObject.getInt("totalElements"));
		assertEquals(2, jsonObject.getInt("totalPages"));
		assertEquals(0, pageableObject.getInt("pageNumber"));
		assertEquals(5, pageableObject.getInt("pageSize"));

		// checking sort by createdAt field
		assertEquals("Title 6", capturedPosts.get(0).getTitle());
		assertEquals("Title 5", capturedPosts.get(1).getTitle());
		assertEquals("Title 2", capturedPosts.get(2).getTitle());
		assertEquals("Title 1", capturedPosts.get(3).getTitle());
		assertEquals("Title 4", capturedPosts.get(4).getTitle());
	}

	@Test
	public void checkSearchCampaignsWithOnePageAndItsSorting() throws Exception {
		List<Post> posts = PostMocks.build();

		postRepository.saveAll(posts);

		String json = mockMvc.perform(MockMvcRequestBuilders.get(GET_POSTS)
			.param("page_number", "0")
			.param("page_items", "10")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();

		JSONObject jsonObject = new JSONObject(json);
		JSONObject pageableObject = jsonObject.getJSONObject("pageable");

		List<PostDto> capturedPosts = CustomMapper.toArrayList(
			jsonObject.getJSONArray("content"),
			PostDto.class
		);

		assertNotNull(capturedPosts);

		// check pagination fields
		assertEquals(7, capturedPosts.size());
		assertTrue(jsonObject.getBoolean("last"));
		assertEquals(7, jsonObject.getInt("totalElements"));
		assertEquals(1, jsonObject.getInt("totalPages"));
		assertEquals(0, pageableObject.getInt("pageNumber"));
		assertEquals(10, pageableObject.getInt("pageSize"));

		// checking sort by createdAt field
		assertEquals("Title 6", capturedPosts.get(0).getTitle());
		assertEquals("Title 5", capturedPosts.get(1).getTitle());
		assertEquals("Title 2", capturedPosts.get(2).getTitle());
		assertEquals("Title 1", capturedPosts.get(3).getTitle());
		assertEquals("Title 4", capturedPosts.get(4).getTitle());
		assertEquals("Title 3", capturedPosts.get(5).getTitle());
		assertEquals("Title 7", capturedPosts.get(6).getTitle());
	}

	@Test
	public void whenUpvoteNonExistentPostShouldUpdateDomainAndReturn404() throws Exception {
		String requestUrl = String.format(POST_POSTS_UPVOTE, 100L);

		mockMvc.perform(MockMvcRequestBuilders.put(requestUrl)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.messages[0]", is("Postagem não encontrada")));
	}

	@Test
	public void whenUpvoteWithInvalidPostIdShouldReturn400() throws Exception {
		String requestUrl = String.format(POST_POSTS_UPVOTE, "invalid_post_id");

		mockMvc.perform(MockMvcRequestBuilders.put(requestUrl)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.messages[0]", is("Identificador da postagem deve ser um número válido")));
	}

	@Test
	public void whenUpvoteExistentPostShouldUpdateDomainAndReturn200() throws Exception {
		Post post = postRepository.save(
			Post.builder()
				.title("Title")
				.text("This is a text")
				.author("Author")
				.votesCount(5L)
				.createdAt(LocalDateTime.now())
				.build()
		);

		String requestUrl = String.format(POST_POSTS_UPVOTE, post.getId());

		mockMvc.perform(MockMvcRequestBuilders.put(requestUrl)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());

		Optional<Post> newPost = postRepository.findById(post.getId());

		assertTrue(newPost.isPresent());
		assertEquals(post.getVotesCount() + 1, newPost.get().getVotesCount());
	}

}
