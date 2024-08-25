package com.tenten.damoa.post.controller;

import com.tenten.damoa.post.dto.PostLikeRes;
import com.tenten.damoa.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts/{id}")
    public ResponseEntity<String> postLike(@PathVariable Long id) {
        PostLikeRes result = postService.postLike(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getMessage());
        } else {
            return ResponseEntity.badRequest().body(result.getMessage());
        }
    }
}
