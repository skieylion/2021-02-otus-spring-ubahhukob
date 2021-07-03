package spring.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.homework.domain.Book;
import spring.homework.exceptions.BookException;
import spring.homework.repositories.BookRepository;
import spring.homework.repositories.CommentRepository;

import static org.mockito.Mockito.when;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceBookImpl implements ServiceBook{

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final JdbcMutableAclService aclService;


    @Override
    @Transactional(readOnly = true)
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public Book read(long id) throws BookException {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    //@PostFilter("hasPermission(filterObject, 'READ')")
    public List<Book> readAll() {
        List<Book> bookList= bookRepository.findAll();
        return bookList;
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_EDITOR')")
    public void update(long bookId, String newName) throws BookException {
        Book book= bookRepository.findById(bookId);
        book.setName(newName);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_EDITOR')")
    public void delete(long bookId) {
	    commentRepository.deleteByBookId(bookId);
        if(bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            final ObjectIdentity objectIdentity=new ObjectIdentityImpl(Book.class,bookId);
            aclService.deleteAcl(objectIdentity,true);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_EDITOR')")
    public long create(Book book) throws BookException {
        book= bookRepository.save(book);
        commentRepository.saveAll(book.getComments());

        final ObjectIdentity objectIdentity=new ObjectIdentityImpl(Book.class,book.getId());
        final MutableAcl mutableAcl=aclService.createAcl(objectIdentity);
        final Sid editor = new GrantedAuthoritySid("ROLE_EDITOR");
        final Sid user = new GrantedAuthoritySid("ROLE_USER");

        mutableAcl.setOwner(editor);
        mutableAcl.insertAce(mutableAcl.getEntries().size(), BasePermission.WRITE,editor,true);
        mutableAcl.insertAce(mutableAcl.getEntries().size(), BasePermission.READ,editor,true);
        mutableAcl.insertAce(mutableAcl.getEntries().size(), BasePermission.READ,user,true);

        aclService.updateAcl(mutableAcl);

        return book.getId();
    }

}
