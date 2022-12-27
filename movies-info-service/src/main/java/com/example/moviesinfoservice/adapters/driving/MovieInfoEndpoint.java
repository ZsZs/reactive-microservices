package com.example.moviesinfoservice.adapters.driving;

import com.example.moviesinfoservice.domain.MovieInfo;
import com.example.moviesinfoservice.usecases.driving.AddMovieInfo;
import com.example.moviesinfoservice.usecases.driving.DeleteMovieInfo;
import com.example.moviesinfoservice.usecases.driving.FindAllMovieInfos;
import com.example.moviesinfoservice.usecases.driving.UpdateMovieInfo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/movie-info")
public class MovieInfoEndpoint {
   private final AddMovieInfo addMovieInfo;
   private final FindAllMovieInfos findAllMovieInfos;
   private final UpdateMovieInfo updateMovieInfo;
   private final DeleteMovieInfo deleteMovieInfo;

   public MovieInfoEndpoint( AddMovieInfo addMovieInfo, FindAllMovieInfos findAllMovieInfos, UpdateMovieInfo updateMovieInfo, DeleteMovieInfo deleteMovieInfo ) {
      this.addMovieInfo = addMovieInfo;
      this.findAllMovieInfos = findAllMovieInfos;
      this.updateMovieInfo = updateMovieInfo;
      this.deleteMovieInfo = deleteMovieInfo;
   }

   @PostMapping @ResponseStatus( HttpStatus.CREATED )
   public Mono<MovieInfo> addMovieInfo( @RequestBody @Valid MovieInfo movieInfo ) {
      return addMovieInfo.add( movieInfo );
   }

   @PutMapping("/{id}")
   public Mono<ResponseEntity<MovieInfo>> updateMovieInfo( @RequestBody @Valid MovieInfo movieInfo, @PathVariable Long id) {
      return updateMovieInfo.update( movieInfo, id )
            .map( movie -> ResponseEntity.ok().body( movie ))
            .switchIfEmpty( Mono.just( ResponseEntity.notFound().build() ) );
   }

   @GetMapping @ResponseStatus( HttpStatus.OK )
   public Flux<MovieInfo> findAll() {
      return findAllMovieInfos.find();
   }

   @DeleteMapping("/{id}") @ResponseStatus( HttpStatus.NO_CONTENT )
   public Mono<Void> deleteMovieInfo( @PathVariable Long id ) {
      return deleteMovieInfo.delete( id );
   }
}
