package com.tenten.damoa.post.controller;

import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.service.PostDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostDetailService postDetailService;

    public PostController (PostDetailService postDetailService) {
        this.postDetailService = postDetailService;
    }

    @GetMapping("/posts/{id}/detail")
    public ResponseEntity<Post> getPostDetail(@PathVariable("id") Long id) {
        Post postDetail = postDetailService.getPostDetail(id);
        return ResponseEntity.ok(postDetail);
    }
}
