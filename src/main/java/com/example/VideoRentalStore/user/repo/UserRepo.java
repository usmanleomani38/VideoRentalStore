package com.example.VideoRentalStore.user.repo;

import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.rental.model.RentalStatus;
import com.example.VideoRentalStore.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

   @Query("SELECT u from User u WHERE u.contactNo = :contactNo")
   Optional<User> findByContactNo(@Param("contactNo") Long contactNo);

    @Query("SELECT u FROM User u WHERE u.userName = ?1")
    Optional<User> findByUserName(String userName);


    @Query("SELECT u FROM User u WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :userName, '%'))")
    List<User> findByUserNameStartsWithIgnoreCase(@Param("userName") String userName);

    @Query("SELECT r FROM Rental r WHERE r.user.userId = ?1 AND r.status IN (?2, ?3)")
    List<Rental> findByUserIdAndRentalStatus(Long userId, RentalStatus rentalStatus1, RentalStatus rentalStatus2);

    @Query("SELECT r FROM Rental r WHERE r.user.userId = ?1 AND r.status = ?2")
    List<Rental> findByUserIdAndStatus(Long userId, RentalStatus rentalStatus);

}
