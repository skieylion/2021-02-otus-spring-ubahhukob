package spring.homework.components;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import spring.homework.dao.AuthorDao;
import spring.homework.dao.BookDao;
import spring.homework.dao.GenreDao;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Genre;

import java.util.List;


@ShellComponent
public class ComponentCommand {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;


    public ComponentCommand(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @ShellMethod(value = "read command",key={"read book"})
    public String readBook(@ShellOption long bookId){
        return bookDao.read(bookId).toString();
    }

    @ShellMethod(value = "read command",key={"read book all"})
    public String readAll(){
        List<Book> bookList=bookDao.readAll();
        return bookList.toString();
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
    public String createBook(@ShellOption String bookName,@ShellOption String authorName, @ShellOption String genreName){
        Author author=new Author(authorName,"");
        Genre genre=new Genre(genreName);
        author.setId(authorDao.create(author));
        genre.setId(genreDao.create(genre));
        Book book=new Book(bookName,author,genre);
        long id=bookDao.create(book);
        return bookDao.read(id).toString();
    }
}
