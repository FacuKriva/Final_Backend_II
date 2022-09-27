package org.example.api.service;

import org.example.domain.models.Movie;
import org.example.domain.models.MovieInfo;
import org.example.domain.repositories.MovieRepository;
import org.example.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository repository;
    private final RedisUtils redisUtils;

    @Autowired
    public MovieService(MovieRepository movieRepository, RedisUtils redisUtils) {
        this.repository = movieRepository;
        this.redisUtils = redisUtils;
    }

    public List<Movie> findByGenre(String genre) {
        MovieInfo movieInfo = redisUtils.getMovieInfo(genre);
        if (Objects.nonNull(movieInfo)) {
            return movieInfo.getMovies();
        }
        List<Movie> movies = repository.findByGenre(genre);
        redisUtils.createMovieInfo(genre, movies);
        return movies;
    }

    public List<Movie> findByGenre(String genre, Boolean throwError) {
        logger.info("Buscando las películas por género");
        if (throwError) {
            logger.error("Ocurrió un error al buscar la películas por género");
            throw new RuntimeException();
        }
        return repository.findByGenre(genre);
    }

    @RabbitListener(queues = "${queue.movie.name}")
    public void save(Movie movie) {
        logger.info("Se recibio una movie a través de rabbit " + movie.toString());
        saveMovie(movie);
    }

    public Movie saveMovie(Movie movie) {
        return repository.save(movie);
    }

}