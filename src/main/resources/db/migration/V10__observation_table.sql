CREATE TABLE observation
(
    id         BIGINT PRIMARY KEY auto_increment,
    auction_id BIGINT,
    user_id    BIGINT,
    FOREIGN KEY (auction_id) REFERENCES auction (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
)
