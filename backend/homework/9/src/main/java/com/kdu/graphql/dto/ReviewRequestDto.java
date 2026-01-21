package com.kdu.graphql.dto;

/**
 * Data Transfer Object for Review request payload.
 * Encapsulates review input data from client requests.
 * Ensures immutability and prevents unintended mutations of internal state.
 */
public class ReviewRequestDto {
    
    private final String movieId;
    private final String comment;
    private final int rating;

    public ReviewRequestDto(String movieId, String comment, int rating) {
        this.movieId = movieId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "ReviewRequestDto{" +
                "movieId='" + movieId + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}
