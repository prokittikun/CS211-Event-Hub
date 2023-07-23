package cs211.project.models.collections;

import cs211.project.models.Movie;

import java.util.ArrayList;

/**
 * You must remove this collection from your project
 */
public class MovieCollection {
    private ArrayList<Movie> movies;

    public MovieCollection() {
        movies = new ArrayList<>();
    }

    /**
     * Add `movie` to collection
     * @param movie
     */
    public void add(Movie movie) {
        movies.add(movie);
    }

    /**
     * Finding movie in collection by movie name
     * @param name String
     * @return Movie
     */
    public Movie findByName(String name) {
        return null;
    }
}
