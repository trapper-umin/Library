package dev.services;

import dev.models.Book;
import dev.models.Person;
import dev.repositories.PeopleRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final EntityManager entityManager;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, EntityManager entityManager){
        this.peopleRepository=peopleRepository;
        this.entityManager = entityManager;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findById(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    public List<Book> personHaveBooks(int id){
        Session session=entityManager.unwrap(Session.class);

        Person person=session.load(Person.class,id);
        List<Book> books=person.getBooks();

        return books;
    }

    @Transactional
    public void create(Person person){
        person.setRegisteredAt(new Date());
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id,Person person){
        person.setId(id);
        person.setRegisteredAt(peopleRepository.findById(id).get().getRegisteredAt());
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
}
