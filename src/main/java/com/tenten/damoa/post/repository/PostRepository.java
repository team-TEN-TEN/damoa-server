package com.tenten.damoa.post.repository;

import com.tenten.damoa.post.domain.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);
}
