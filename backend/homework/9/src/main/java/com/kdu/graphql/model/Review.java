package com.kdu.graphql.model;

import java.util.UUID;

/**
 * Domain model representing a movie review.
 * Each review is automatically assigned a unique identifier upon creation.
 */
public class Review {

    private String id;
    private String comment;
    private int rating;

    /**
     * Constructs a new Review instance with an auto-generated unique identifier.
     * 
     * @param comment the review comment text
     * @param rating the review rating value
     */
    public Review(String comment, int rating) {
        this.id = UUID.randomUUID().toString();
        this.comment = comment;
        this.rating = rating;
    }

    public String getId() { return id; }
    public String getComment() { return comment; }
    public int getRating() { return rating; }
}
