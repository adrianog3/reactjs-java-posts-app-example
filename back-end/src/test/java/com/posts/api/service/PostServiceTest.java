package com.posts.api.service;

import com.posts.api.dto.PostDto;
import com.posts.api.mapper.PostMapper;
import com.posts.api.model.Post;
import com.posts.api.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PostServiceTest {

	@InjectMocks
	private PostService postService;

	@Mock
	private PostRepository postRepository;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private PostMapper postMapper;

	@Test
	public void whenSavePostNotThrowsExceptionShouldBeOk() {
		Post post = Post.builder().id(1L).build();

		assertDoesNotThrow(() -> postService.savePost(post));
	}

	@Test
	public void whenSavePostThrowsExceptionShouldThrowOut() {
		Post post = Post.builder().id(1L).build();

		doThrow(RuntimeException.class).when(postRepository).save(any());

		assertThrows(RuntimeException.class, () -> postService.savePost(post));
	}

	@Test
	public void whenSearchPostsIsOkShouldNotThrowsException() {
		Page<Post> response = new PageImpl<>(List.of(
			Post.builder()
				.id(1L)
				.title("Title")
				.text("This is a text")
				.author("Author")
				.build(),
			Post.builder()
				.id(2L)
				.title("Title 1")
				.text("This is a text 1")
				.author("Author 1")
				.build()
		));

		when(postRepository.findAll(any(Pageable.class))).thenReturn(response);

		Page<PostDto> posts = postService.searchPosts(0, 5);

		assertEquals(2, posts.getContent().size());
		assertEquals(1, posts.getTotalPages());
		assertEquals(2, posts.getTotalElements());
	}

	@Test
	public void whenPageNumberIsLessThanZeroShouldThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> postService.searchPosts(-1, 10));
	}

	@Test
	public void whenPageItemsIsLessThanOneShouldThrowsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> postService.searchPosts(0, 0));
	}

}
