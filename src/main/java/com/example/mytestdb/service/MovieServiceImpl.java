package com.example.mytestdb.service;

import com.example.mytestdb.model.Movie;
import com.example.mytestdb.repository.MoviesRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService{



    @Autowired
    private MoviesRepository moviesRepository;

    @PersistenceContext
    private EntityManager em;


    public Movie movieExistsByName(String name) {
        List movies = em.createQuery(String.format("select c.id from Movie c where c.name='%s'",name)).getResultList();
        if (movies.size()>0)
        return moviesRepository.getOne((Integer) movies.get(0));
        else
            return  null;
    }

    @Override
    public Movie create(Movie movie) {



        try {
            movie.setImdb(getImdbId(movie.getName()));
            movie.setImdbData(getImdbData(movie.getImdb()));


        } catch (IOException | UnirestException e) {
            e.printStackTrace();
        }

        Movie movieFounded = movieExistsByName(movie.getName());
        if(movieFounded!=null)
            return movieFounded;

        if(!movie.getImdb().equals("na")) {
            moviesRepository.save(movie);
            return movie;
        }
        else return null;
    }

    private Map<String,String> getImdbData(String imdb) throws IOException, UnirestException {
        Map<String,String> map = new HashMap<>();

        HttpResponse<String> response = Unirest.get("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/film/"+imdb)
                .header("x-rapidapi-host", "imdb-internet-movie-database-unofficial.p.rapidapi.com")
                .header("x-rapidapi-key", "7c2daf4fa6msh1d45c7b5896722ep1c80aejsna56760b05ffa")
                .asString();

        byte[] jsonData = response.getBody().getBytes();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        map.put("year",rootNode.path("year").textValue());
        map.put("title",rootNode.path("title").textValue());
        map.put("rating",rootNode.path("rating").textValue());
        return map;
    }

    private String getImdbId(String name) throws IOException, UnirestException {

        HttpResponse<String> response = Unirest.get("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/search/"+name)
                .header("x-rapidapi-host", "imdb-internet-movie-database-unofficial.p.rapidapi.com")
                .header("x-rapidapi-key", "7c2daf4fa6msh1d45c7b5896722ep1c80aejsna56760b05ffa")
                .asString();

        byte[] jsonData = response.getBody().getBytes();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode movieTitles = rootNode.path("titles");
        Iterator<JsonNode> elements = movieTitles.elements();
        if(elements.hasNext()) {
            JsonNode title = elements.next();
            JsonNode movieTitle = title.path("id");
            return movieTitle.textValue();
        }
        return "na";
    }

    @Override
    public List<Movie> readAll() {
        return moviesRepository.findAll();
    }

    @Override
    public Movie read(int id) {

        return moviesRepository.getOne(id);
    }

    @Override
    public boolean update(Movie movie, int id) {

        if (moviesRepository.existsById(id)) {
            movie.setId(id);
            moviesRepository.save(movie);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {

        if (moviesRepository.existsById(id)) {
            moviesRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
