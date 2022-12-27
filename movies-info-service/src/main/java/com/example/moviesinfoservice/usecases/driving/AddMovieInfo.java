package com.example.moviesinfoservice.usecases.driving;

import com.example.moviesinfoservice.domain.MovieInfo;
import com.example.moviesinfoservice.usecases.driven.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AddMovieInfo {
   private final MovieInfoRepository repository;

   public AddMovieInfo( MovieInfoRepository repository ) {
      this.repository = repository;
   }

   public Mono<MovieInfo> add( MovieInfo movieInfo ) {
      return repository.save( movieInfo ).log();
   }
}
