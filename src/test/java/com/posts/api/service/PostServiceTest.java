package com.posts.api.service;

import com.posts.api.model.Post;
import com.posts.api.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(SpringExtension.class)
public class PostServiceTest {

	@InjectMocks
	private PostService postService;

	@Mock
	private PostRepository postRepository;

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

}
