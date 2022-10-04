package org.example.api.controller;

import org.example.api.service.MovieService;
import org.example.domain.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping( "/movies" )
public class MovieController {

    private final MovieService service;

    @Autowired
    public MovieController (MovieService movieService) { this.service = movieService; }

    @GetMapping( "/{genre}" )
    public ResponseEntity<List<Movie>> findByGenre(
            @PathVariable String genre) throws Exception {
        List<Movie> movies = service.findByGenre(genre, Boolean.FALSE);
        return movies.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping( "/withErros/{genre}" )
    ResponseEntity<List<Movie>> getMovieByGenreOrThrowError(@PathVariable String genre,
                                                @RequestParam( "throwError" ) boolean throwError
    ) throws  Exception {
        List<Movie> movies;
        movies = service.findByGenre(genre, throwError);
        return movies.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PostMapping("/save")
    ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        Movie savedMovie = this.service.saveMovie(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.OK);
    }
}
