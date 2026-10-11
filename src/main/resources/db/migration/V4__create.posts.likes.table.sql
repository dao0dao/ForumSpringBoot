CREATE TABLE posts_likes (
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY (post_id, user_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (post_id) REFERENCES posts (id)
);
