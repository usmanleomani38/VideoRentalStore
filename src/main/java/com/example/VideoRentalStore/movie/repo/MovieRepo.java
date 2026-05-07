package com.example.VideoRentalStore.movie.repo;

import com.example.VideoRentalStore.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie, Long> {
    Optional<Movie> findByMovieName(String movieName);

    @Query("SELECT m FROM Movie m WHERE LOWER(m.movieName) LIKE LOWER(CONCAT('%', :movieName, '%'))")
    Optional<Movie> findByMovieNameContainingIgnoreCase(String movieName);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE LOWER(g.genreName) = LOWER(:genreName)")
    List<Movie> findMoviesByGenreName(@Param("genreName") String genreName);
}


