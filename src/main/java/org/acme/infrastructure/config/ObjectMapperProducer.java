package org.acme.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ObjectMapperProducer {

   @Produces
   @ApplicationScoped
   public ObjectMapper createObjectMapper() {

      final ObjectMapper mapper = new ObjectMapper();

      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

      mapper.registerModule(new JavaTimeModule());

      return mapper;
   }

}
