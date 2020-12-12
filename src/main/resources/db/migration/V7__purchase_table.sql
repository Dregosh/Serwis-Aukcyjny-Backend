CREATE TABLE purchase
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    auction_id BIGINT,
    buyer_id BIGINT,
    price decimal,
    FOREIGN KEY (auction_id) REFERENCES auction(id),
    FOREIGN KEY (buyer_id) REFERENCES user(id)
)
