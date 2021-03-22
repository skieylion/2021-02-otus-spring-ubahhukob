package spring.homework.components;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Component;
import spring.homework.dao.BookDao;
import spring.homework.domain.Book;

import javax.annotation.PostConstruct;


@ShellComponent
public class ComponentCommand {
    private final BookDao bookDao;

    public ComponentCommand(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @ShellMethod(value = "read command",key={"read book"})
    public String readBook(@ShellOption long bookId){
        return bookDao.read(bookId).toString();
    }

    @ShellMethod(value = "update command",key={"update book"})
    public String updateBook(@ShellOption long bookId,@ShellOption String newName){
        Book book=bookDao.read(bookId);
        book.setName(newName);
        bookDao.update(book);
        return bookDao.read(bookId).toString();
    }

    @ShellMethod(value = "delete command",key={"delete book"})
    public String deleteBook(@ShellOption long bookId){
        bookDao.delete(bookId);
        return "deleted the book";
    }

    @ShellMethod(value = "create command",key={"create book"})
    public String createBook(@ShellOption String name){
        Book book=new Book(name);
        long id=bookDao.create(book);
        return bookDao.read(id).toString();
    }
}
