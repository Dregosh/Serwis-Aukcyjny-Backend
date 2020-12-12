CREATE TABLE auction (
    id BIGINT PRIMARY KEY,
    version BIGINT,
    title varchar(255),
    description text,
    min_price decimal,
    buy_now_price decimal,
    is_promoted boolean default 0,
    location varchar(500),
    start_date_time datetime,
    end_date_time datetime,
    status varchar(15)
);
