package com.posts.api.service;

import com.posts.api.model.Post;
import com.posts.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public void savePost(Post post) {
		postRepository.save(post);
	}

}
