package spring.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Book;
import spring.homework.exceptions.BookException;
import spring.homework.repositories.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceBookImpl implements ServiceBook{

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public Book read(long id) throws BookException {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> readAll() {
        List<Book> bookList= bookRepository.findAll();
        return bookList;
    }

    @Override
    @Transactional
    public void update(long bookId, String newName) throws BookException {
        Book book= bookRepository.findById(bookId);
        book.setName(newName);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(long bookId) {
	    commentRepository.deleteByBookId(bookId);
        if(bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
        }
    }

    @Override
    @Transactional
    public long create(Book book) throws BookException {

        book.getComments().forEach(comment -> {
            comment.setId((int) (Math.random() * 99999999));
        });

        book= bookRepository.save(book);
        commentRepository.saveAll(book.getComments());

        return book.getId();
    }

}
