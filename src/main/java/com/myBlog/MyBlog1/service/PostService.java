package com.myBlog.MyBlog1.service;

import com.myBlog.MyBlog1.payload.PostDto;
import com.myBlog.MyBlog1.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    void deletePostById(long id);

    PostDto getPostById(long id);

    PostDto updatePost(long id, PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
