package com.example.mytestdb.service;

import com.example.mytestdb.model.Movie;

import java.util.List;

public interface MovieService {

    Movie create(Movie movie);

    List<Movie> readAll();

    Movie read(int id);

    boolean update(Movie movie, int id);

    boolean delete(int id);
}

