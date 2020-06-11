package com.example.mytestdb.repository;

import com.example.mytestdb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepository extends JpaRepository<Movie,Integer> {
}
