package com.example.VideoRentalStore.rental.model;

import com.example.VideoRentalStore.audit.AuditFields;
import com.example.VideoRentalStore.movie.model.Movie;
import com.example.VideoRentalStore.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rental extends AuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RentalStatus status;
    Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
