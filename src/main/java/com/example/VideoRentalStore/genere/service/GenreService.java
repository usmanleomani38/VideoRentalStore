package com.example.VideoRentalStore.genere.service;

import com.example.VideoRentalStore.apputils.CommonUtils;
import com.example.VideoRentalStore.exceptionhandler.customexceptions.ResourceNotFoundException;
import com.example.VideoRentalStore.genere.dtos.GenreDTO;
import com.example.VideoRentalStore.genere.dtos.GenresDTO;
import com.example.VideoRentalStore.genere.model.Genre;
import com.example.VideoRentalStore.genere.repo.GenreRepo;
import com.example.VideoRentalStore.genere.dtos.MoviesCountDTO;
import com.example.VideoRentalStore.movie.model.Movie;
import com.example.VideoRentalStore.movie.repo.MovieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepo genreRepo;
    private final MovieRepo movieRepo;

    public GenreDTO addGenre(GenreDTO genreDTO) {

        genreRepo.findByGenreName(genreDTO.getGenreName())
                .ifPresent(g->
                { throw new RuntimeException("Genre already exists"); });
        Genre newGenre = new Genre();
        newGenre.setGenreName(genreDTO.getGenreName());
        return GenreDTO.toDTO(genreRepo.save(newGenre));
    }

    public String deleteByGenreId(Long genreId) {

       Genre genre = genreRepo.findById(genreId)
               .orElseThrow(()-> new ResourceNotFoundException("Genre not found"));

        for(Movie movie : genre.getMovieList()) {
            movie.getGenres().remove(movie);
            movieRepo.save(movie);
        }
        genreRepo.deleteById(genreId);
        return "Genre deleted successfully";
    }

    public GenreDTO updateGenreById(Long genreId, GenreDTO genreDTO) {

        Genre genre = genreRepo.findById(genreId)
                .orElseThrow(()-> new ResourceNotFoundException("Genre not found!"));
        genre.setGenreName(genreDTO.getGenreName());
        return GenreDTO.toDTO(genreRepo.save(genre));
    }

    public GenreDTO getGenreById(Long genreId) {

        Genre genre = genreRepo.findById(genreId)
                .orElseThrow(()-> new ResourceNotFoundException("Genre not found!"));
        return GenreDTO.toDTO(genre);

    }

    public GenresDTO getAllGenres(String sortBy, String sortOrder) {

        List<Genre> genres = genreRepo.findAll(CommonUtils.buildSort(sortBy, sortOrder));
        if (genres.isEmpty())
            return GenresDTO.builder()
                    .genres(Collections.emptyList())
                    .build();
        else
            return GenresDTO.toDTO(new ArrayList<>(genres));
    }

    public MoviesCountDTO countMovieByGenre() {

        List<Genre> genres = genreRepo.findAll();
        if(genres.isEmpty())
           return MoviesCountDTO.builder()
                    .genres(Collections.emptyList())
                    .build();
        else
          return MoviesCountDTO.toDTO(new ArrayList<>(genres));
    }
}
