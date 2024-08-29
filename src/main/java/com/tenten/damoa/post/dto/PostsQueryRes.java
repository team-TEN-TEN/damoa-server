package com.tenten.damoa.post.dto;

import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.domain.SnsType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsQueryRes {
    private Long id;
    private String contentId;
    private SnsType type;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private int shareCount;

    public PostsQueryRes(Post post) {
        this.id = post.getId();
        this.contentId = post.getContentId();
        this.type = post.getType();
        this.title = post.getTitle();
        this.viewCount = post.getViewCount();
        this.likeCount = post.getLikeCount();
        this.shareCount = post.getShareCount();

        // content 20자 제한
        String content = post.getContent();
        this.content = content.length() > 20 ? content.substring(0, 20) : content;
    }
}