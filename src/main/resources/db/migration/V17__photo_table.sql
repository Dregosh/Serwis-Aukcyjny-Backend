create table photo(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name varchar(255) not null,
    auction_id BIGINT,

    foreign key (auction_id) references auction(id)
)