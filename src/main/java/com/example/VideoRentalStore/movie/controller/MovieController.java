package com.example.VideoRentalStore.movie.controller;

import com.example.VideoRentalStore.apputils.AppConstants;
import com.example.VideoRentalStore.exceptionhandler.ApiResponse;
import com.example.VideoRentalStore.exceptionhandler.Status;
import com.example.VideoRentalStore.movie.dtos.*;
import com.example.VideoRentalStore.movie.service.MovieService;
import com.example.VideoRentalStore.rental.dto.response.RentalResponseDTO;
import com.example.VideoRentalStore.movie.dtos.ReturnMovieResponseDTO;
import com.example.VideoRentalStore.user.dtos.OnCreate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity<ApiResponse<MovieDTO>> addMovie(
                                                        @Validated(OnCreate.class)
                                                        @RequestBody
                                                        MovieDTO movieDTO) {

        ApiResponse<MovieDTO> response = ApiResponse.<MovieDTO>builder()
                .status(Status.CREATED)
                .message("Movie added successfully")
                .data(movieService.addMovie(movieDTO))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-movie/{id}")
    public ResponseEntity<ApiResponse<MovieDTO>> getMovieById(
                                                        @PathVariable
                                                        Long id) {

        ApiResponse<MovieDTO> response = ApiResponse.<MovieDTO>builder()
                .status(Status.OK)
                .message("Movie fetched successfully")
                .data(movieService.getMovieById(id))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-movie/{movieId}")
    public ResponseEntity<ApiResponse<String>> deleteMovieById(
                                                        @PathVariable
                                                        Long movieId) {

        movieService.deleteMovieById(movieId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.OK)
                .message("Movie deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-movie/{movieId}")
    public ResponseEntity<ApiResponse<MovieDTO>> updateMovieById(
                                                                @PathVariable
                                                                Long movieId,
                                                                @Valid
                                                                @RequestBody
                                                                MovieDTO movieDTO) {

        ApiResponse<MovieDTO> response = ApiResponse.<MovieDTO>builder()
                .status(Status.OK)
                .message("Movie updated successfully")
                .data(movieService.updateMovieById(movieId, movieDTO))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-movies")
    public ResponseEntity<ApiResponse<MoviesDTO>> getAllMovies(
            @RequestParam(name = "pageNumber",
                    defaultValue = AppConstants.PAGE_NUMBER,
                    required = false)
            Integer pageNumber,
            @RequestParam(name = "pageSize",
                    defaultValue = AppConstants.PAGE_SIZE,
                    required = false)
            Integer pageSize,
            @RequestParam(name = "sortBy",
                    defaultValue = AppConstants.SORT_MOVIES_BY,
                    required = false)
            String sortBy,
            @RequestParam(name = "sortOrder",
                    defaultValue = AppConstants.SORT_DIR,
                    required = false)
            String sortOrder
    ) {

        MoviesDTO moviesDTO = movieService.getAllMovies(sortBy, sortOrder,
                                                                    pageNumber,
                                                                    pageSize);
        boolean isEmpty = moviesDTO == null || moviesDTO.getMovies().isEmpty();
        String message = isEmpty
                ? "No Records found!"
                : "Movies fetched Successfully";

        ApiResponse<MoviesDTO> response = ApiResponse.<MoviesDTO>builder()
                .status(Status.OK)
                .message(message)
                .data(moviesDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-movie-by-name")
    public ResponseEntity<ApiResponse<MovieDTO>>getMovieByName(
                                                            @RequestParam
                                                            String movieName) {

        ApiResponse<MovieDTO> response = ApiResponse.<MovieDTO>builder()
                .status(Status.OK)
                .message("Movie fetched Successfully")
                .data(movieService.getMovieByName(movieName))
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-movie-by-genre-name")
    public ResponseEntity<ApiResponse<MoviesDTO>>getMovieByGenreName(
                                                                @RequestParam
                                                                String genreName) {

        MoviesDTO moviesDTO = movieService.getMovieByGenreName(genreName);
        boolean isEmpty = moviesDTO == null || moviesDTO.getMovies().isEmpty();
        String message = isEmpty
                ? "No Records found!"
                : "Movies fetched Successfully";

        ApiResponse<MoviesDTO> response = ApiResponse.<MoviesDTO>builder()
                .status(Status.OK)
                .message(message)
                .data(moviesDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<RentalResponseDTO>> assignMovieToUser(
                                                            @RequestBody
                                                            AssignMovieToUserDTO dto) {

        ApiResponse<RentalResponseDTO> response = ApiResponse.<RentalResponseDTO>builder()
                .status(Status.CREATED)
                .message("Checkout successful")
                .data(movieService.assignMovieToUser(dto))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/return")
    public ResponseEntity<ApiResponse<ReturnMovieResponseDTO>> processMovie(
                                                                @RequestBody
                                                                ReturnMoviesDTO dto) {

        ApiResponse<ReturnMovieResponseDTO> response = ApiResponse.<ReturnMovieResponseDTO>builder()
                .status(Status.OK)
                .message("Return processed successfully")
                .data(movieService.processReturn(dto))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/apply-discount")
    public ResponseEntity<ApiResponse<String>> applyDiscount(
            @RequestParam
            @NotNull(message = "Discount is required")
            @DecimalMin(value = "0.0", message = "Discount cannot be less than 0")
            @DecimalMax(value = "1.0", message = "Discount cannot be greater than 1")
            @Digits(integer = 1, fraction = 2, message = "Invalid discount format")
            Double discount) {

        movieService.applyDiscount(discount);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.OK)
                .message("Discount Applied successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/remove-discount")
    public ResponseEntity<ApiResponse<String>> removeDiscount() {

        movieService.removeDiscount();
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.OK)
                .message("Discount Removed successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-movies-count")
    public ResponseEntity<ApiResponse<MoviesDashboard>> getMoviesCount() {

        ApiResponse<MoviesDashboard> response = ApiResponse.<MoviesDashboard>builder()
                .status(Status.SUCCESS)
                .message("Records fetched successful")
                .data(movieService.getMoviesCount())
                .build();
        return ResponseEntity.ok(response);
    }


}
