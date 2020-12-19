package com.posts.api.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	@Column(name = "text", columnDefinition = "text")
	private String text;
	private String author;
	private Long votesCount = 0L;
	private LocalDateTime createdAt;

	@PrePersist
	public void setUp() {
		if (createdAt == null) {
			createdAt = LocalDateTime.now();
		}

		if (votesCount == null) {
			votesCount = 0L;
		}
	}

}
