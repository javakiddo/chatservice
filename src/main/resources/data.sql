insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (1,'Steve', 'JOBS', 'steve', 'steve', 'steve.jobs@apple.com', '0033 1 23 45 67 89', 'en', true);
insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (2,'Bill', 'GATES', 'bill', 'bill', 'bill.gates@microsoft.com', '0033 1 23 45 67 89', 'fr', true);
insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (3,'Mark', 'ZUCKERBERG', 'mark', 'zuckerberg', 'mark.zuckerberg@facebook.com', '0033 1 23 45 67 89', 'en', true);
insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (4,'Tim', 'COOK', 'tim', 'cook', 'tim.cook@apple.com', '0033 1 23 45 67 89', 'en', true);
insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (5,'Larry', 'Page', 'larry', 'page', 'larry.page@gmail.com', '0033 1 23 45 67 89', 'en', true);
insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (6,'Sergey', 'Brin', 'sergey', 'brin', 'sergey.brin@gmail.com', '0033 1 23 45 67 89', 'en', true);
insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (7,'Larry', 'ELLISON', 'larry2', 'ellison', 'larry.ellison@oracle.com', '0033 1 23 45 67 89', 'en', true);
insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (8,'Jeff', 'BEZOS', 'jeff', 'bezos', 'jeff.bezos@amazon.com', '0033 1 23 45 67 89', 'en', true);
insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (9,'Paul', 'ALLEN', 'paul', 'allen', 'paul.allen@microsoft.com', '0033 1 23 45 67 89', 'en', true);
insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (10,'Steve', 'BALLMER', 'steve2', 'ballmer', 'steve.ballmer@microsoft.com', '0033 1 23 45 67 89', 'en', true);
insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (11,'Jack', 'DORSEY', 'jack', 'dorsey', 'jack.dorsey@twitter.com', '0033 1 23 45 67 89', 'en', true);
insert into users (id,first_name, family_name, login, password, e_mail, phone, language, enabled) values (12,'Matt', 'MULLENWEG', 'matt', 'mullenweg', 'matt.mullenweg@wordpress.com', '0033 1 23 45 67 89', 'en', true);

insert into authority (id,name) values (1,'admin');
insert into authority (id,name) values (2,'technical user');
insert into authority (id,name) values (3,'user');

insert into users_authority (id_user, id_authority) values (1, 1);
insert into users_authority (id_user, id_authority) values (1, 2);
insert into users_authority (id_user, id_authority) values (1, 3);
insert into users_authority (id_user, id_authority) values (2, 3);
insert into users_authority (id_user, id_authority) values (3, 3);
	
