create table message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payload varchar(500),
    message_type varchar(30) not null,
    send_tries int default 0,
    message_status varchar (15) not null,
    created_at datetime,
    email varchar(75) not null
)