package com.tenten.damoa.post.controller;

import com.tenten.damoa.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Post", description = "게시글 API")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts/{postId}")
    @Operation(
        summary = "게시글 좋아요",
        description = "지정된 ID의 게시글에 대해 좋아요를 처리합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "좋아요 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "게시글이 존재하지 않습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러로 인해 처리 실패")
        }
    )
    public ResponseEntity<String> postLike(@PathVariable Long postId) {
        postService.postLike(postId);
        return ResponseEntity.ok("좋아요 처리되었습니다.");
    }
}
