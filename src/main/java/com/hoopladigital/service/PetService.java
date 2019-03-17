package com.hoopladigital.service;

import java.util.List;

import javax.inject.Inject;

import com.hoopladigital.bean.Pet;
import com.hoopladigital.mapper.PetMapper;

public class PetService
{
    private final PetMapper petMapper;

    @Inject
    public PetService(final PetMapper petMapper)
    {
        this.petMapper = petMapper;
    }

    public List<Pet> getPetsByPersonId(final Long personId)
    {
        return this.petMapper.getPetsByPersonId(personId);
    }

    public Pet getPetById(final Long id)
    {
        return this.petMapper.getPetById(id);
    }
    
    public Pet addPet(final Pet pet)
    {
        this.petMapper.addPet(pet);
        return pet;
    }
    
    public Pet updatePet(final Pet pet)
    {
        this.petMapper.updatePet(pet);
        return pet;
    }
    
    public void deletePet(final Long id)
    {
        this.petMapper.deletePet(id);
    }
    
    public void deletePetsByPerson(final Long personId)
    {
        this.petMapper.deletePetsByPerson(personId);
    }
}
