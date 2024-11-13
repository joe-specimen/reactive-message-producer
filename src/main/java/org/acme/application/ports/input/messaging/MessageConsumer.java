package org.acme.application.ports.input.messaging;

public interface MessageConsumer<T> {

   void consume(T payload);
}
