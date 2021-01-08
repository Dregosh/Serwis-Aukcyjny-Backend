create table premium_order (
    id BIGINT PRIMARY KEY auto_increment,
    order_id VARCHAR(50) not null unique,
    user_id BIGINT not null,
    order_date DATETIME not null,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

alter table user ADD COLUMN premium_account_expiration DATETIME;