package com.example.moviesinfoservice.usecases.driving;

import com.example.moviesinfoservice.domain.MovieInfo;
import com.example.moviesinfoservice.usecases.driven.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class FindAllMovieInfos {
   private final MovieInfoRepository repository;

   public FindAllMovieInfos( MovieInfoRepository repository ) {
      this.repository = repository;
   }

   public Flux<MovieInfo> find() {
      return repository.findAll();
   }
}
