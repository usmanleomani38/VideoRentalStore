package com.example.VideoRentalStore.genere.model;

import com.example.VideoRentalStore.audit.AuditFields;
import com.example.VideoRentalStore.movie.model.Movie;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Genre extends AuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;
    private String genreName;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movieList = new ArrayList<>();

}
