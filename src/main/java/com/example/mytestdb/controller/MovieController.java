package com.example.mytestdb.controller;

import com.example.mytestdb.model.Movie;
import com.example.mytestdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping(value = "/movies")
    public ResponseEntity<?> create(@RequestBody Movie movie) {
        movieService.create(movie);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/movies")
    public ResponseEntity<List<Movie>> read() {
        final List<Movie> movies = movieService.readAll();

        return movies != null &&  !movies.isEmpty()
                ? new ResponseEntity<>(movies, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/movies/{id}")
    public ResponseEntity<Movie> read(@PathVariable(name = "id") int id) {
        final Movie client = movieService.read(id);

        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/movies/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Movie movie) {
        final boolean updated = movieService.update(movie, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/movies/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = movieService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}


