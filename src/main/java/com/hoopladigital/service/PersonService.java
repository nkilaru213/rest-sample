package com.hoopladigital.service;

import com.hoopladigital.bean.Person;
import com.hoopladigital.mapper.PersonMapper;

import javax.inject.Inject;
import java.util.List;

public class PersonService
{

    private final PersonMapper personMapper;

    @Inject
    public PersonService(final PersonMapper personMapper)
    {
        this.personMapper = personMapper;
    }

    public List<Person> getPersonList()
    {
        return personMapper.getPersonList();
    }

    public Person getPersonById(final Long id)
    {
        return this.personMapper.getPersonById(id);
    }

    public Person addPerson(final Person person)
    {
        this.personMapper.addPerson(person);
        return person;
    }

    public Person updatePerson(final Person person)
    {
        this.personMapper.updatePerson(person);
        return person;
    }

    public void deletePerson(final Long id)
    {
        this.personMapper.deletePerson(id);
    }
}
