package com.tenten.damoa.post.controller;

import com.tenten.damoa.post.service.PostShareService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.dto.PostQueryRes;
import com.tenten.damoa.post.service.PostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "게시물")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    private final PostQueryService postQueryService;
    private final PostShareService postShareService;

    @GetMapping("/posts/{postId}/detail")
    @Tag(name = "게시물 상세 조회 API", description = "id를 통해 게시물을 조회하는 API")
    public ResponseEntity<Post> getPostDetail(@PathVariable("postId") Long id) {
        Post postDetail = postQueryService.getPostDetail(id);
        return ResponseEntity.ok(postDetail);
    }

    @GetMapping("/posts")
    @Tag(name = "post", description = "게시글 API")
    @Operation(summary = "게시물 목록 조회 API", description = "쿼리 파라미터에 따라 조건에 맞는 게시물을 반환합니다.")
    public ResponseEntity<Page<PostQueryRes>> getPosts(@RequestParam(required = false) String tag,
            @RequestParam(required = false) String type,
            @RequestParam(name = "order-by", required = false, defaultValue = "createdAt") String orderBy,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(name = "search-by", required = false, defaultValue = "title,content") String searchBy,
            @RequestParam(required = false) String search,
            @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "0") int page) {

        Page<PostQueryRes> ResPages = postQueryService.getPosts(tag, type, orderBy, order, searchBy,
                search, pageSize, page);
        return ResponseEntity.ok(ResPages);
    }


    @PatchMapping("/{postId}/share")
    public ResponseEntity<String> sharePost(@PathVariable("postId") Long id) {
        postShareService.sharePost(id);
        return ResponseEntity.ok("게시물이 공유되었습니다.");
    }
}