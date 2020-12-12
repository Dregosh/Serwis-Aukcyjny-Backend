create table user (
    id BIGINT PRIMARY KEY,
    email VARCHAR(75) not null unique,
    display_name VARCHAR(255) not null unique,
    address VARCHAR(500) not null,
    created_at DATETIME not null,
    account_status VARCHAR(15) not null,
    account_type VARCHAR(15) not null,
    version BIGINT not null default 0,
    promoted_auctions_count int default 0
)