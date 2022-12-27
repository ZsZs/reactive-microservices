package com.example.moviesinfoservice.adapters.driving;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest( controllers = FluxAndMonoEndpoint.class )
@AutoConfigureWebTestClient
class FluxAndMonoEndpointTest {
   @Autowired private WebTestClient webTestClient;

   @Test void flux() {
      var responseBody = webTestClient
            .get()
            .uri( "/flux" )
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
//            .expectBodyList( Integer.class )
//            .hasSize( 3 )
            .returnResult(Integer.class)
            .getResponseBody();

      StepVerifier.create( responseBody )
            .expectNext( 1,2,3 )
            .verifyComplete();
   }

   @Test void mono() {
      webTestClient
            .get()
            .uri( "/mono" )
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody( String.class )
            .consumeWith( exchangeResult -> {
               var responseBody = exchangeResult.getResponseBody();
               assertEquals( "Hello World", responseBody );
            });

   }

   @Test void stream() {
      var responseBody = webTestClient
            .get()
            .uri( "/stream" )
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .returnResult(Long.class)
            .getResponseBody();

      StepVerifier.create( responseBody )
            .expectNext( 0L, 1L, 2L, 3L )
            .thenCancel()
            .verify();
   }
}