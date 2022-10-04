package org.example.api.service;

import org.example.domain.models.Movie;
import org.example.domain.models.MovieInfo;
import org.example.domain.repositories.MovieRepository;
import org.example.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    @Value("${queue.movie.name}")
    private String movieQueue;
    private final MovieRepository movieRepository;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MovieService(MovieRepository movieRepository, RabbitTemplate rabbitTemplate) {
        this.movieRepository = movieRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Movie> findByGenre(String genre, Boolean throwError) {
        logger.info("Buscando las películas por género");
        if (throwError) {
            logger.error("Ocurrió un error al buscar la películas por género");
            throw new RuntimeException();
        }
        return this.movieRepository.findByGenre(genre);
    }

    public Movie saveMovie(Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        logger.info("Se recibió una película a través de rabbit");
        rabbitTemplate.convertAndSend(movieQueue, savedMovie);
        logger.info("Se ha enviado a la cola");
        return savedMovie;
    }

}