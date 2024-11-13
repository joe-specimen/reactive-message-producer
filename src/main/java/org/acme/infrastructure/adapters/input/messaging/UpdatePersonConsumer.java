package org.acme.infrastructure.adapters.input.messaging;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.java.Log;
import org.acme.infrastructure.adapters.output.persistence.Person;
import org.acme.application.ports.input.messaging.MessageConsumer;
import org.acme.application.services.UpdatePerson;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@Log
public class UpdatePersonConsumer implements MessageConsumer<Person>  {

   @Inject
   UpdatePerson updatePerson;

   @Incoming("update-person")
   @RunOnVirtualThread
   @Override
   public void consume(Person person) {
      log.info(() -> "[Start] Update Person: " + person);
      updatePerson.updatePerson(person.getId());
      try {
         Thread.sleep(5000);
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
      log.info(() -> "[End] Update Person: " + person);
   }
}
