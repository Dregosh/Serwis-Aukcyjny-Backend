ALTER TABLE auction
    ADD seller_id BIGINT,
    ADD FOREIGN KEY (seller_id) REFERENCES user(id)
