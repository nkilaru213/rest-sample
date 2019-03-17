package com.hoopladigital.mapper;

import javax.inject.Inject;

import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoopladigital.bean.Person;
import com.hoopladigital.bean.Pet;
import com.hoopladigital.test.AbstractMapperTest;

public class PetMapperTest extends AbstractMapperTest
{
    private static final Logger logger = LoggerFactory.getLogger(PetMapperTest.class);

    @Inject
    private PetMapper petMapper;

    @Test
    public void should_add_update_deletePet() throws Exception
    {
        // create pet
        final Person person = new Person();
        person.setId(1L);
        final Pet dog = new Pet();
        dog.setId(1L);
        dog.setName("puppy");
        dog.setPerson(person);
        petMapper.addPet(dog);
        logger.debug("Pet was added.");
        Pet persistentPet = this.petMapper.getPetById(dog.getId());
        logger.debug("The persistent pet id:" + persistentPet.getId());
        assertEquals(new Long(1), persistentPet.getId());

        // update pet

        dog.setName(dog.getName() + "_Updated");
        petMapper.updatePet(dog);
        persistentPet = this.petMapper.getPetById(dog.getId());
        assertEquals("puppy_Updated", persistentPet.getName());

        // delete pet
        petMapper.deletePet(dog.getId());
        persistentPet = this.petMapper.getPetById(dog.getId());
        assertNull(persistentPet);
        // test completed
    }
}
