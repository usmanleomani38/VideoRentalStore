package com.example.VideoRentalStore.movie.repo;

import com.example.VideoRentalStore.movie.model.Movie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE LOWER(m.movieName) LIKE LOWER(CONCAT('%', :movieName, '%'))")
    Optional<Movie> findByMovieNameContainingIgnoreCase(String movieName);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE LOWER(g.genreName) = LOWER(:genreName)")
    List<Movie> findMoviesByGenreName(@Param("genreName") String genreName);

    @Query("SELECT m FROM Movie m WHERE LOWER(m.movieName) = LOWER(?1)")
    Optional<Movie> findByMovieNameIgnoreCase(String movieName);


    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.discountedRate = m.dailyRentalRate - (m.dailyRentalRate * :discount)")
    void applyDiscount(@Param("discount") Double discount);


    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.discountedRate = NULL")
    void removeDiscount();

    @Query("SELECT SUM(m.availableQuantity) FROM Movie m")
    Long getTotalAvailableStock();
}


