INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'admin'),
(2, 1, 'user'),
(3, 0, 'ROLE_EDITOR');

INSERT INTO acl_class (id, class) VALUES
(1, 'spring.homework.domain.Book');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 3, 0),
(2, 1, 2, NULL, 3, 0),
(3, 1, 3, NULL, 3, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 2, 1, 2, 1, 1, 1),
(3, 1, 3, 3, 1, 1, 1, 1),
(4, 2, 1, 2, 1, 1, 1, 1),
(5, 2, 2, 3, 1, 1, 1, 1),
(6, 3, 1, 3, 1, 1, 1, 1),
(7, 3, 2, 3, 2, 1, 1, 1);


insert into GENRE (ID,NAME) values (1, 'Роман');
insert into GENRE (ID,NAME) values (2, 'Поэма');

insert into AUTHOR (ID,FULLNAME,ALIAS) values (1, 'Алексей Максимович Пешков','Максим Горький');
insert into AUTHOR (ID,FULLNAME) values (2, 'Александр Сергеевич Пушкин');

insert into BOOK (ID,NAME,AUTHOR_ID,GENRE_ID) values (1, 'Мать',1,1);
insert into BOOK (ID,NAME,AUTHOR_ID,GENRE_ID) values (2, 'Ruslan and Ludmila',2,2);
insert into BOOK (ID,NAME,AUTHOR_ID,GENRE_ID) values (3, 'Евгений Онегин',1,2);

insert into COMMENT (ID,DESCRIPTION,BOOK_ID) values (1, 'Какая прекрасная книга',1);
insert into COMMENT (ID,DESCRIPTION,BOOK_ID) values (2, 'Эта книга просто ужасна',1);
insert into COMMENT (ID,DESCRIPTION,BOOK_ID) values (3, 'И так сойдет',2);

insert into ROLE (ID,NAME) values (1, 'ADMIN');
insert into ROLE (ID,NAME) values (2, 'USER');

insert into USER (ID,LOGIN,NAME,PASSWORD,ROLE_ID) values (1,'admin', 'Админов Админ Админович','$2y$04$J9LlbdNpKxmJ6csxybkYye5YQKtgiAjxpSD.3S8s2nVhTE8rkSe7W',1);
insert into USER (ID,LOGIN,NAME,PASSWORD,ROLE_ID) values (2,'user', 'Юзеров Юзер Юзерович','$2y$04$RB8m2o04Cdq.U3tRkWcste.UsxDFWv3gyxJ5Z0.4h9UGyxc1mYTmO',2);