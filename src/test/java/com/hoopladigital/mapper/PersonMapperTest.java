package com.hoopladigital.mapper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoopladigital.bean.Person;
import com.hoopladigital.test.AbstractMapperTest;

public class PersonMapperTest extends AbstractMapperTest
{
    private static final Logger logger = LoggerFactory.getLogger(PersonMapperTest.class);

    @Inject
    private PersonMapper personMapper;

    @Test
    public void should_get_person_list() throws Exception
    {
        // setup
        final Person george = new Person();
        george.setId(1L);
        george.setFirstName("George");
        george.setLastName("Washington");

        // run test
        final List<Person> personList = personMapper.getPersonList();

        // verify mocks / capture values

        // assert results
        assertEquals(10, personList.size());
        beanTestHelper.diffBeans(george, personList.get(0));
    }

    @Test
    public void should_add_update_deletePerson()
    {
        // create person
        final Person naveen = new Person();
        naveen.setFirstName("Naveen");
        naveen.setMiddleName("Kumar");
        naveen.setLastName("Kilaru");
        naveen.setId(11L);
        personMapper.addPerson(naveen);
        logger.debug("Person was added.");
        Person persistentPerson = this.personMapper.getPersonById(naveen.getId());
        assertEquals(new Long(11), persistentPerson.getId());
        logger.debug("The persistent person id:" + persistentPerson.getId());
        // retrieve all the person instances
        List<Person> personList = personMapper.getPersonList();
        assertEquals(11, personList.size());
        
        // update first name
        naveen.setFirstName(naveen.getFirstName() + "_Updated");
        personMapper.updatePerson(naveen);
        persistentPerson = this.personMapper.getPersonById(naveen.getId());
        assertEquals("Naveen_Updated", persistentPerson.getFirstName());
        //delete the newly created person
        personMapper.deletePerson(naveen.getId());
        personList = personMapper.getPersonList();
        assertEquals(10, personList.size());
        // test completed
    }
}
