package com.kdu.graphql.dto;

/**
 * Data Transfer Object for Director responses.
 * Exposes only public information about directors.
 * Sensitive operational data is protected from direct exposure.
 */
public class DirectorResponseDto {
    
    private final String id;
    private final String name;
    private final int totalAwards;

    public DirectorResponseDto(String id, String name, int totalAwards) {
        this.id = id;
        this.name = name;
        this.totalAwards = totalAwards;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalAwards() {
        return totalAwards;
    }

    @Override
    public String toString() {
        return "DirectorResponseDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", totalAwards=" + totalAwards +
                '}';
    }
}
