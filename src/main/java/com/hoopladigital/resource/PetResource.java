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
import com.hoopladigital.bean.Pet;
import com.hoopladigital.service.PersonService;
import com.hoopladigital.service.PetService;

@Path("/pet")
public class PetResource
{
    private final PetService petService;
    private final PersonService personSerice;

    @Inject
    public PetResource(final PetService petService, final PersonService personService)
    {
        this.petService = petService;
        this.personSerice = personService;
    }

    @GET
    @Produces("application/json")
    @Path("/{personId}")
    public List<Pet> getPersonsListByPerson(@PathParam("personId") Long personId)
    {
        return this.petService.getPetsByPersonId(personId);
    }

    @GET
    @Path("/getPetById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPetById(@PathParam("id") Long id)
    {
        if (id == null)
        {
            return Response.serverError().entity("Invalid pet id").build();
        }
        final Pet persistentPet = this.petService.getPetById(id);
        if (null == persistentPet)
        {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("There is no pet associated with the given id:" + id).build();
        }
        return Response.status(Response.Status.OK).entity(persistentPet).build();
    }

    @Path("/addPet")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPet(Pet pet)
    {
        // as the id is an auto generated value, not adding any validation.
        final Person persistentPerson = this.personSerice.getPersonById(pet.getPerson().getId());
        if (null == persistentPerson)
        {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("There is no person with the given id:" + pet.getPerson().getId()).build();
        }
        final Pet persistentPet = this.petService.addPet(pet);
        return Response.status(Response.Status.OK).entity(persistentPet).build();
    }

    @Path("/updatePet")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePet(final Pet pet)
    {
        Pet persistentPet = this.petService.getPetById(pet.getId());
        if (null == persistentPet)
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Pet id is invalid.").build();
        }
        persistentPet = this.petService.updatePet(pet);
        return Response.status(Response.Status.OK).entity(persistentPet).build();
    }

    @DELETE
    @Path("/deletePet/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePet(@PathParam("id") Long id)
    {
        Pet persistentPet = this.petService.getPetById(id);
        if (null == persistentPet)
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Pet id is invalid.").build();
        }
        this.petService.deletePet(id);
        return Response.status(Response.Status.OK).entity("Successfully deleted the person with id:" + id).build();
    }

    @DELETE
    @Path("/deletePetsByPerson/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePetsByPerson(@PathParam("id") Long id)
    {
        Person persistentPersion = this.personSerice.getPersonById(id);
        if (null == persistentPersion)
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Person id is invalid.").build();
        }
        this.petService.deletePetsByPerson(id);
        return Response.status(Response.Status.OK).entity("Successfully deleted the pets with the persion id:" + id)
                .build();
    }
}
