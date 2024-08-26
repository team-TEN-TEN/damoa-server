package com.tenten.damoa.post.service;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import com.tenten.damoa.interaction.domain.InteractionCategory;
import com.tenten.damoa.interaction.domain.InteractionHistory;
import com.tenten.damoa.interaction.repository.InteractionHistoryRepository;
import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.domain.SnsType;
import com.tenten.damoa.post.repository.PostRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final RestTemplate restTemplate;
    private final InteractionHistoryRepository interactionHistoryRepository;

    public PostService(PostRepository postRepository, RestTemplate restTemplate,
        InteractionHistoryRepository interactionHistoryRepository) {
        this.postRepository = postRepository;
        this.restTemplate = restTemplate;
        this.interactionHistoryRepository = interactionHistoryRepository;
    }


    @Transactional
    public void postLike(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        String contentId = post.getContentId();
        SnsType type = post.getType();

        String apiEndpoint = getApiEndpoint(type, contentId);

        if (apiEndpoint == null) {
            throw new BusinessException(ErrorCode.TYPE_NOT_FOUND);
        }

        try {
             restTemplate.postForEntity(apiEndpoint, null,
                String.class);
            handleLikeSuccess(post);
        } catch (Exception e) {
            handleLikeSuccess(post);
        }
    }

    private void handleLikeSuccess(Post post) {
        post.incrementLikeCount();
        InteractionHistory interactionHistory = InteractionHistory.builder()
            .category(InteractionCategory.LIKE)
            .createdAt(LocalDateTime.now())
            .post(post)
            .build();
        interactionHistoryRepository.save(interactionHistory);
    }

    private String getApiEndpoint(SnsType type, String contentId) {
        return switch (type) {
            case FACEBOOK, TWITTER, INSTAGRAM, THREADS -> type.getUrl(contentId);
            default -> null;
        };
    }
}
