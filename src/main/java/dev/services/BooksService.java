package dev.services;

import dev.models.Book;
import dev.models.Person;
import dev.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository){
        this.booksRepository=booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public Book findById(int id){
        return booksRepository.findById(id).orElse(null);
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
