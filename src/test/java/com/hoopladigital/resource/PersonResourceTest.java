package com.hoopladigital.resource;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.hoopladigital.bean.Person;
import com.hoopladigital.service.PersonService;
import com.hoopladigital.service.PetService;
import com.hoopladigital.test.AbstractTest;

public class PersonResourceTest extends AbstractTest
{
    @Mock
    private PersonService personService;
    @Mock
    private PetService petService;

    private PersonResource personResource;

    @Before
    public void beforePersonResourceTest()
    {
        personResource = new PersonResource(personService, petService);
    }

    @Test
    public void should_get_person_list()
    {
        // setup
        final List<Person> expected = Collections.emptyList();
        when(personService.getPersonList()).thenReturn(expected);

        // run test
        final List<Person> actual = personResource.getPersonList();
        // verify mocks / capture values
        verify(personService).getPersonList();
        verifyNoMoreInteractions(allDeclaredMocks(this));

        // assert results
        assertEquals(expected, actual);

    }
   
    
}
