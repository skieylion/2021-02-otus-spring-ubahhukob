package spring.homework.services;

import org.springframework.stereotype.Service;
import spring.homework.domain.Book;

import java.util.List;

@Service
public class ServiceStringBookImpl  implements ServiceStringBook {
    @Override
    public String convert(Book book) {
        return "Book{" +
                "id=" + book.getId() +
                ", name='" + book.getName() + '\'' +
                ", author=" + book.getAuthor().getFullName() +
                ", genre=" + book.getGenre().getName() +
                '}';
    }

    @Override
    public String convert(List<Book> book) {
        StringBuilder str=new StringBuilder();
        book.forEach(b->{
            str.append(convert(b));
            str.append("\n");
        });
        return str.toString();
    }
}
