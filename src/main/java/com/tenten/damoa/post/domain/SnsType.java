package com.tenten.damoa.post.domain;

public enum SnsType {
    FACEBOOK("https://www.facebook.com/likes/"),
    TWITTER("https://www.twitter.com/likes/"),
    INSTAGRAM("https://www.instagram.com/likes/"),
    THREADS("https://www.threads.net/likes/");


    private final String url;

    SnsType(String url) {
        this.url = url;
    }

    public String getUrl(String contentId) {
        return url + contentId;
    }
}