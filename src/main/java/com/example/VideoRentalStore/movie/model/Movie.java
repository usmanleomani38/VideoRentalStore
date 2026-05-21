package com.example.VideoRentalStore.movie.model;

import com.example.VideoRentalStore.audit.AuditFields;
import com.example.VideoRentalStore.genere.model.Genre;
import com.example.VideoRentalStore.rental.model.Rental;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie extends AuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long barcode;
    private String movieName;
    @Column(nullable = true)
    private Long duration;
    @Column(nullable = true)
    private Integer releaseYear;
    private Integer availableQuantity;
    private Double dailyRentalRate;
    private Double discountedRate;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

}
