CREATE TABLE IF NOT EXISTS member
(
    id        VARCHAR(255) DEFAULT (UUID()) PRIMARY KEY,
    account   VARCHAR(50) UNIQUE NOT NULL,
    email     VARCHAR(100)       NOT NULL,
    password  VARCHAR(255)       NOT NULL,
    role      VARCHAR(50)        NOT NULL,
    joined_at TIMESTAMP(6)       NOT NULL
);

CREATE TABLE IF NOT EXISTS verification_code
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    code      VARCHAR(6)   NOT NULL,
    send_at   TIMESTAMP(6) NOT NULL,
    member_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_member_verification_code FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE IF NOT EXISTS post
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    content_id  VARCHAR(255) NOT NULL,
    type        VARCHAR(50)  NOT NULL,
    title       VARCHAR(255) NOT NULL,
    content     VARCHAR(255) NOT NULL,
    view_count  INT          NOT NULL,
    like_count  INT          NOT NULL,
    share_count INT          NOT NULL,
    created_at  TIMESTAMP(6) NOT NULL,
    updated_at  TIMESTAMP(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS hashtag
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag        VARCHAR(50)  NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    post_id    BIGINT       NOT NULL,
    CONSTRAINT fk_post_hashtag FOREIGN KEY (post_id) REFERENCES post (id)
);

CREATE TABLE IF NOT EXISTS interaction_history
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    category   VARCHAR(50)  NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    post_id    BIGINT       NOT NULL,
    CONSTRAINT fk_post_interaction_history FOREIGN KEY (post_id) REFERENCES post (id)
);