package com.tenten.damoa.post.repository;

import com.tenten.damoa.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
