package com.example.mytestdb.service;

import com.example.mytestdb.model.Movie;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService{

    private static Map<Integer,Movie> movie_map = new HashMap<>();
    private static int id_init = 0;

    @Override
    public void create(Movie movie) {
        movie.setId(++id_init);
        try {
            movie.setImdb(getImdbId(movie.getName()));
            movie.setImdbData(getImdbData(movie.getImdb()));


        } catch (IOException | UnirestException e) {
            e.printStackTrace();
        }
        movie_map.put(id_init,movie);
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
