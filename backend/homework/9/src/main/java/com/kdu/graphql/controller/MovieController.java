package com.kdu.graphql.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.kdu.graphql.dto.MovieResponseDto;
import com.kdu.graphql.dto.DirectorResponseDto;
import com.kdu.graphql.mapper.DtoMapper;
import com.kdu.graphql.model.Director;
import com.kdu.graphql.model.Movie;
import com.kdu.graphql.model.Review;

/**
 * GraphQL controller handling movie-related queries and mutations.
 * Uses DTOs for all responses to maintain encapsulation and prevent sensitive data exposure.
 * Provides operations for finding movies and adding reviews.
 */
@Controller
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final List<Movie> movies = new ArrayList<>(List.of(
        new Movie("1", "Inception", "Sci-Fi", "D1"),
        new Movie("2", "Interstellar", "Sci-Fi", "D1"),
        new Movie("3", "Dunkirk", "War", "D2")
    ));


    private final List<Director> directors = List.of(
            new Director("D1", "Christopher Nolan", 11),
            new Director("D2", "Steven Spielberg", 34)
    );


    /**
     * Query mapping to find a movie by its unique identifier.
     * Returns a MovieResponseDto to protect internal state from direct exposure.
     * 
     * @param id the unique identifier of the movie to find
     * @return MovieResponseDto containing movie details with director and reviews, or null if not found
     */
    @QueryMapping
    public MovieResponseDto findMovieById(@Argument String id) {
        logger.debug("Searching for movie with ID: {}", id);
        Movie movie = movies.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (movie != null) {
            logger.info("Movie found: {} (Genre: {})", movie.getTitle(), movie.getGenre());
            Director director = directors.stream()
                    .filter(d -> d.getId().equals(movie.getDirectorId()))
                    .findFirst()
                    .orElse(null);
            return DtoMapper.toMovieResponseDto(movie, director, movie.getReviews());
        } else {
            logger.warn("Movie not found for ID: {}", id);
            return null;
        }
    }

    /**
     * Nested field resolver for the director field of a Movie.
     * Director is already mapped in the MovieResponseDto, so we can return it directly.
     * 
     * @param movie the MovieResponseDto containing the director information
     * @return DirectorResponseDto with director details, or null if not found
     */
    @SchemaMapping(typeName = "Movie", field = "director")
    public DirectorResponseDto director(MovieResponseDto movie) {
        logger.debug("Resolving director for movie: {} (Director ID: {})", movie.getTitle(), movie.getId());
        // Director is already mapped in the MovieResponseDto, so we can return it directly
        DirectorResponseDto director = movie.getDirector();
        if (director != null) {
            logger.info("Director resolved: {}", director.getName());
        } else {
            logger.warn("Director not found for movie: {}", movie.getId());
        }
        return director;
    }

    /**
     * Mutation mapping to add a review to an existing movie.
     * Returns the updated movie as DTO to avoid exposing internal state and prevent direct mutations.
     * 
     * @param movieId the unique identifier of the movie to add the review to
     * @param comment the review comment text
     * @param rating the review rating (integer value)
     * @return MovieResponseDto with the newly added review, or null if movie not found
     */
    @MutationMapping
    public MovieResponseDto addReview( @Argument String movieId,
        @Argument String comment,
        @Argument int rating){

            logger.debug("Adding review to movie ID: {} with rating: {}", movieId, rating);
            Movie movie = movies.stream()
                .filter(m -> m.getId().equals(movieId))
                .findFirst()
                .orElse(null);

        if (movie == null) {
            logger.error("Failed to add review: Movie not found with ID: {}", movieId);
            return null;
        }

        movie.getReviews().add(new Review(comment, rating));
        logger.info("Review added successfully to movie: {}. Total reviews: {}", movie.getTitle(), movie.getReviews().size());

        Director director = directors.stream()
                .filter(d -> d.getId().equals(movie.getDirectorId()))
                .findFirst()
                .orElse(null);
        return DtoMapper.toMovieResponseDto(movie, director, movie.getReviews());
    }


}
