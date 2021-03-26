package spring.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Repository
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookDaoImpl(NamedParameterJdbcOperations namedParameterJdbcOperations, AuthorDao authorDao, GenreDao genreDao) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public long create(Book book) {

        Author author=book.getAuthor();
        long authorId=authorDao.create(author);
        Genre genre=book.getGenre();
        long genreId=genreDao.create(genre);

        MapSqlParameterSource params=new MapSqlParameterSource();
        params.addValue("name",book.getName());
        params.addValue("author_id",authorId);
        params.addValue("genre_id",genreId);

        KeyHolder kh=new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into BOOKS(name,author_id,genre_id) values(:name,:author_id,:genre_id)",params,kh);

        return kh.getKey().longValue();
    }

    @Override
    public Book read(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Book book=namedParameterJdbcOperations.queryForObject(
                "select id,name,author_id,genre_id from BOOKS where id = :id", params,new BookMapper()
        );
        book.setAuthor(authorDao.read(book.getAuthor().getId()));
        book.setGenre(genreDao.read(book.getGenre().getId()));

        return book;
    }

    @Override
    public void update(Book book) {
        authorDao.update(book.getAuthor());
        genreDao.update(book.getGenre());

        Map<String, Object> params = Map.of(
                "id",book.getId(),
                "name",book.getName(),
                "author_id",book.getAuthor().getId(),
                "genre_id",book.getGenre().getId()
        );

        namedParameterJdbcOperations.update(
                "update BOOKS set name=:name,author_id=:author_id, genre_id=:genre_id where id = :id", params
        );
    }

    @Override
    public void delete(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from BOOKS where id = :id", params
        );
    }

    @Override
    public List<Book> readAll() {
        List<Book> bookList=namedParameterJdbcOperations.getJdbcOperations().queryForObject(
                "select b.id as id,b.name as name,a.id as author_id,a.fullname,a.alias,g.id as genre_id,g.name as genre_name from BOOKS b left join AUTHORS a on b.author_id=a.id left join GENRES g on g.id=b.genre_id",
                new BookMapperList()
        );
        return bookList;
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long authorId=resultSet.getLong("author_id");
            long genreId=resultSet.getLong("genre_id");
            Author author=new Author(authorId);
            Genre genre=new Genre(genreId);

            return new Book(id, name,author,genre);
        }
    }
    private static class BookMapperList implements RowMapper<List<Book>> {
        @Override
        public List<Book> mapRow(ResultSet resultSet, int i) throws SQLException {
            List<Book> bookList=new ArrayList<>();
            while(resultSet.next()){
                long bookId=resultSet.getLong("id");
                String bookName=resultSet.getString("name");
                long authorId=resultSet.getLong("author_id");
                String authorFullName=resultSet.getString("fullname");
                String authorAlias=resultSet.getString("alias");
                long genreId=resultSet.getLong("genre_id");
                String genreName=resultSet.getString("genre_name");

                Author author=new Author(authorId,authorFullName,authorAlias);
                Genre genre=new Genre(genreId,genreName);
                Book book=new Book(bookId,bookName,author,genre);

                bookList.add(book);
            }
            return bookList;
        }
    }
}
