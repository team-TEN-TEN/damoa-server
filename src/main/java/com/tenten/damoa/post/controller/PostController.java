package com.tenten.damoa.post.controller;
import com.tenten.damoa.common.exception.BusinessException;
import com.tenten.damoa.common.exception.ErrorCode;
import com.tenten.damoa.post.service.PostShareService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tenten.damoa.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.dto.PostQueryRes;
import com.tenten.damoa.post.service.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "게시물")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {

    private final PostQueryService postQueryService;
    private final PostService postService;
    private final PostShareService postShareService;

    @GetMapping("/posts/{postId}/detail")
    @Operation(summary = "게시물 상세 조회", description = "게시물 id를 통해 상세 조회하는 API")
    public ResponseEntity<Post> getPostDetail(@PathVariable("postId") Long id) {
        Post postDetail = postQueryService.getPostDetail(id);

        return ResponseEntity.ok(postDetail);
    }

    @GetMapping("/posts")
    @Operation(summary = "게시물 목록 조회", description = "쿼리 파라미터에 따라 조건에 맞는 게시물을 반환합니다.")
    public ResponseEntity<Page<PostQueryRes>> getPosts(@RequestParam(required = false) String tag,
            @RequestParam(required = false) String type,
            @RequestParam(name = "order-by", required = false, defaultValue = "createdAt") String orderBy,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(name = "search-by", required = false, defaultValue = "title, content") String searchBy,
            @RequestParam(required = false) String search,
            @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "0") int page) {

        Page<PostQueryRes> postQueryRes = postQueryService.getPosts(tag, type, orderBy, order, searchBy,
                search, pageSize, page);

        return ResponseEntity.ok(postQueryRes);
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

    @PatchMapping("/{postId}/share")
    @Operation(
            summary = "게시글 공유",
            description = "지정된 ID의 게시글을 공유합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "공유 되었습니다."),
                    @ApiResponse(responseCode = "400", description = "게시글이 존재하지 않습니다."),
                    @ApiResponse(responseCode = "500", description = "서버 에러로 인해 처리 실패")
            }
    )
    public ResponseEntity<String> sharePost(@PathVariable("postId") Long id) {
        postShareService.sharePost(id);

        return ResponseEntity.ok("게시물이 공유되었습니다.");
    }
}