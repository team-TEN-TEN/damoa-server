package com.tenten.damoa.post.controller;

import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import com.tenten.damoa.post.service.PostShareService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "posts", description = "게시물 공유 API")
@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostShareService postShareService;

    public PostController (PostShareService postShareService) {
        this.postShareService = postShareService;
    }

    @PatchMapping("/{postId}/share")
    public ResponseEntity<String> sharePost(@PathVariable("postId") Long id ) {
        postShareService.sharePost(id);
        return ResponseEntity.ok("게시물이 공유되었습니다.");
    }
}
