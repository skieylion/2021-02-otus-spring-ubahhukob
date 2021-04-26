package spring.homework.repositories;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;

@AllArgsConstructor
public class CommentDaoCustomImpl implements CommentDaoCustom{

    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteByBookId(String id) {
        Book book=new Book();
        book.setId(id);
        Query query=new Query(Criteria.where("book").is(book));
        mongoTemplate.findAllAndRemove(query, Comment.class);
    }
}
