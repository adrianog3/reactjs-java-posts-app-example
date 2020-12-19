package com.posts.api.service;

import com.posts.api.dto.PostDto;
import com.posts.api.exception.EntityNotFoundException;
import com.posts.api.mapper.PostMapper;
import com.posts.api.model.Post;
import com.posts.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final PostMapper postMapper;

	public void savePost(Post post) {
		postRepository.save(post);
	}

	public Page<PostDto> searchPosts(int pageNumber, int pageItems) {
		PageRequest pageRequest = PageRequest.of(
			pageNumber,
			pageItems,
			Sort.by("createdAt").descending()
		);

		return postRepository.findAll(pageRequest).map(postMapper::toPostDto);
	}

	public void upvote(Long postId) throws EntityNotFoundException {
		if (!postRepository.existsById(postId)) {
			throw new EntityNotFoundException("Post not found");
		}

		postRepository.upvote(postId);
	}

}
