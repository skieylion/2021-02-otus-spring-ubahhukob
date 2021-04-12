insert into AUTHORS (ID,FULLNAME,ALIAS) values (1, 'Алексей Максимович Пешков','Максим Горький');
insert into AUTHORS (ID,FULLNAME) values (2, 'Александр Сергеевич Пушкин');

insert into GENRES (ID,NAME) values (1, 'Роман');
insert into GENRES (ID,NAME) values (2, 'Поэма');
insert into GENRES (ID,NAME) values (3, 'Фантастика');
insert into GENRES (ID,NAME) values (4, 'Фентези');
insert into GENRES (ID,NAME) values (5, 'Сатира');

insert into BOOKS (ID,NAME,AUTHOR_ID,GENRE_ID) values (1, 'Мать',1,1);
insert into BOOKS (ID,NAME,AUTHOR_ID,GENRE_ID) values (2, 'Ruslan and Ludmila',2,2);
insert into BOOKS (ID,NAME,AUTHOR_ID,GENRE_ID) values (3, 'Евгений Онегин',1,2);
insert into BOOKS (ID,NAME,AUTHOR_ID,GENRE_ID) values (4, 'Гоголь',1,2);