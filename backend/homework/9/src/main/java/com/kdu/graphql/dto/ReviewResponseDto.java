package com.kdu.graphql.dto;

/**
 * Data Transfer Object for Review responses.
 * Provides immutable view of review data for API responses.
 */
public class ReviewResponseDto {
    
    private final String id;
    private final String comment;
    private final int rating;

    public ReviewResponseDto(String id, String comment, int rating) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "ReviewResponseDto{" +
                "id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}
