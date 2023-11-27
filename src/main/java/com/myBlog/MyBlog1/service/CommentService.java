package com.myBlog.MyBlog1.service;

import com.myBlog.MyBlog1.entity.CommentDto;

import java.util.List;

public interface CommentService {
 public CommentDto createComment(long postId, CommentDto commentDto);
 public void deleteCommentById(long postId,long commentId);
 CommentDto updateCommentById(long commentId, CommentDto commentDto);
 List<CommentDto> getCommentsByPostId(long postId);
}
