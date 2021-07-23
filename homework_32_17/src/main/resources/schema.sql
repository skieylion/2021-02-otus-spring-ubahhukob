CREATE TABLE IF NOT EXISTS GENRE(
    ID BIGINT PRIMARY KEY,
    NAME VARCHAR(127) NOT NULL
);

CREATE TABLE IF NOT EXISTS AUTHOR(
    ID BIGINT PRIMARY KEY,
    FULLNAME VARCHAR(511) NOT NULL,
    ALIAS VARCHAR(127)
);

CREATE TABLE IF NOT EXISTS BOOK(
    ID BIGINT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    AUTHOR_ID BIGINT REFERENCES AUTHOR(ID),
    GENRE_ID BIGINT REFERENCES GENRE(ID)
);

CREATE TABLE IF NOT EXISTS COMMENT(
    ID BIGINT PRIMARY KEY,
    DESCRIPTION VARCHAR(8191),
    BOOK_ID BIGINT REFERENCES BOOK(ID)
);


DELETE FROM COMMENT;
DELETE FROM BOOK;
DELETE FROM AUTHOR;
DELETE FROM GENRE;