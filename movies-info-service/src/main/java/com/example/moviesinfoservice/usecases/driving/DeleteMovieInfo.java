package com.example.moviesinfoservice.usecases.driving;

import com.example.moviesinfoservice.usecases.driven.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteMovieInfo {
   private final MovieInfoRepository repository;

   public DeleteMovieInfo( MovieInfoRepository repository ) {
      this.repository = repository;
   }

   public Mono<Void> delete( Long id ) {
      return repository.deleteById( id );
   }
}
