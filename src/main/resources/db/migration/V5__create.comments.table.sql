CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    created TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    message varchar(500) NOT NULL,
    post_id INT NOT NULL REFERENCES posts (id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_comments_post_id_created ON comments (post_id, created);
