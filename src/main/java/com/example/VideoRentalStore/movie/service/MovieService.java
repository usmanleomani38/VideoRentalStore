package com.example.VideoRentalStore.movie.service;

import com.example.VideoRentalStore.apputils.CommonUtils;
import com.example.VideoRentalStore.coupon.model.Coupon;
import com.example.VideoRentalStore.coupon.repo.CouponRepo;
import com.example.VideoRentalStore.exceptionhandler.customexceptions.OutOfStockException;
import com.example.VideoRentalStore.exceptionhandler.customexceptions.ResourceNotFoundException;
import com.example.VideoRentalStore.genere.model.Genre;
import com.example.VideoRentalStore.genere.repo.GenreRepo;
import com.example.VideoRentalStore.rental.model.RentalStatus;
import com.example.VideoRentalStore.movie.dtos.*;
import com.example.VideoRentalStore.movie.model.Movie;
import com.example.VideoRentalStore.movie.repo.MovieRepo;
import com.example.VideoRentalStore.rental.dto.response.RentalResponseDTO;
import com.example.VideoRentalStore.rental.dtos.ReturnMovieResponseDTO;
import com.example.VideoRentalStore.rental.model.Rental;
import com.example.VideoRentalStore.rental.repo.RentalRepo;
import com.example.VideoRentalStore.user.model.User;
import com.example.VideoRentalStore.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.WordUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepo movieRepo;
    private final GenreRepo genreRepo;
    private final UserRepo userRepo;
    private final RentalRepo rentalRepo;
    private final CouponRepo couponRepo;


    public MovieDTO addMovie(MovieDTO movieDTO) {

        movieRepo.findByMovieNameIgnoreCase(movieDTO.getMovieName())
                .ifPresent(m -> {
                    throw new RuntimeException("This movie already exists");
                });

        Movie movie = new Movie();
        movie.setMovieName(WordUtils.capitalize(movieDTO.getMovieName()));
        movie.setDuration(movieDTO.getDuration());
        movie.setAvailableQuantity(movieDTO.getAvailableQuantity());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setDailyRentalRate(movieDTO.getDailyRentalRate());
        List<Genre> genreList = genreRepo.findAllById(movieDTO.getGenreIds());
        movie.setGenres(genreList);
        return MovieDTO.toDTO(movieRepo.save(movie));

    }

    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepo.findById((id))
                .orElseThrow(()-> new ResourceNotFoundException("Movie Not found!"));
        return MovieDTO.toDTO(movie);

    }

    @Transactional
    public RentalResponseDTO assignMovieToUser(AssignMovieToUserDTO dto) {

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User not found!"));

        List<Rental> rentals = new ArrayList<>();
        for(Long barcode : dto.getBarcodes()) {
            Movie movie = movieRepo.findById(barcode)
                    .orElseThrow(()-> new ResourceNotFoundException("Movie not found!"));
            Rental rental = new Rental();
            rental.setMovie(movie);
            rental.setUser(user);
            rental.setRentalDate(LocalDate.now());
            rental.setStatus(RentalStatus.PENDING);
            if(movie.getAvailableQuantity() <= 0)
                throw new OutOfStockException("This movie is out of Stock");
            else {
                movie.setAvailableQuantity(movie.getAvailableQuantity() - 1);
            }
            movieRepo.save(movie);
            rentals.add(rentalRepo.save(rental));
            movie.getRentals().add(rental);
            user.getRentals().add(rental);
        }
        return RentalResponseDTO.toDTO(user,rentals);
    }

    @Transactional
    public ReturnMovieResponseDTO processReturn(ReturnMoviesDTO dto) {

        Long totalDays = 0L;
        double totalAmount = 0D;
        double lossAmount = 0D;
        double returnAmount = 0D;
        Double grandTotal = 0D;

        List<Rental> rentals = new ArrayList<>();
        User user = null;
        for (ReturnItemDTO returnItemDTO : dto.getMovies()) {
            Rental rental = rentalRepo.findByMovieId(returnItemDTO.getMovieId())
                    .orElseThrow(() -> new ResourceNotFoundException("Movie not found!"));

            if(rental.getStatus().equals(RentalStatus.RETURNED)
                    || rental.getStatus().equals(RentalStatus.LOSS))
                throw new IllegalStateException(
                        "This movie is already processed!"
                );

            Movie movie = rental.getMovie();
            if (returnItemDTO.getStatus() == RentalStatus.RETURNED) {
                totalDays = ChronoUnit.DAYS.between(rental.getRentalDate(), LocalDate.now().plusDays(2));
                rental.setReturnDate(LocalDate.now());
                rental.setStatus(RentalStatus.RETURNED);
                returnAmount = movie.getDailyRentalRate() * totalDays;
                totalAmount += returnAmount;
                rental.setTotalAmount(returnAmount);
                movie.setAvailableQuantity(movie.getAvailableQuantity() + 1);

            }
            else if (returnItemDTO.getStatus().equals(RentalStatus.LOSS)) {
                rental.setStatus(RentalStatus.LOSS);
                lossAmount = 5 * movie.getDailyRentalRate();
                rental.setTotalAmount(lossAmount);
                totalAmount += lossAmount;
            }
            else
                throw new IllegalArgumentException(
                        "Invalid Status!"
                );

            movieRepo.save(movie);
            rentals.add(rentalRepo.save(rental));
            user = rental.getUser();
        }

        Coupon coupon = null;
        if(dto.getCouponCode() != null) {
          coupon = couponRepo.findByCouponCode(dto.getCouponCode())
                    .orElseThrow(()-> new ResourceNotFoundException("Invalid Coupon"));
            if(!coupon.getIsActive())
              throw new RuntimeException("Coupon is not active");
            if(coupon.getExpiryDate().isBefore(LocalDateTime.now()))
                throw new RuntimeException("Coupon is expired");
            double discountAmount = totalAmount * (coupon.getDiscountPercent() / 100.0);
            double finalAmount = totalAmount - discountAmount;
            finalAmount = finalAmount = Math.round(finalAmount * 100.0) / 100.0;
            totalAmount = finalAmount;
        }

        return ReturnMovieResponseDTO.toDTO(rentals,
                totalDays,
                user,
                totalAmount);
    }


    @Transactional
    public String deleteMovieById(Long movieId) {

        Movie existingMovie = movieRepo.findById(movieId)
                .orElseThrow(()-> new ResourceNotFoundException("Movie not found!"));

        if(!existingMovie.getRentals().isEmpty())
            throw new IllegalStateException("Movie has active rentals — cannot delete!");
        for(Genre genre : existingMovie.getGenres())
            genre.getMovieList().remove(existingMovie);
        // existingMovie.getGenres().clear();
        movieRepo.delete(existingMovie);
        return "Movie Deleted!";
    }

    @Transactional
    public MovieDTO updateMovieById(Long movieId, MovieDTO movieDTO) {

        Movie movie = movieRepo.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found!"));

        movieRepo.findByMovieNameIgnoreCase(movieDTO.getMovieName())
                .ifPresent(c-> {
                    if(!movie.getBarcode().equals(movieId))
                        throw new IllegalStateException(
                                "This Movie already exists!"
                        );
                });

        movie.setMovieName(WordUtils.capitalize(movieDTO.getMovieName()));
        movie.setDuration(movieDTO.getDuration());
        movie.setAvailableQuantity(movieDTO.getAvailableQuantity());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setDailyRentalRate(movieDTO.getDailyRentalRate());

        List<Genre> genreList = null;
        if (movieDTO.getGenreIds() != null && !movieDTO.getGenreIds().isEmpty()) {
            genreList = genreRepo.findAllById(movieDTO.getGenreIds());

            if (genreList.size() != movieDTO.getGenreIds().size())
                throw new ResourceNotFoundException("Some IDs are not present in the Database!");
            movie.setGenres(genreList);
        }

        return MovieDTO.toDTO(movieRepo.save(movie));
    }

    public MoviesDTO getAllMovies(String sortBy, String sortOrder, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,CommonUtils.buildSort(sortBy, sortOrder));
        Page<Movie> page = movieRepo.findAll(pageRequest);
        var movies = page.getContent();
        var totalPages = page.getTotalPages();
        var totalElements = page.getTotalElements();

        if (movies.isEmpty())
            return MoviesDTO.builder()
                    .movies(Collections.emptyList())
                    .build();
        return MoviesDTO.toDTO(new ArrayList<>(movies), pageNumber,pageSize, totalPages, totalElements);
    }

    public MovieDTO getMovieByName(String movieName) {

        Movie movie = movieRepo.findByMovieNameContainingIgnoreCase(movieName)
                .orElseThrow(()-> new ResourceNotFoundException("Movie not found!"));
        return MovieDTO.toDTO(movie);
    }

    public MoviesDTO getMovieByGenreName(String genreName) {

        List<Movie> movies = movieRepo.findMoviesByGenreName(genreName);
        if(movies.isEmpty())
            return MoviesDTO.builder()
                    .movies(Collections.emptyList())
                    .build();
        return MoviesDTO.toDTO(movies);

    }

}
