package dev.services;

import dev.models.Person;
import dev.repositories.PeopleRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository){
        this.peopleRepository=peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findById(int id){
        return peopleRepository.findById(id).orElse(null);
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
