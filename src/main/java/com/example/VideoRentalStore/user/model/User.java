package com.example.VideoRentalStore.user.model;

import com.example.VideoRentalStore.audit.AuditFields;
import com.example.VideoRentalStore.movie.model.Movie;
import com.example.VideoRentalStore.rental.model.Rental;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Entity
public class User extends AuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    @Column(unique = true)
    private String contactNo;
    @Column(unique = true)
    private String email;
    private String address;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Rental> rentals = new ArrayList<>();
}
