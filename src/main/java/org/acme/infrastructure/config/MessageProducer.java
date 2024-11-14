package org.acme.infrastructure.config;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.java.Log;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
@Log
public class MessageProducer {

   @Inject
   @Channel("create-person")
   Emitter<String> emitter;

   private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
       1,
       Thread.ofVirtual().factory()
   );

   void onStart(@Observes StartupEvent ev) {
      AtomicLong counter = new AtomicLong();

      scheduler.scheduleAtFixedRate(
          () -> sendMessage(counter),
          0,
          1,
          TimeUnit.SECONDS
      );
   }

   void onStop(@Observes ShutdownEvent ev) {
      scheduler.shutdown();
   }

   void sendMessage(AtomicLong counter) {
      String payload = "message-%d".formatted(counter.addAndGet(1));

      log.info("Sending message");
      emitter
           .send("message-%d".formatted(counter.addAndGet(1)))
           .whenComplete(this::handle);
      log.info("Message sent");
   }

   void handle(Void unused, Throwable e) {
      if (e != null) {
         log.severe(() -> "Error happened: " + e.getMessage());
      }
   }

}
