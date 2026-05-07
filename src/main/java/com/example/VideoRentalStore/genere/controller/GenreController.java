package com.example.VideoRentalStore.genere.controller;

import com.example.VideoRentalStore.exceptionhandler.ApiResponse;
import com.example.VideoRentalStore.exceptionhandler.Status;
import com.example.VideoRentalStore.genere.dtos.GenreDTO;
import com.example.VideoRentalStore.genere.dtos.GenresDTO;
import com.example.VideoRentalStore.genere.service.GenreService;
import com.example.VideoRentalStore.genere.dtos.MoviesCountDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/add-genre")
    public ResponseEntity<ApiResponse<GenreDTO>> addGenre(@Valid @RequestBody GenreDTO genreDTO) {

        ApiResponse<GenreDTO> response = ApiResponse.<GenreDTO>builder()
                .status(Status.CREATED)
                .message("Genre Added Successfully")
                .data(genreService.addGenre(genreDTO))
                .build();
        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/get-genre/{genreId}")
    public ResponseEntity<ApiResponse<GenreDTO>> getGenreById(@PathVariable Long genreId) {

        ApiResponse<GenreDTO> response = ApiResponse.<GenreDTO>builder()
                .status(Status.OK)
                .message("Genre Fetched Successfully")
                .data(genreService.getGenreById(genreId))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-genre/{genreId}")
    public ResponseEntity<ApiResponse<String>> deleteGenreById(@PathVariable Long genreId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.OK)
                .message("Genre Deleted Successfully")
                .data(genreService.deleteByGenreId(genreId))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-genre/{genreId}")
    public ResponseEntity<ApiResponse<GenreDTO>> updateGenreById(@PathVariable Long genreId, @RequestBody GenreDTO genreDTO) {
        ApiResponse<GenreDTO> response = ApiResponse.<GenreDTO>builder()
                .status(Status.OK)
                .message("Genre Deleted Successfully")
                .data(genreService.updateGenreById(genreId, genreDTO))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-genre")
    public ResponseEntity<ApiResponse<GenresDTO>>getAllGenres() {

        ApiResponse<GenresDTO> response = ApiResponse.<GenresDTO>builder()
                .status(Status.OK)
                .message("Genres fetched Successfully")
                .data(genreService.getAllGenres())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-movies")
    public ResponseEntity<ApiResponse<MoviesCountDTO>>countMovieByGenre() {

        ApiResponse<MoviesCountDTO> response = ApiResponse.<MoviesCountDTO>builder()
                .status(Status.OK)
                .message("Records fetched Successfully")
                .data(genreService.countMovieByGenre())
                .build();
        return ResponseEntity.ok(response);
    }



}
