package br.com.restdocs.controller;

import br.com.restdocs.model.Person;
import br.com.restdocs.model.respository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "people", produces = APPLICATION_JSON_VALUE)
public class PersonController {

	private final PersonRepository repository;

	@Autowired
	public PersonController(final PersonRepository repository) {
		this.repository = repository;
	}

	@RequestMapping(method = GET)
	public List<Person> findAll() {
		return this.repository.findAll();
	}

	@RequestMapping(method = GET, value = "{id}")
	public Person findOne(@PathVariable final Long id) {
		return this.repository.findOne(id);
	}

	@RequestMapping(method = POST)
	@ResponseStatus(CREATED)
	public Person save(@RequestBody final Person person) {
		return this.repository.save(person);
	}

	@RequestMapping(method = PUT)
	public Person update(@RequestBody final Person person) {
		return this.repository.update(person);
	}

	@RequestMapping(method = DELETE, value = "{id}")
	@ResponseStatus(OK)
	public void delete(@PathVariable final Long id) {
		this.repository.delete(id);
	}
}
