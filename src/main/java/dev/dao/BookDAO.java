package dev.dao;

import dev.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Book> show(){
        return jdbcTemplate.query("SELECT book.book_id,book.name,book.author,book.year FROM book",new BeanPropertyRowMapper<>(Book.class));
    }

    public Book index(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new Object[]{id},new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void create(Book book){
        jdbcTemplate.update("INSERT INTO book(name,author,year) VALUES (?,?,?)",book.getName(),book.getAuthor(),book.getYear());
    }

    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE book SET name=?,author=?,year=? WHERE book_id=?",book.getName(),book.getAuthor(),book.getYear(),id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?",id);
    }
}
