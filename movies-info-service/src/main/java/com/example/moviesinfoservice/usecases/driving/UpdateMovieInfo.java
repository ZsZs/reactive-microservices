package com.example.moviesinfoservice.usecases.driving;

import com.example.moviesinfoservice.domain.MovieInfo;
import com.example.moviesinfoservice.usecases.driven.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateMovieInfo {
   private final MovieInfoRepository repository;

   public UpdateMovieInfo( MovieInfoRepository repository ) {
      this.repository = repository;
   }

   public Mono<MovieInfo> update( MovieInfo movieInfoUpdate, Long id ) {
      return repository.findById( id ).flatMap( movieInfo -> {
         movieInfo.setName( movieInfoUpdate.getName() );
         movieInfo.setYear( movieInfoUpdate.getYear() );
         movieInfo.setReleaseDate( movieInfoUpdate.getReleaseDate() );
         return repository.save( movieInfo );
      });
   }
}
