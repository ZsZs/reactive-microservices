package com.example.moviesreviewservice.adapters.driving;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReviewRouter {
   @Bean public RouterFunction<ServerResponse> reviewRoute( ReviewHandler reviewHandler ) {
      return route().nest( path("/v1/reviews" ), builder -> {
               builder.POST( "", reviewHandler::addReview )
                      .GET("", reviewHandler::findAll )
                      .PUT( "/{id}", reviewHandler::update );
            })
            .GET("/v1/reviews/helloworld", ( request -> ServerResponse.ok().bodyValue( "helloworld" )))
            .build();
   }
}
