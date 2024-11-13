package org.acme.infrastructure.adapters.input.messaging;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.acme.infrastructure.adapters.output.persistence.Person;
import org.acme.application.ports.input.messaging.MessageConsumer;
import org.acme.application.services.CreatePerson;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@Log
public class CreatePersonConsumer implements MessageConsumer<String> {

   @Inject
   CreatePerson createPerson;

   @Inject
   @Channel("update-person")
   Emitter<Person> emitter;

   @Incoming("create-person")
   @RunOnVirtualThread
   @Override
   public void consume(String personName) {
      log.info(() -> "[Start] Create Person: " + personName);
      Person person = createPerson.createPerson(personName);
      emitter.send(person);
      log.info(() -> "[End] Create Person: " + personName);
   }
}
