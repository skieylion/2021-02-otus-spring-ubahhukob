package spring.homework.components;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import spring.homework.exceptions.BookException;
import spring.homework.services.ServiceBook;
import spring.homework.services.ServiceStringBook;

@ShellComponent
public class ComponentCommand {

    private final ServiceBook serviceBook;

    public ComponentCommand(ServiceBook serviceBook) {
        this.serviceBook = serviceBook;
    }

    @ShellMethod(value = "read command",key={"read book"})
    public String readBook(@ShellOption String bookId) throws BookException {
        return serviceBook.read(bookId);
    }

    @ShellMethod(value = "read command",key={"read book all"})
    public String readAll(){
        return serviceBook.readAll();
    }

    @ShellMethod(value = "update command",key={"update book"})
    public String updateBook(@ShellOption String bookId,@ShellOption String newName) throws BookException {
        return serviceBook.update(bookId,newName);
    }

    @ShellMethod(value = "delete command",key={"delete book"})
    public String deleteBook(@ShellOption String bookId) throws BookException {
        return serviceBook.delete(bookId);
    }

    @ShellMethod(value = "create command",key={"create book"})
    public String createBook(@ShellOption String bookName,@ShellOption String authorName, @ShellOption String genreName,@ShellOption String commentValue) throws BookException {
        return serviceBook.create(bookName,authorName,genreName,commentValue);
    }
}
