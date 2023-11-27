package com.myBlog.MyBlog1.service.impl;

import com.myBlog.MyBlog1.entity.Post;
import com.myBlog.MyBlog1.exception.ResourceNotFound;
import com.myBlog.MyBlog1.payload.PostDto;
import com.myBlog.MyBlog1.payload.PostResponse;
import com.myBlog.MyBlog1.repository.PostRepository;
import com.myBlog.MyBlog1.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post is not Found with Id:" + id)
        );
        postRepository.deleteById(id);
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post Not Found by id:" + id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post Not Found by id:" + id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> pagePostObject = postRepository.findAll(pageable);
        List<Post> posts = pagePostObject.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse response=new PostResponse();
        response.setDto(dtos);
        response.setPageNo(pagePostObject.getNumber());
        response.setTotalPages(pagePostObject.getTotalPages());
        response.setLastPage(pagePostObject.isLast());
        response.setPageSize(pagePostObject.getSize());
        return response;

    }


    Post mapToEntity(PostDto postDto){
        Post post=modelMapper.map(postDto,Post.class);
        //post.setId(postDto.getId());
        //post.setTitle(postDto.getTitle());
        //post.setDescription(postDto.getDescription());
        //post.setContent(postDto.getContent());
        return post;
    }
    PostDto mapToDto(Post post){
        PostDto postDto=modelMapper.map(post,PostDto.class);
        //postDto.setId(post.getId());
        //postDto.setTitle(post.getTitle());
        //postDto.setDescription(post.getDescription());
        //postDto.setContent(post.getContent());
        return postDto;
    }


}
