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
import java.util.Locale;

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
        String lowercaseType = type.name().toLowerCase();

        String externalApiUrl = String.format("https://www.%s.com/share/%s", lowercaseType, contentId);
        //String externalApiUrl = String.format("https://www.youtube.com/");//작동 잘 하는지 예시로 유튜브에 호출해봄.

        //RestClient로 외부 API 호출하기 위한 RestClient 생성
        RestClient restClient = RestClient.create();

        //RestClient로 외부 API 호출(GET 요청)
        String result = restClient.get()
                .uri(externalApiUrl)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
                })
                .body(String.class);



        // 외부 api 호출이 정상적으로 되었다면

        // shareCount 1 증가
        post.increaseShareCount();

        // 변경된 Post를 레포지토리에 저장
        postRepository.save(post);

        // InteractionHistory에 로그 기록 (SHARE)도 남겨주기
        InteractionHistory interactionHistory = new InteractionHistory();
        interactionHistory.setCategory(InteractionCategory.SHARE);
        interactionHistory.setCreatedAt(LocalDateTime.now());
        interactionHistory.setPost(post);

        interactionHistoryRepository.save(interactionHistory);

        // 변경된 Post 반환
        return post;
    }
}
