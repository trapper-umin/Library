package dev.dao;

import dev.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Person> show(){
        return jdbcTemplate.query("SELECT * FROM person",new BeanPropertyRowMapper<>(Person.class));
    }

    public Person index(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?",new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void create(Person person){
        jdbcTemplate.update("INSERT INTO person(name,birth) VALUES (?,?)",person.getName(),person.getDateOfBirth());
    }

    public void update(int id,Person person){
        jdbcTemplate.update("UPDATE person SET name=?, birth=? WHERE person_id=?",person.getName(),person.getDateOfBirth(),id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?",id);
    }

}
