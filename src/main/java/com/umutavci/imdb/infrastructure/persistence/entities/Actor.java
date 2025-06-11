package com.umutavci.imdb.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "actors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actor extends BaseEntity{
    private String name;
    private LocalDate birthDate;
    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;
 }

