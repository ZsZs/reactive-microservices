package com.example.moviesreviewservice.adapters.driving;

import com.example.moviesreviewservice.domain.Review;
import com.example.moviesreviewservice.usecases.driven.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class ReviewHandler {
   private final ReviewRepository repository;

   public ReviewHandler( ReviewRepository repository ) {
      this.repository = repository;
   }

   public Mono<ServerResponse> addReview( ServerRequest request ) {
      return request.bodyToMono( Review.class )
            .flatMap( repository::save )
            .flatMap( ServerResponse.status( HttpStatus.CREATED )::bodyValue);
   }

   public Mono<ServerResponse> findAll( ServerRequest request ) {
      var reviews = repository.findAll();
      return ServerResponse.ok().body( reviews, Review.class );
   }

   public Mono<ServerResponse> update( ServerRequest serverRequest ) {
      var id = serverRequest.pathVariable( "id" );
      return repository
            .findById( Long.valueOf( id ))
            .flatMap( existingReview -> serverRequest.bodyToMono( Review.class )
               .map( requestReview -> {
                  existingReview.setComment( requestReview.getComment() );
                  existingReview.setRating( requestReview.getRating() );
                  existingReview.setMovieInfoId( requestReview.getMovieInfoId() );
                  return existingReview;
               })
              .flatMap( repository::save )
              .flatMap( savedReview -> ServerResponse.ok().bodyValue( savedReview )));
   }
}
