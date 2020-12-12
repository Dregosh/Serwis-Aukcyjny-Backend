CREATE TABLE bid (
    id BIGINT PRIMARY KEY auto_increment,
    auction_id BIGINT,
    user_id BIGINT,
    bid_price DECIMAL,
    FOREIGN KEY (auction_id) REFERENCES auction(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
)
