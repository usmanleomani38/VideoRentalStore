package com.example.VideoRentalStore.rental.repo;

import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.rental.model.RentalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentalRepo extends JpaRepository<Rental, Long>, JpaSpecificationExecutor<Rental> {



    @Query("SELECT r FROM Rental r WHERE r.status = ?1")
    Page<Rental> findRentalByStatus(RentalStatus status, Pageable pageable);


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



    @Query("""
        SELECT SUM(r.totalAmount) FROM Rental r
        WHERE (:movieName IS NULL OR r.movie.movieName LIKE %:movieName%)
        AND (:status IS NULL OR r.status = :status)
        AND (:startDate IS NULL OR r.returnDate >= :startDate)
        AND (:endDate IS NULL OR r.returnDate <= :endDate)
        """)
    Double getTotalAmountWithFilters(
            @Param("movieName") String movieName,
            @Param("status") RentalStatus status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);


    @Query("SELECT COUNT(r) FROM Rental r WHERE r.status = :status")
    Long findRentalsCountByStatus(@Param("status") RentalStatus rentalStatus);
}
