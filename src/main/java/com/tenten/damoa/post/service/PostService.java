package com.tenten.damoa.post.service;

import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


}
