package com.example.moviesinfoservice.usecases.driven;

import com.example.moviesinfoservice.application.H2R2DbcTestConfiguration;
import com.example.moviesinfoservice.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest( classes = { H2R2DbcTestConfiguration.class, MovieInfoRepository.class })
class MovieInfoRepositoryTest {
   @Autowired private MovieInfoRepository repository;
   private MovieInfo avatar1;
   private MovieInfo avatar2;

   @BeforeEach public void beforeEachTest() {
      creatAndSaveTestData();
   }

   @AfterEach public void afterEachTest() {
      repository.deleteAll();
   }

   @Test public void findAll() {

      repository.findAll()
            .as( StepVerifier::create)
            .expectNextCount( 2 )
//            .expectNext( avatar1 )
//            .expectNext( avatar2 )
            .verifyComplete();
   }

   @Test public void findById() {
      repository.findByName( "Avatar" ).subscribe( movieInfo -> {
         avatar1 = movieInfo;

         var movieInfoMono = repository.findById( avatar1.getId() );
         StepVerifier.create( movieInfoMono )
               .assertNext( movie -> assertEquals( "Avatar", movie.getName() ) )
               .verifyComplete();
      });
   }

   @Test public void update() {
      repository.findByName( "Avatar" ).subscribe( movieInfo -> {
         movieInfo.setYear( 2023 );

         var movieInfoMono = repository.save( movieInfo ).log();
         StepVerifier.create( movieInfoMono )
               .assertNext( movie -> assertEquals( 2023, movie.getYear() ))
               .verifyComplete();
      });
   }

   private void creatAndSaveTestData() {
      avatar1 = MovieInfo.builder()
            .name( "Avatar" )
            .year( 2009 )
            .releaseDate( LocalDate.of( 2009, 12, 18 ) )
            .build();
      avatar2 = MovieInfo.builder()
            .name( "Avatar 2" )
            .year( 2022 )
            .releaseDate( LocalDate.of( 2022, 12, 14 ) )
            .build();

      saveTestData( avatar1, avatar2 );
   }
   private void saveTestData( MovieInfo... movieInfos ) {
      repository.saveAll( Arrays.asList( movieInfos ) )
            .as( StepVerifier::create )
            .expectNextCount( 2 )
            .verifyComplete();
   }
}