package com.example.moviesinfoservice.application;

import com.example.moviesinfoservice.domain.MovieInfo;
import com.example.moviesinfoservice.usecases.driven.MovieInfoRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@Configuration
@EnableR2dbcRepositories( basePackageClasses = { MovieInfoRepository.class })
public class H2R2DbcConfiguration extends AbstractR2dbcConfiguration {
   @Override @Bean public ConnectionFactory connectionFactory() {
      var connectionFactoryBuilder = org.springframework.boot.r2dbc.ConnectionFactoryBuilder
            .withUrl( "r2dbc:h2:mem://./testdb;DB_CLOSE_DELAY=-1;" )
            .username( "sa" )
            .password( "" )
            .build();
      return connectionFactoryBuilder;
   }
   @Bean BeforeConvertCallback<MovieInfo> idGeneratingCallback( DatabaseClient databaseClient) {

      return (movieInfo, sqlIdentifier) -> {

         if (movieInfo.getId() == null) {
            return databaseClient.sql("SELECT NEXT VALUE FOR primary_key")
                  .map(row -> row.get(0, Long.class)) //
                  .first() //
                  .map(movieInfo::withId);
         }

         return Mono.just(movieInfo);
      };
   }
}
