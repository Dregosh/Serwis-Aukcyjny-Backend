CREATE TABLE rating (
    id BIGINT PRIMARY KEY auto_increment,
    purchase_id BIGINT,
    sellers_rating TINYINT,
    sellers_comment TEXT,
    sellers_rating_date DATETIME,
    buyers_rating TINYINT,
    buyers_comment TEXT,
    buyers_rating_date DATETIME,
    FOREIGN KEY (purchase_id) REFERENCES purchase(id)
)
