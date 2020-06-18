package com.example.mytestdb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "movies")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="year")
    private int year;
    @Column(name="imdb")
    private String imdb;
    @Column(name="imdb_rating")
    private double imdb_rating;

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
                this.setImdb_rating(Double.parseDouble(map.get("rating")));
            } catch (NumberFormatException e) {}
        if(map.containsKey("title"))
            try {
                this.setName(map.get("title"));
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

    public double getImdb_rating() {
        return imdb_rating;
    }

    public void setImdb_rating(double imdb_rating) {
        this.imdb_rating = imdb_rating;
    }
}
