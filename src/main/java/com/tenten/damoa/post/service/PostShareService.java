package com.tenten.damoa.post.service;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import com.tenten.damoa.interaction.domain.InteractionCategory;
import com.tenten.damoa.interaction.domain.InteractionHistory;
import com.tenten.damoa.interaction.repository.InteractionHistoryRepository;
import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.domain.SnsType;
import com.tenten.damoa.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatusCode;


import java.time.LocalDateTime;

@Service
public class PostShareService {

    private final PostRepository postRepository;
    private final InteractionHistoryRepository interactionHistoryRepository;;

    public PostShareService(PostRepository postRepository, InteractionHistoryRepository interactionHistoryRepository) {
        this.postRepository = postRepository;
        this.interactionHistoryRepository = interactionHistoryRepository;
    }

    @Transactional
    public Post sharePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST));

        String contentId = post.getContentId();
        SnsType type = post.getType();



        // shareCount 1 증가
        post.increaseShareCount();

        // 변경된 Post를 저장 (트랜잭션에 의해 자동으로 저장됨)
        postRepository.save(post);

        // 변경된 Post 반환
        return post;
    }
}