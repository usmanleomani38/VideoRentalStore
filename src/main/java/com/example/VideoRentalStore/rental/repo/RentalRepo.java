package com.example.VideoRentalStore.rental.repo;

import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.rental.model.RentalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RentalRepo extends JpaRepository<Rental, Long> {



    @Query("SELECT r FROM Rental r WHERE r.movie.barcode = :movieId AND r.user.userId = :userId")
    List<Rental> findByMovieIdAndUserId(@Param("movieId")
                                        Long movieId,
                                        @Param("userId")
                                        Long userId);

    @Query("SELECT r FROM Rental r WHERE r.status = ?1")
    Page<Rental> findRentalByStatus(RentalStatus status, Pageable pageable);

    @Query("SELECT r FROM Rental r WHERE r.movie.barcode = :movieId AND r.user.userId = :userId AND r.status = :status")
    Optional<Rental> findByMovieIdAndUserIdAndStatus(
                                                @Param("movieId")
                                                Long barcode,
                                                @Param("userId")
                                                Long userId,
                                                @Param("status")
                                                RentalStatus status);

    @Query("SELECT COUNT(r) > 0 FROM Rental r WHERE r.movie.barcode = ?1 AND r.user.userId = ?2 AND r.status = ?3")
    boolean existsByMovieIdAndUserIdAndStatus(Long barcode,
                                              Long userId,
                                              RentalStatus status);

    @Query("SELECT r FROM Rental r WHERE r.user.userId = ?1 AND r.status = ?2")
    Page<Rental> findByUserIdAndStatus(
                                        Long userId,
                                        RentalStatus status,
                                        Pageable pageable);

    @Query("SELECT r FROM Rental r WHERE  r.user.userId = ?1")
    Page<Rental> findByUserId(Long userId, Pageable pageable);
}
