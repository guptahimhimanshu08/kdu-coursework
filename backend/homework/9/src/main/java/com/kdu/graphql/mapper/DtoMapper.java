package com.kdu.graphql.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kdu.graphql.dto.DirectorResponseDto;
import com.kdu.graphql.dto.MovieResponseDto;
import com.kdu.graphql.dto.ReviewRequestDto;
import com.kdu.graphql.dto.ReviewResponseDto;
import com.kdu.graphql.model.Director;
import com.kdu.graphql.model.Movie;
import com.kdu.graphql.model.Review;

/**
 * Mapper utility for converting between domain models and DTOs.
 * Ensures separation of concerns and prevents exposure of sensitive internal state.
 */
public class DtoMapper {

    private static final Logger logger = LoggerFactory.getLogger(DtoMapper.class);

    /**
     * Convert Movie model to MovieResponseDto
     */
    public static MovieResponseDto toMovieResponseDto(Movie movie, Director director, List<Review> reviews) {
        logger.debug("Mapping Movie to MovieResponseDto: {}", movie.getId());
        
        DirectorResponseDto directorDto = director != null ? 
            toDirectorResponseDto(director) : null;
        
        List<ReviewResponseDto> reviewDtos = reviews != null ?
            reviews.stream()
                .map(DtoMapper::toReviewResponseDto)
                .collect(Collectors.toList()) : List.of();
        
        return new MovieResponseDto(
            movie.getId(),
            movie.getTitle(),
            movie.getGenre(),
            directorDto,
            reviewDtos
        );
    }

    /**
     * Convert Movie model to MovieResponseDto (with no director or reviews)
     */
    public static MovieResponseDto toMovieResponseDto(Movie movie) {
        logger.debug("Mapping Movie to MovieResponseDto (without relations): {}", movie.getId());
        return new MovieResponseDto(
            movie.getId(),
            movie.getTitle(),
            movie.getGenre(),
            null,
            List.of()
        );
    }

    /**
     * Convert Director model to DirectorResponseDto
     */
    public static DirectorResponseDto toDirectorResponseDto(Director director) {
        logger.debug("Mapping Director to DirectorResponseDto: {}", director.getId());
        return new DirectorResponseDto(
            director.getId(),
            director.getName(),
            director.getTotalAwards()
        );
    }

    /**
     * Convert Review model to ReviewResponseDto
     */
    public static ReviewResponseDto toReviewResponseDto(Review review) {
        logger.debug("Mapping Review to ReviewResponseDto");
        return new ReviewResponseDto(
            review.getId(),
            review.getComment(),
            review.getRating()
        );
    }

    /**
     * Create ReviewRequestDto from individual parameters
     */
    public static ReviewRequestDto toReviewRequestDto(String movieId, String comment, int rating) {
        logger.debug("Creating ReviewRequestDto for movie: {} with rating: {}", movieId, rating);
        return new ReviewRequestDto(movieId, comment, rating);
    }
}
