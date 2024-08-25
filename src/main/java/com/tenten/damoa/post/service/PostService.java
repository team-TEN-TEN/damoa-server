package com.tenten.damoa.post.service;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.domain.SnsType;
import com.tenten.damoa.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void postLike(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        String contentId = post.getContentId();
        SnsType type = post.getType();

    }
}
