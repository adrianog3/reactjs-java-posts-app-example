package com.posts.api.mapper;

import com.posts.api.dto.CreatePostDto;
import com.posts.api.dto.PostDto;
import com.posts.api.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

	Post toPost(CreatePostDto postDto);

	PostDto toPostDto(Post post);

}
