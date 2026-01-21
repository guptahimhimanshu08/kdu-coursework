package com.kdu.graphql.model;

/**
 * Domain model representing a movie director.
 * Contains information about the director including their achievements.
 */
public class Director {

    private String id;
    private String name;
    private int totalAwards;

    /**
     * Constructs a new Director instance.
     * 
     * @param id the unique identifier for the director
     * @param name the full name of the director
     * @param totalAwards the total number of awards won by the director
     */
    public Director(String id, String name, int totalAwards) {
        this.id = id;
        this.name = name;
        this.totalAwards = totalAwards;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getTotalAwards() { return totalAwards; }
}
