package com.tenten.damoa.post.service;

import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.dto.PostQueryRes;
import com.tenten.damoa.post.repository.PostRepository;
import com.tenten.damoa.post.specification.PostSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import com.tenten.damoa.interaction.domain.InteractionCategory;
import com.tenten.damoa.interaction.domain.InteractionHistory;
import com.tenten.damoa.interaction.repository.InteractionHistoryRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostQueryService {
    private final PostRepository postRepository;
    private final InteractionHistoryRepository interactionHistoryRepository;

    @Transactional
    public PostQueryRes getPostDetail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST));

        post.increaseViewCount();
        postRepository.save(post);

        PostQueryRes res= new PostQueryRes(post);

        //builder 패턴을 이용하여 객체(생성자) 생성
        InteractionHistory interactionHistory = InteractionHistory.builder()
                .category(InteractionCategory.VIEW)
                .createdAt(LocalDateTime.now())
                .post(post)
                .build();


        interactionHistoryRepository.save(interactionHistory);
        return res;
    }

    @Transactional
    public Page<PostQueryRes> getPosts(String tag, String type, String orderBy, String order, String searchBy, String search, int pageSize, int page) {

        Sort.Direction direction = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(direction, orderBy));
        Specification<Post> spec = (root, query, criteriaBuilder) -> null;

        if (tag != null && !tag.isBlank()) {
            spec = spec.and(PostSpecification.findByHashtag(tag));
        } else {    // 로그인된 사용자 정보를 가져와야 하지만, Spring Security가 아직 없으므로 하드코딩
            spec = spec.and(PostSpecification.findByHashtag("tenten"));
        }
        if (type != null && !type.isBlank()) {
            spec = spec.and(PostSpecification.findByType(type));
        }
        if (search != null && !search.isBlank()) {
            if (searchBy.equals("title")) {
                spec = spec.and(PostSpecification.findByTitle(search));
            } else if (searchBy.equals("content")) {
                spec = spec.and(PostSpecification.findByContent(search));
            } else {
                spec = spec.and(PostSpecification.findByTitleOrContent(search));
            }
        }
        Page<Post> postsPages = postRepository.findAll(spec, paging);

        return postsPages.map(PostQueryRes::new);
    }
}