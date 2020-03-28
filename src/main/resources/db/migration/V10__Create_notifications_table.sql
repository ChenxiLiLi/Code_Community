create table Notifications
(
	id bigint auto_increment,
	notifier bigint not null,
	NOTIFIER_NAME varchar(50) not null,
	receiver bigint not null,
	outerId bigint not null,
	OUTER_TITLE varchar(50),
	type int not null,
	gmt_create bigint not null,
	constraint Notifications_pk
		primary key (id)
);