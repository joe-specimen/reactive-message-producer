package org.acme.application.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import org.acme.infrastructure.adapters.output.persistence.Person;
import org.acme.infrastructure.adapters.output.persistence.PersonRepository;

@ApplicationScoped
public class CreatePerson {

   @Inject
   PersonRepository personRepository;

   @Transactional(TxType.REQUIRES_NEW)
   public Person createPerson(String personName) {
      Person person = new Person();
      person.setAge(10);
      person.setName(personName);
      personRepository.persist(person);

      return person;
   }

}
