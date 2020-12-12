create table verification_code (
    id BIGINT primary key auto_increment,
    code varchar(50) not null,
    created_at datetime,
    user_id bigint,

    foreign key (user_id) references user(id)
)