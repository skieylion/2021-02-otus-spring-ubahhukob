package spring.homework.components;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import spring.homework.domain.Author;
import spring.homework.domain.Book;
import spring.homework.domain.Comment;
import spring.homework.domain.Genre;
import spring.homework.repositories.AuthorDao;
import spring.homework.repositories.BookDao;
import spring.homework.repositories.GenreDao;

import java.util.ArrayList;
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
        bookDao.save(book);
        return bookDao.read(bookId).toString();
    }

    @ShellMethod(value = "delete command",key={"delete book"})
    public String deleteBook(@ShellOption long bookId){
        bookDao.delete(bookId);
        return "deleted the book";
    }

    @ShellMethod(value = "create command",key={"create book"})
    public String createBook(@ShellOption String bookName,@ShellOption String authorName, @ShellOption String genreName,@ShellOption String commentValue){
        Author author=new Author(authorName,"");
        Genre genre=new Genre(genreName);
        Comment comment=new Comment(commentValue);
        author.setId(authorDao.save(author));
        genre.setId(genreDao.save(genre));
        List<Comment> commentList=new ArrayList<>();
        commentList.add(comment);
        Book book=new Book(bookName,author,genre,commentList);
        long id=bookDao.save(book);
        return bookDao.read(id).toString();
    }
}
