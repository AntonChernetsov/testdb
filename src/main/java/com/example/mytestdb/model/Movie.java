package com.example.mytestdb.model;

import java.util.Map;

public class Movie {
    private int id;
    private String name;
    private int year;
    private String imdb;
    private float imdb_rating;

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public void setImdbData(Map<String,String> map){
        if(map.containsKey("year"))
            try {
                this.setYear(Integer.parseInt(map.get("year")));
            } catch (NumberFormatException e) {}
        if(map.containsKey("rating"))
            try {
                this.setImdb_rating(Float.parseFloat(map.get("rating")));
            } catch (NumberFormatException e) {}

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getImdb_rating() {
        return imdb_rating;
    }

    public void setImdb_rating(float imdb_rating) {
        this.imdb_rating = imdb_rating;
    }
}
