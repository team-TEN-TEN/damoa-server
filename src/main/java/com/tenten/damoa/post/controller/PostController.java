package com.tenten.damoa.post.controller;

import com.tenten.damoa.post.service.PostShareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostShareService postShareService;

    public PostController (PostShareService postShareService) {
        this.postShareService = postShareService;
    }

    @PatchMapping("/{id}/share")
    public ResponseEntity<String> sharePost(@PathVariable("id") Long id ) {
        try {
            postShareService.sharePost(id);
            return ResponseEntity.ok("Post shared successfully.");//게시물 공유가 잘 되었다면 200과 함께 message 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to share post: " + e.getMessage());
        }
    }
}
