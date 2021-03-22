package spring.homework.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;


@Repository
public class BookDaoImpl extends DefaultDaoImpl implements BookDao {

    public BookDaoImpl(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        super(jdbc, namedParameterJdbcOperations);
    }

    @Override
    public long create(Book book) {
        MapSqlParameterSource params=new MapSqlParameterSource();
        params.addValue("name",book.getName());
        params.addValue("author_id",book.getAuthorId()!=0?book.getAuthorId():null);
        params.addValue("genre_id",book.getGenreId()!=0?book.getGenreId():null);
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
        return book;
    }

    @Override
    public void update(Book book) {
        Map<String, Object> params = Map.of(
                "id",book.getId(),
                "name",book.getName(),
                "author_id",book.getAuthorId(),
                "genre_id",book.getGenreId()
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
    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long authorId=resultSet.getLong("author_id");
            long genreId=resultSet.getLong("genre_id");

            return new Book(id, name,authorId,genreId);
        }
    }
}
