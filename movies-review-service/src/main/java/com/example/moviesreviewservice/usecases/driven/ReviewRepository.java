package com.example.moviesreviewservice.usecases.driven;

import com.example.moviesreviewservice.domain.Review;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends ReactiveCrudRepository<Review, Long> {
}
