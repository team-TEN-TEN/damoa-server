package com.tenten.damoa.post.controller;

import com.tenten.damoa.post.dto.PostQueryRes;
import com.tenten.damoa.post.service.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostQueryService postQueryService;

    @GetMapping("/posts")
    public Page<PostQueryRes> getPosts(@RequestParam(required = false) String tag,
                                       @RequestParam(required = false) String type,
                                       @RequestParam(name = "order-by", required = false, defaultValue = "created_at") String order_by,
                                       @RequestParam(required = false, defaultValue = "desc") String order,
                                       @RequestParam(name = "search-by", required = false, defaultValue = "title,content") String search_by,
                                       @RequestParam(required = false) String search,
                                       @RequestParam(name = "page-count", required = false, defaultValue = "10") int page_count,
                                       @RequestParam(required = false, defaultValue = "0") int page) {

        return postQueryService.getPosts(tag, type, order_by, order, search_by, search, page_count, page);
    }

}