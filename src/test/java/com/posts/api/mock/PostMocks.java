package com.posts.api.mock;

import com.posts.api.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public class PostMocks {

	public static List<Post> build() {
		return List.of(
			Post.builder() // 4
				.title("Title 1")
				.author("Author 1")
				.text("Text 1")
				.votesCount(20L)
				.createdAt(LocalDateTime.now())
				.build(),
			Post.builder() // 3
				.title("Title 2")
				.author("Author 2")
				.text("Text 2")
				.votesCount(21L)
				.createdAt(LocalDateTime.now().plusDays(1))
				.build(),
			Post.builder() // 6
				.title("Title 3")
				.author("Author 3")
				.text("Text 3")
				.votesCount(46L)
				.createdAt(LocalDateTime.now().minusDays(4))
				.build(),
			Post.builder() // 5
				.title("Title 4")
				.author("Author 4")
				.text("Text 4")
				.votesCount(14L)
				.createdAt(LocalDateTime.now().minusDays(3))
				.build(),
			Post.builder() // 2
				.title("Title 5")
				.author("Author 5")
				.text("Text 5")
				.votesCount(2L)
				.createdAt(LocalDateTime.now().plusDays(2))
				.build(),
			Post.builder() // 1
				.title("Title 6")
				.author("Author 6")
				.text("Text 6")
				.votesCount(3L)
				.createdAt(LocalDateTime.now().plusDays(10))
				.build(),
			Post.builder() // 7
				.title("Title 7")
				.author("Author 7")
				.text("Text 7")
				.votesCount(6L)
				.createdAt(LocalDateTime.now().minusDays(6))
				.build()
		);
	}

}
