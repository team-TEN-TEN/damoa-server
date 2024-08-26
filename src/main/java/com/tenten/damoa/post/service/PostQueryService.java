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

@Service
@RequiredArgsConstructor
public class PostQueryService {
    private final PostRepository postRepository;

    @Transactional
    public Page<PostQueryRes> getPosts(String tag, String type, String order_by, String order, String search_by, String search, int page_count, int page) {

        Sort.Direction direction = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable paging = PageRequest.of(page, page_count, Sort.by(direction, order_by));
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
            if(search_by.equals("title")) {
                spec = spec.and(PostSpecification.findByTitle(search));
            }
            else if (search_by.equals("content")) {
                spec = spec.and(PostSpecification.findByContent(search));
            }
            else {
                spec = spec.and(PostSpecification.findByTitleOrContent(search));
            }
        }

        Page<Post> postsPages = postRepository.findAll(spec, paging);

        Page<PostQueryRes> getPostsRes = postsPages.map(
                postPage -> new PostQueryRes(postPage)
        );

        return getPostsRes;
    }
}
