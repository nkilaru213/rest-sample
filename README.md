# rest-sample

This is a simple REST service to provide an API to a database of people and their pets.

The only endpoint currently implemented is: `GET /people`

	curl http://localhost:8080/people

Implement an appropriate API to allow a client application to:

- read a single person (by id)
- create an additional person
- update a person
- delete a person
- create, read, update, and delete pets associated with people (remember: person id is required)

Consistency is important - follow the patterns used by the existing endpoint, using the existing `PersonResource` 
resource class for the `/people` endpoints. For the pet-related endpoints, create a new resource class and again, follow 
the patterns as before.

When creating or updating a person:

- return the new person object as JSON
- if the id is invalid, return a status of 404
- the person object should be provided as JSON to the endpoint

When reading a person:

- if the id is valid, return a status of 200 and the content as JSON
- if the id is invalid, return a status of 404

When deleting a person:

- if the id is valid, return a status of 204
- if the id is invalid, return a status of 404

Unit tests are required - the expectation is 100% branch and line coverage - the project can be tested using:

	mvn clean jacoco:prepare-agent test site jxr:jxr -Duser.timezone=GMT

Integration tests are "extra-credit", but may be useful for you to use to verify behavior.
