/*
 * Created on Mar 17, 2019
 * $Id: EmpowerMX_CodeTemplates_Galileo.xml,v 1.2 2009/07/06 20:21:52 matt.accola Exp $
 *
 * Copyright (c) 2006 by EmpowerMX. All Rights Reserved.
 */
package com.hoopladigital.bean;

import java.io.Serializable;

public class Pet implements Serializable
{
    private Long id;
    private String name;
    private Person person;

    public Pet()
    {
        super();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }
}
