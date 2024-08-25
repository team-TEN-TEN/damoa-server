package com.tenten.damoa.post.service;
import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.domain.SnsType;
import com.tenten.damoa.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final RestTemplate restTemplate;

    public PostService(PostRepository postRepository, RestTemplate restTemplate) {
        this.postRepository = postRepository;
        this.restTemplate = restTemplate;
    }


    public void postLike(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        String contentId = post.getContentId();
        SnsType type = post.getType();

        String apiEndpoint = getApiEndpoint(type, contentId);
        if (apiEndpoint != null) {
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(apiEndpoint, null, String.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    post.incrementLikeCount();
                    postRepository.save(post);
                }
            }
        }

    }

    private String getApiEndpoint(SnsType type, String contentId) {
        return switch (type) {
            case FACEBOOK -> "https://www.facebook.com/likes/" + contentId;
            case TWITTER -> "https://www.twitter.com/likes/" + contentId;
            case INSTAGRAM -> "https://www.instagram.com/likes/" + contentId;
            case THREADS -> "https://www.threads.net/likes/" + contentId;
            default -> null;
        };
    }
}
