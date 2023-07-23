package cs211.project.controllers;

import cs211.project.models.Movie;
import cs211.project.models.collections.MovieCollection;
import javafx.fxml.FXML;

/**
 * You must remove this controller from your project
 */
public class MovieController {
    private MovieCollection movies;

    @FXML
    public void initialize() {
        movies = new MovieCollection(); // todo: load from csv file in the future
    }

    @FXML
    public void onSearchButtonClick() {
        String name = ""; // todo: get from input text
        Movie movie = movies.findByName(name);
        showMovie(movie);
    }

    /**
     * Show movie data in UI
     * @param movie Movie
     */
    private void showMovie(Movie movie) {
        System.out.println(movie); // todo: show movie data in UI
    }
}
