package com.tenten.damoa.post.controller;

import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.service.PostDetailQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostDetailQueryService postDetailQueryService;

    public PostController (PostDetailQueryService postDetailQueryService) {
        this.postDetailQueryService = postDetailQueryService;
    }

    @GetMapping("/posts/{id}/detail")
    public ResponseEntity<Post> getPostDetail(@PathVariable("id") Long id) {
        Post postDetail = postDetailQueryService.getPostDetail(id);
        return ResponseEntity.ok(postDetail);
    }
}
