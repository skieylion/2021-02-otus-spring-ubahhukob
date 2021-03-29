insert into COMMENTS (ID,DESCRIPTION) values (1, 'Какая прекрасная книга');
insert into COMMENTS (ID,DESCRIPTION) values (2, 'Эта книга просто ужасна');
insert into COMMENTS (ID,DESCRIPTION) values (3, 'И так сойдет');
insert into COMMENTS (ID,DESCRIPTION) values (4, 'Не знаю');


insert into GENRES (ID,NAME) values (1, 'Роман');
insert into GENRES (ID,NAME) values (2, 'Поэма');
insert into GENRES (ID,NAME) values (3, 'Фантастика');
insert into GENRES (ID,NAME) values (4, 'Классика');

insert into AUTHORS (ID,FULLNAME,ALIAS) values (1, 'Алексей Максимович Пешков','Максим Горький');
insert into AUTHORS (ID,FULLNAME) values (2, 'Александр Сергеевич Пушкин');
insert into AUTHORS (ID,FULLNAME) values (3, 'Александр Сергеевич Пушкин');

insert into BOOKS (ID,NAME,AUTHOR_ID,GENRE_ID,COMMENT_ID) values (1, 'Мать',1,1,1);
insert into BOOKS (ID,NAME,AUTHOR_ID,GENRE_ID,COMMENT_ID) values (2, 'Ruslan and Ludmila',2,2,1);
insert into BOOKS (ID,NAME,AUTHOR_ID,GENRE_ID,COMMENT_ID) values (3, 'Евгений Онегин',1,2,2);