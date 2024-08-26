package com.tenten.damoa.post.service;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import com.tenten.damoa.interaction.domain.InteractionCategory;
import com.tenten.damoa.interaction.domain.InteractionHistory;
import com.tenten.damoa.interaction.repository.InteractionHistoryRepository;
import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostDetailQueryService {

    private final PostRepository postRepository;

    private final InteractionHistoryRepository interactionHistoryRepository;

    public PostDetailQueryService(PostRepository postRepository, InteractionHistoryRepository interactionHistoryRepository) {
        this.postRepository = postRepository;
        this.interactionHistoryRepository = interactionHistoryRepository;
    }
    @Transactional
    public Post getPostDetail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ErrorCode.BAD_REQUEST));

        post.increaseViewCount();
        postRepository.save(post);


        InteractionHistory interactionHistory = new InteractionHistory(
                InteractionCategory.VIEW,
                LocalDateTime.now(),
                post
        );


        interactionHistoryRepository.save(interactionHistory);
        return post;
    }
}
