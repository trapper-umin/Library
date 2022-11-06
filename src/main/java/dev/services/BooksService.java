package dev.services;

import dev.models.Book;
import dev.models.Person;
import dev.repositories.BooksRepository;
import net.bytebuddy.description.annotation.AnnotationValue;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final EntityManager entityManager;

    @Autowired
    public BooksService(BooksRepository booksRepository, EntityManager entityManager){
        this.booksRepository=booksRepository;
        this.entityManager = entityManager;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAllWithPagination(int page,int booksPerPage){

        return booksRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }

    public List<Book> findAllWithSort(boolean sort){
        return booksRepository.findAll(Sort.by("year"));
    }

    public List<Book> findAllWithPaginationAndSort(int page,int booksPerPage,boolean sort){
        return booksRepository.findAll(PageRequest.of(page,booksPerPage, Sort.by("year"))).getContent();
    }

    public List<Book> search(String search){
        return booksRepository.findBookByNameStartingWith(search);
    }

    public Book findById(int id){
        return booksRepository.findById(id).orElse(null);
    }

    public Person whoOwner(int id){
        Session session=entityManager.unwrap(Session.class);

        Book book=session.get(Book.class,id);
        Person owner=book.getOwner();

        return owner;
    }

    @Transactional
    public void select(int id,Book book){
        Session session=entityManager.unwrap(Session.class);

        Person ownerBook=session.get(Person.class,book.getOwnerId());

        book.setId(id);
        book.setOwner(ownerBook);
        book.setName(session.get(Book.class,id).getName());
        book.setAuthor(session.get(Book.class,id).getAuthor());
        book.setYear(session.get(Book.class,id).getYear());
        book.setCreatedAt(session.get(Book.class,id).getCreatedAt());
        booksRepository.save(book);
    }

    @Transactional
    public void absolve(int id){
        Session session=entityManager.unwrap(Session.class);

        Book book=session.get(Book.class,id);
        book.setOwner(null);
    }

    @Transactional
    public void create(Book book){
        book.setCreatedAt(new Date());
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id,Book book){
        book.setId(id);
        book.setCreatedAt(booksRepository.findById(id).get().getCreatedAt());
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
}
