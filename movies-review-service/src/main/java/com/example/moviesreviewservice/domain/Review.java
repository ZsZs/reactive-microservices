package com.example.moviesreviewservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review implements Persistable {
   @Id private Long id;
   @Column("movie_info_id") private Long movieInfoId;
   @Column("review_comment") private String comment;
   private Double rating;

   public Review withId( Long id ) {
      return Review.builder()
            .id( id )
            .movieInfoId( movieInfoId )
            .comment( comment )
            .rating( rating )
            .build();
   }

   @Override public boolean isNew() {
      return id == null;
   }
}
