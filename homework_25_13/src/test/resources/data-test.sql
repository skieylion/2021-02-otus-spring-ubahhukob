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


insert into GENRE (ID,NAME) values (1, 'Novel');
insert into GENRE (ID,NAME) values (2, 'Poem');

insert into AUTHOR (ID,FULLNAME,ALIAS) values (1, 'Alexey Maximovich Peshkov','Maxim Gorkiy');
insert into AUTHOR (ID,FULLNAME) values (2, 'Alexandr Sergeevich Pushkin');

insert into BOOK (ID,NAME,AUTHOR_ID,GENRE_ID) values (1, 'Mother',1,1);
insert into BOOK (ID,NAME,AUTHOR_ID,GENRE_ID) values (2, 'Ruslan and Ludmila',2,2);
insert into BOOK (ID,NAME,AUTHOR_ID,GENRE_ID) values (3, 'Evgeniy Onegin',1,2);

insert into COMMENT (ID,DESCRIPTION,BOOK_ID) values (1, 'The book is interesting',1);
insert into COMMENT (ID,DESCRIPTION,BOOK_ID) values (2, 'The book is terrible',1);
insert into COMMENT (ID,DESCRIPTION,BOOK_ID) values (3, 'So-so',2);