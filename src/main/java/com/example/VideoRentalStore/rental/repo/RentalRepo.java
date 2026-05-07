package com.example.VideoRentalStore.rental.repo;

import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.rental.model.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RentalRepo extends JpaRepository<Rental, Long> {

    @Query("SELECT r FROM Rental r WHERE r.movie.barcode =:movieId")
    Optional<Rental> findByMovieId(@Param("movieId")Long movieId);

    @Query("SELECT r FROM Rental r WHERE r.status = ?1")
    List<Rental> findRentalByStatus(RentalStatus status);
}
