package com.tenten.damoa.post.dto;

import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.domain.SnsType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostQueryRes {
    private Long id;
    private String contentId;
    private SnsType type;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private int shareCount;

    public PostQueryRes(Post post) {
        this.id = post.getId();
        this.contentId = post.getContentId();
        this.type = post.getType();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.viewCount = post.getViewCount();
        this.likeCount = post.getLikeCount();
        this.shareCount = post.getShareCount();
    }
}