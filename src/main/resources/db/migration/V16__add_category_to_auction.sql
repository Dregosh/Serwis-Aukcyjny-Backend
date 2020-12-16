alter table auction add column category_id BIGINT;

alter table auction add foreign key (category_id) references category(id);