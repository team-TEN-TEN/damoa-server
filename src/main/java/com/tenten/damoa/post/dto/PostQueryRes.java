package com.tenten.damoa.post.dto;

import com.tenten.damoa.hashtag.domain.Hashtag;
import com.tenten.damoa.post.domain.Post;
import com.tenten.damoa.post.domain.SnsType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
    private List<String> hashtags;

    public PostQueryRes(Post post) {
        this.id = post.getId();
        this.contentId = post.getContentId();
        this.type = post.getType();
        this.title = post.getTitle();
        this.viewCount = post.getViewCount();
        this.likeCount = post.getLikeCount();
        this.shareCount = post.getShareCount();
        this.content = post.getContent();
        this.hashtags = post.getHashtags().stream()
                .map(Hashtag::getTag) // 해시태그 이름을 추출한다고 가정
                .collect(Collectors.toList());
    }
}
