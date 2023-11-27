package com.myBlog.MyBlog1.repository;

import com.myBlog.MyBlog1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
