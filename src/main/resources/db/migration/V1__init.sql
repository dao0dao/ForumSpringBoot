CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    likes INTEGER NOT NULL DEFAULT 0,
    UNIQUE(title)
);

INSERT INTO posts(title, content, created, likes) VALUES ('First Post', 'This is first content', current_timestamp, 1);