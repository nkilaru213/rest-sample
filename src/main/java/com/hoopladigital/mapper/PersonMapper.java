package com.hoopladigital.mapper;

import com.hoopladigital.bean.Person;

import java.util.List;

public interface PersonMapper {
	List<Person> getPersonList();
	Person getPersonById(final Long id);
	void addPerson(final Person person);
	void updatePerson(final Person person);
	void deletePerson(final Long id);
}
