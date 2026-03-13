package com.kdu.graphql.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain model representing a movie entity.
 * Contains core movie information and associated reviews.
 * Internal state should not be directly exposed; use DTOs for API responses.
 */
public class Movie {

    private String id;
    private String title;
    private String genre;
    private String directorId;
    private List<Review> reviews = new ArrayList<>();

    /**
     * Constructs a new Movie instance.
     * 
     * @param id the unique identifier for the movie
     * @param title the title of the movie
     * @param genre the genre classification of the movie
     * @param directorId the unique identifier of the movie's director
     */
    public Movie(String id, String title, String genre, String directorId) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.directorId = directorId;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getDirectorId() { return directorId; }
    public List<Review> getReviews() { return reviews; }

}
