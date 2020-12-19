package com.posts.api.repository;

import com.posts.api.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Modifying
	@Transactional
	@Query("update Post set votesCount = votesCount + 1 where id = :postId")
	void upvote(Long postId);

}
