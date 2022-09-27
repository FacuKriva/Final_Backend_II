package org.example.api.controller;

import org.example.api.service.MovieService;
import org.example.domain.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/movies" )
public class MovieController {

    private final MovieService service;

    @Autowired
    public MovieController (MovieService movieService) { this.service = movieService; }

    @GetMapping( "/{genre}" )
    ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre){
        return ResponseEntity.ok().body(service.findByGenre(genre));
    }

    @GetMapping( "/withErros/{genre}" )
    ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre,
                                                @RequestParam( "throwError" ) boolean throwError) {
        return ResponseEntity.ok().body(service.findByGenre(genre, throwError));
    }

    @PostMapping
    ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok().body(service.saveMovie(movie));
    }
}
