/*
 * Created on Mar 17, 2019
 * $Id: EmpowerMX_CodeTemplates_Galileo.xml,v 1.2 2009/07/06 20:21:52 matt.accola Exp $
 *
 * Copyright (c) 2006 by EmpowerMX. All Rights Reserved.
 */
package com.hoopladigital.mapper;

import java.util.List;

import com.hoopladigital.bean.Pet;

/**
 * //TODO add class description
 * 
 * @author Naveen
 * @version $Revision: 1.2 $ $Date: 2009/07/06 20:21:52 $
 */
public interface PetMapper
{
    List<Pet> getPetsByPersonId(final Long personId);
    Pet getPetById(final Long id);
    void addPet(final Pet pet);
    void updatePet(final Pet pet);
    void deletePet(final Long id);
    void deletePetsByPerson(final Long personId);
}
