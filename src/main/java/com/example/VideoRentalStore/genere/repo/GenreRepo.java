package com.example.VideoRentalStore.genere.repo;

import com.example.VideoRentalStore.genere.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GenreRepo extends JpaRepository<Genre, Long> {

    @Query("Select g from Genre g WHERE g.genreName = ?1")
    Optional<Genre> findByGenreName(String genreName);

}
