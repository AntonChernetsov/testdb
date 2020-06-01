package com.example.mytestdb.service;

import com.example.mytestdb.model.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService{

    private static Map<Integer,Movie> movie_map = new HashMap<>();
    private static int id_init = 0;

    @Override
    public void create(Movie movie) {
        movie.setId(++id_init);
        movie_map.put(id_init,movie);
    }

    @Override
    public List<Movie> readAll() {
        return new ArrayList<>(movie_map.values());
    }

    @Override
    public Movie read(int id) {
        return movie_map.get(id);
    }

    @Override
    public boolean update(Movie movie, int id) {
        if (movie_map.containsKey(id)){
            movie_map.put(id,movie);
            return true;}
        return false;
    }

    @Override
    public boolean delete(int id) {
        return movie_map.remove(id)!=null;
    }
}
