package com.example.VideoRentalStore.genere.service;

import com.example.VideoRentalStore.apputils.CommonUtils;
import com.example.VideoRentalStore.exceptionhandler.customexceptions.ResourceNotFoundException;
import com.example.VideoRentalStore.genere.dtos.GenreDTO;
import com.example.VideoRentalStore.genere.dtos.GenreWithMoviesListDTO;
import com.example.VideoRentalStore.genere.dtos.GenresDTO;
import com.example.VideoRentalStore.genere.model.Genre;
import com.example.VideoRentalStore.genere.repo.GenreRepo;
import com.example.VideoRentalStore.genere.dtos.MoviesCountDTO;
import com.example.VideoRentalStore.movie.model.Movie;
import com.example.VideoRentalStore.movie.repo.MovieRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.WordUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepo genreRepo;
    private final MovieRepo movieRepo;

    public GenreDTO addGenre(GenreDTO genreDTO) {

        genreRepo.findByGenreName(genreDTO.getGenreName())
                .ifPresent(g-> {
                    throw new RuntimeException(
                            "Genre already exists"
                    );
                });

        Genre genre = new Genre();
        genre.setGenreName(WordUtils.capitalize(genreDTO.getGenreName()));
        return GenreDTO.toDTO(genreRepo.save(genre));
    }

    @Transactional
    public void deleteByGenreId(Long genreId) {

       Genre genre = genreRepo.findById(genreId)
               .orElseThrow(()-> new ResourceNotFoundException(
                       "Genre not found"
               ));

        for(Movie movie : genre.getMovieList())
            movie.getGenres().remove(genre);
        genre.getMovieList().clear();
        genreRepo.deleteById(genreId);
    }

    public GenreDTO updateGenreById(Long genreId, GenreDTO genreDTO) {

        Genre genre = genreRepo.findById(genreId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Genre not found!"
                ));

        genreRepo.findByGenreName(genreDTO.getGenreName())
                .ifPresent(g-> {
                    if(!genre.getGenreId().equals(genreId))
                        throw new RuntimeException(
                                "Genre already exists"
                        );
                });

        genre.setGenreName(WordUtils.capitalize(genreDTO.getGenreName()));
        return GenreDTO.toDTO(genreRepo.save(genre));
    }

    public GenreDTO getGenreById(Long genreId) {

        Genre genre = genreRepo.findById(genreId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Genre not found!"
                ));
        return GenreDTO.toDTO(genre);

    }

    public GenresDTO getAllGenres(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,CommonUtils.buildSort(sortBy, sortOrder));
        Page<Genre> page = genreRepo.findAll(pageRequest);
        var genres = page.getContent();
        var totalPages = page.getTotalPages();
        var totalElements = page.getTotalElements();

        if (genres.isEmpty())
            return GenresDTO.builder()
                    .genres(Collections.emptyList())
                    .build();

        return GenresDTO.toDTO(genres,pageNumber,pageSize, totalPages, totalElements);
    }

    public MoviesCountDTO countMovieByGenre() {

        List<Genre> genres = genreRepo.findAll();
        if(genres.isEmpty())
           return MoviesCountDTO.builder()
                    .genres(Collections.emptyList())
                    .build();

        return MoviesCountDTO.toDTO(genres);
    }

    public GenreWithMoviesListDTO getMoviesPerGenre() {

        List<Genre> genres = genreRepo.findAll();
        if(genres.isEmpty())
            return GenreWithMoviesListDTO.builder()
                    .genres(Collections.emptyList())
                    .build();

        return GenreWithMoviesListDTO.toDTO(genres);

    }

    public Map<String, Object> getGenresCount() {

//        Map<String, Object> data = new HashMap<>();
//        data.put("totalGenres", genreRepo.count());

//        return new HashMap<String, Object>() {{
//            put("totalGenres", genreRepo.count());
//        }};

//        return Map.ofEntries(Map.entry("totalGenres", genreRepo.count()));

//        return Collections.singletonMap("totalGenres", genreRepo.count());

//        return MapBuilder
//                .of("totalGenres", genreRepo.count())
//                .build();

//        return ImmutableMap.of("totalGenres", genreRepo.count());

        return Map.of("totalGenres", genreRepo.count());
    }
}


