package com.tenten.damoa.post.specification;

import com.tenten.damoa.hashtag.domain.Hashtag;
import com.tenten.damoa.post.domain.Post;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {
    public static Specification<Post> findByHashtag(String tag) {
        return (root, query, criteriaBuilder) -> {
            Join<Post, Hashtag> hashtags = root.join("hashtags");
            return criteriaBuilder.equal(hashtags.get("tag"), tag);
        };
    }
    public static Specification<Post> findByType(String type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type").as(String.class), type.toUpperCase());
    }
    public static Specification<Post> findByTitle(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + search + "%");
    }
    public static Specification<Post> findByContent(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("content"), "%" + search + "%");
    }
    public static Specification<Post> findByTitleOrContent(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(root.get("title"), "%" + search + "%"),
                criteriaBuilder.like(root.get("content"), "%" + search + "%")
        );
    }
}