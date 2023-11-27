package com.myBlog.MyBlog1.service.impl;

import com.myBlog.MyBlog1.entity.Comment;
import com.myBlog.MyBlog1.entity.CommentDto;
import com.myBlog.MyBlog1.entity.Post;
import com.myBlog.MyBlog1.exception.ResourceNotFound;
import com.myBlog.MyBlog1.repository.CommentRepository;
import com.myBlog.MyBlog1.repository.PostRepository;
import com.myBlog.MyBlog1.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
private CommentRepository commentRepository;
private PostRepository postRepository;
private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow
                (() -> new ResourceNotFound("Post Not Found with id:" + postId));

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment c = commentRepository.save(comment);
       return  mapToDto(c);

    }

    @Override
    public void deleteCommentById(long postId,long commentId) {
        Post post = postRepository.findById(postId).orElseThrow
                (() -> new ResourceNotFound("Post Not Found with id:" + postId));
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDto updateCommentById(long commentId, CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFound("Comment Not Found with id:" + commentId));

        // Update the existing comment with the data from commentDto
        existingComment.setName(commentDto.getName());
        existingComment.setEmail(commentDto.getEmail());
        existingComment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(existingComment);
        return mapToDto(updatedComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> dtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return dtos;
    }

    Comment mapToEntity(CommentDto dto){
        Comment comment=modelMapper.map(dto,Comment.class);
        //comment.setName(dto.getName());
        //comment.setEmail(dto.getEmail());
        //comment.setBody(dto.getBody());
        return comment;
    }
    CommentDto mapToDto(Comment comment){
        CommentDto dto=modelMapper.map(comment,CommentDto.class);
       // dto.setName(comment.getName());
        //dto.setEmail(comment.getEmail());
        //dto.setBody(comment.getBody());
        return dto;
    }
}
