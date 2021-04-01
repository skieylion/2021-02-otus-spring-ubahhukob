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