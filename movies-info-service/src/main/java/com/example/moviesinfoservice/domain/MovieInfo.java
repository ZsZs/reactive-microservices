package com.example.moviesinfoservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "movie_info" )
public class MovieInfo implements Persistable<Long> {
   @Id private Long id;
   @Column("movie_name") @NotBlank(message = "Name must be present")
   private String name;
   @Column("release_year") @NotNull @Positive( message = "Year must be a positive value.")
   private Integer year;
   private List<@NotBlank( message = "Cast must be present.") String> cast;
   @Column("release_date") @JsonFormat(pattern="yyyy-MM-dd") private LocalDate releaseDate;

   public MovieInfo withId( Long id ) {
      return MovieInfo.builder()
            .id( id )
            .name( name )
            .year( year )
            .releaseDate( releaseDate )
            .build();
   }

   @Override public boolean isNew() {
      return id == null;
   }
}
