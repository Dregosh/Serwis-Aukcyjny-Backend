create table audit (
    id BIGINT PRIMARY KEY auto_increment,
    command_name varchar (150) not null,
    command_content varchar(500) not null,
    result VARCHAR(15) not null,
    created_at DATETIME not null,
    duration BIGINT not null
)