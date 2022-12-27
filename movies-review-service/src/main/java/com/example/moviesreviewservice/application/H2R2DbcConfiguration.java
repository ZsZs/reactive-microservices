package com.example.moviesreviewservice.application;

import com.example.moviesreviewservice.domain.Review;
import com.example.moviesreviewservice.usecases.driven.ReviewRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@Configuration
@EnableR2dbcRepositories( basePackageClasses = { ReviewRepository.class })
public class H2R2DbcConfiguration extends AbstractR2dbcConfiguration {
   @Override @Bean public ConnectionFactory connectionFactory() {
      var connectionFactoryBuilder = org.springframework.boot.r2dbc.ConnectionFactoryBuilder
            .withUrl( "r2dbc:h2:mem://./testdb;DB_CLOSE_DELAY=-1;" )
            .username( "sa" )
            .password( "" )
            .build();
      return connectionFactoryBuilder;
   }
   @Bean BeforeConvertCallback<Review> idGeneratingCallback( DatabaseClient databaseClient) {

      return (review, sqlIdentifier) -> {

         if (review.getId() == null) {
            return databaseClient.sql("SELECT NEXT VALUE FOR primary_key")
                  .map(row -> row.get(0, Long.class)) //
                  .first() //
                  .map(review::withId);
         }

         return Mono.just(review);
      };
   }
}
