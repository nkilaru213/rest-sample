package com.hoopladigital.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hoopladigital.bean.Person;
import com.hoopladigital.service.PersonService;
import com.hoopladigital.service.PetService;

@Path("/people")
public class PersonResource
{

    private final PersonService personService;
    private final PetService petService;

    @Inject
    public PersonResource(final PersonService personService, final PetService petService)
    {
        this.personService = personService;
        this.petService = petService;
    }

    @GET
    @Produces("application/json")
    public List<Person> getPersonList()
    {
        return personService.getPersonList();
    }

    @GET
    @Path("/getPersonById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonById(@PathParam("id") Long id)
    {
        if (id == null)
        {
            return Response.serverError().entity("Invalid person id").build();
        }
        final Person persistentPerson = this.personService.getPersonById(id);
        if (null == persistentPerson)
        {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("There is no person associated with the given id:" + id).build();
        }
        return Response.status(Response.Status.OK).entity(persistentPerson).build();
    }

    @Path("/addPerson")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person)
    {
        // as the id is an auto generated value, not adding any validation.
        final Person persistentPerson = this.personService.addPerson(person);
        return Response.status(Response.Status.OK).entity(persistentPerson).build();
    }

    @Path("/updatePerson")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(final Person person)
    {
        Person persistentPerson = this.personService.getPersonById(person.getId());
        if (null == persistentPerson)
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Person id is invalid.").build();
        }
        persistentPerson = this.personService.updatePerson(person);
        return Response.status(Response.Status.OK).entity(persistentPerson).build();
    }

    @DELETE
    @Path("/deletePerson/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("id") Long id)
    {
        Person persistentPerson = this.personService.getPersonById(id);
        if (null == persistentPerson)
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Person id is invalid.").build();
        }
        this.petService.deletePetsByPerson(id);
        this.personService.deletePerson(id);
        return Response.status(Response.Status.OK).entity("Successfully deleted the person with id:" + id).build();
    }
}
