--liquibase formatted sql

--changeset s.nochvay:2
CREATE UNIQUE INDEX idx_user_username_lower ON users (LOWER(username));

--changeset s.nochvay:3
CREATE INDEX idx_news_user_id ON news (user_id);
CREATE INDEX idx_news_category_id ON news (category_id);
CREATE INDEX idx_comments_news_id ON comments (news_id);

CREATE INDEX idx_news_create_at ON news (create_at);