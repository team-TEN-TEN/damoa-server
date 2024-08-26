package com.tenten.damoa.post.controller;

import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.service.PostDetailQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name =  "게시물 조회", description = "id를 통해 게시물을 조회하는 API")
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
