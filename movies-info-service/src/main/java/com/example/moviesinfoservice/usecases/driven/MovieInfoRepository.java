package com.example.moviesinfoservice.usecases.driven;

import com.example.moviesinfoservice.domain.MovieInfo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MovieInfoRepository extends ReactiveCrudRepository<MovieInfo, Long> {
   @Query("SELECT * FROM movie_info WHERE movie_name = :name")
   Mono<MovieInfo> findByName( String name );
}
