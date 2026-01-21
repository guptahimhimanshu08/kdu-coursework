package com.kdu.graphql.dto;

import java.util.List;

/**
 * Data Transfer Object for Movie responses.
 * Prevents sensitive data exposure and maintains immutability for response data.
 */
public class MovieResponseDto {
    
    private final String id;
    private final String title;
    private final String genre;
    private final DirectorResponseDto director;
    private final List<ReviewResponseDto> reviews;

    public MovieResponseDto(String id, String title, String genre, DirectorResponseDto director, List<ReviewResponseDto> reviews) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.reviews = reviews;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public DirectorResponseDto getDirector() {
        return director;
    }

    public List<ReviewResponseDto> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "MovieResponseDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", director=" + director +
                ", reviews=" + reviews +
                '}';
    }
}
