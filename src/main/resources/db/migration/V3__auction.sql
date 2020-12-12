CREATE TABLE auction (
    id BIGINT PRIMARY KEY,
    title varchar(255),
    description text,
    min_price decimal,
    buy_now_price decimal,
    is_promoted boolean,
    location varchar(255),
    start_date_time datetime,
    end_date_time datetime,
    viewed_count int
);
