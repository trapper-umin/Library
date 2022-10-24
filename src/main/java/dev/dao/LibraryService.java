package dev.dao;

import dev.models.Book;
import dev.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LibraryService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LibraryService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Book> personHaveBooks(int id){
        return jdbcTemplate.query("SELECT * FROM person JOIN book ON person.person_id=book.person_id WHERE book.person_id=?",new Object[]{id},new BeanPropertyRowMapper<>(Book.class));
    }

    public boolean thisBookHaveOwner(int id){
        List<Book> books=jdbcTemplate.query("SELECT * FROM person JOIN book ON person.person_id=book.person_id where book_id=?",new Object[]{id},new BeanPropertyRowMapper<>(Book.class));
        if (books.isEmpty()){
            return false;
        }else return true;
    }

    public Person whoOwner(int id){
        return jdbcTemplate.query("SELECT person.name,person.birth FROM person JOIN book ON person.person_id=book.person_id where book_id=?"
                        ,new Object[]{id},new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void select(int id, Book book){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?",book.getOwner(),id);
    }
}
