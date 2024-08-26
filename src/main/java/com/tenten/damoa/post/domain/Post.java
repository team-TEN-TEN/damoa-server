package com.tenten.damoa.post.domain;

import com.tenten.damoa.common.model.BaseEntity;
import com.tenten.damoa.hashtag.domain.Hashtag;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contentId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SnsType type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private int shareCount;

    @OneToMany(mappedBy = "post")
    private List<Hashtag> hashtags = new ArrayList<>();
}
