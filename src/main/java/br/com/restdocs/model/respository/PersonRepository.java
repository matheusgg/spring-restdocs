package br.com.restdocs.model.respository;

import br.com.restdocs.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PersonRepository extends SimpleJpaRepository<Person, Long> {

	private final EntityManager em;

	@Autowired
	public PersonRepository(final EntityManager em) {
		super(Person.class, em);
		this.em = em;
	}

	public Person update(final Person person) {
		return this.em.merge(person);
	}

}
