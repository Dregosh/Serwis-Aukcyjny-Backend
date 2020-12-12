create table verification_code (
    id BIGINT primary key,
    code varchar (12) not null,
    created_at datetime,
    user_id bigint,

    foreign key (user_id) references user(id)
)